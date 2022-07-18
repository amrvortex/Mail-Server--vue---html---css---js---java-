package demo.blitz.service;

import demo.blitz.dao.database;
import demo.blitz.model.Mail;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class Mail_Service {
    public final database db;

    public Mail_Service() throws FileNotFoundException {
        db = database.getSingleton();
    }

    //This function composes new Email and it saves it in both sender and receivers
    public String [] compose(Mail mail) throws IOException {
        //This part determines the valid and invalid users
        ArrayList<String> valid_users = new ArrayList<>();
        ArrayList<String> invalid_users = new ArrayList<>();
        for (String receiver : mail.getReceivers()){
            if (db.verify_user(receiver)){
                valid_users.add(receiver);
            }else{
                invalid_users.add(receiver);
            }
        }

        //This part saves the mail in the sender sent folder:
        //Initializing the mail folders
        int number_of_mails = read_update_index(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+mail.getSender()+"\\sent");
        //mail.setID(number_of_mails);


        File sender_path = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+mail.getSender()+"\\sent");
        sender_path.mkdir();
        File attachment_path = new File(sender_path.toString()+ "\\" + number_of_mails + " attachments");
        attachment_path.mkdir();
        FileWriter fw = new FileWriter(attachment_path.toString()+"\\index.txt");
        fw.write(mail.getAttachments().length+"");
        fw.close();
        //Copying and making folders for attachments
        int attachment_counter = 0;
        for (String sourceDirectory : mail.getAttachments()){
            File sourceFile = new File(sourceDirectory);
            File targetDirectory = new File(attachment_path.toString()+"\\"+attachment_counter);
            targetDirectory.mkdir();
            copyFolder(sourceFile,new File(targetDirectory+"\\" + sourceFile.getName()));
            mail.changeElementAttachments(attachment_counter,targetDirectory+"\\" + sourceFile.getName());
            attachment_counter++;
        }
        mail.save_json(sender_path + "\\" + number_of_mails + ".json");

        //This part saves the mails for the receivers
        for(String valid_user : valid_users){
            sender_path = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+valid_user+"\\inbox\\");
            read_update_index(sender_path.toString());
            mail.save_json(sender_path + "\\" + number_of_mails + ".json");
        }

        return invalid_users.toArray(String[]::new);
    }

    //This functions saves draft incoming mail
    public void draft(Mail mail) throws IOException {
        String draft_path = ".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+mail.getSender()+"\\Draft";
        int id = read_update_index(draft_path);
        //mail.setID(id);
        mail.save_json(draft_path + "\\" + id + ".json" );
    }

    //This function sends draft mail
    public String [] send_draft(String path, String date) throws IOException, ParseException {
        Mail mail = Mail.parseMail(path);
        mail.setDate(date);
        File file = new File(path);
        file.delete();
        return compose(mail);
    }

    //This Function undo delete of a mail and removes it from trash and returns it to its position
    public void undo_delete(String path){
        File mail = new File(path);
        String user = mail.getParentFile().getParentFile().getName();
        String name = mail.getName().substring(2);
        String folder = "";
        switch(mail.getName().charAt(0)){
            case 'I':
                folder = "Inbox";
                break;
            case'S':
                folder = "Sent";
                break;
            case 'D':
                folder = "Draft";
                break;
        }
        mail.renameTo(new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"
                +user+"\\"+folder+"\\"+name));
    }

    //This function delete mails and move them to trash
    public void delete(String path) throws IOException {
        File messageToDelete = new File(path);
        String name = messageToDelete.getName(); //The File name which we want to delete
        String folder = messageToDelete.getParentFile().getName(); //The folder which contains the mail [Draft,Inbox,Sent]
        String user = messageToDelete.getParentFile().getParentFile().getName(); //The the user whom deleted the mail
        messageToDelete.renameTo(new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"
                +user+"\\Trash\\"+folder.charAt(0)+"_"+name));
    }

    //Private function which reads and update the index in each folder
    private int read_update_index(String path) throws IOException {
        Scanner fr = new Scanner(new File(path+"\\index.txt"));
        int number_of_mails = fr.nextInt();
        fr.close();
        FileWriter fw = new FileWriter(path +"\\index.txt",false);
        fw.write(""+(number_of_mails+1));
        fw.close();
        return number_of_mails;
    }

    //Private function which copy folder from source to destination
    private void copyFolder(File source, File destination) throws IOException {
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0)
        {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }
}
