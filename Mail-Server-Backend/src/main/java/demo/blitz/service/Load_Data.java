package demo.blitz.service;

import demo.blitz.model.Mail;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Load_Data {

    public ArrayList<Mail> getAll (String path){
        ArrayList<Mail> mails = new ArrayList<Mail>();
        File dir = new File(path);
        File[] list = dir.listFiles();
        if(list == null){
            return mails;
        }
        for (File file : list){
            if (file.isFile() && !getExtension(file.getName()).equals("txt")) {
                try {
                    Mail mail = Mail.parseMail(file.getPath());
                    String id = file.getName().replace(".json","");
                    mail.setID(id);
                    mails.add(0, mail);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return mails;
    }

    public ArrayList<Mail> getPage(ArrayList<Mail> mails, int page) throws Exception {
        if (mails.isEmpty() || mails.size() < 10){
            return mails;
        }
        // frontend.
        int n_Pages =  mails.size()/10;
        n_Pages = (n_Pages % 10 != 0) ? (n_Pages + 1) : n_Pages;
        if (page <= n_Pages){
            int start = (page - 1)*10;
            int end = (page == n_Pages) ? mails.size() : (page * 10);
            ArrayList<Mail> mailsPage = new ArrayList<Mail>();
            for (int i = start; i < end; i++)
                mailsPage.add(mails.get(i));
            return mailsPage;
        }
        return new ArrayList<Mail>();
    }

    private String getExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
}
