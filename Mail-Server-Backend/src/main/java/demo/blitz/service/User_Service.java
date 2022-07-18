package demo.blitz.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import demo.blitz.model.User;
import demo.blitz.dao.database;

public class User_Service {

	File index = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\index.txt");
	public final database db;

	public User_Service() throws FileNotFoundException {
		db = database.getSingleton();
	}

	public boolean verifyUser(String Email , String Password) {
		if(verifyEmail(Email)) {
			return verifyPassword(Email, Password);
		}
		return false;
	}
	private boolean verifyEmail(String Email) {
		return db.verify_user(Email);
	}
	private boolean verifyPassword(String Email , String Password) {
		JSONParser parser = new JSONParser();
		boolean passwordVerified=false;
		try {
			FileReader reader = new FileReader(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\" + Email + "\\UsersInfo.json");
			Object obj = parser.parse(reader);
			JSONObject user = (JSONObject) obj;
			passwordVerified=user.get("Password").equals(Password);
			reader.close();
			return passwordVerified;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public JSONObject getUserInfo(String Email) {
		JSONParser parser = new JSONParser();
		JSONObject infoCollector = new JSONObject();
		try {
			FileReader reader = new FileReader(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\" + Email + "\\UsersInfo.json");
			Object obj = parser.parse(reader);
			JSONObject usersInfo = (JSONObject) obj;
			infoCollector.put("Email", usersInfo.get("Email"));
			infoCollector.put("Name", usersInfo.get("Name"));
			infoCollector.put("Password", usersInfo.get("Password"));
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return infoCollector;
	}
	public boolean createUser(User user) {
		if(verifyEmail(user.getEmail()))
			return false;

		String UsersFolderPath = ".\\src\\main\\java\\demo\\blitz\\Data\\Users\\";
		UsersFolderPath = UsersFolderPath + user.getEmail();
		File mail = new File(UsersFolderPath);
		File inbox = new File(UsersFolderPath + "\\Inbox");
		File draft = new File(UsersFolderPath + "\\Draft");
		File trash = new File(UsersFolderPath + "\\Trash");
		File sent = new File(UsersFolderPath + "\\Sent");
		boolean create = mail.mkdir() && inbox.mkdir() && draft.mkdir() && trash.mkdir() && sent.mkdir();
		if(create){
			try {
				File Contacts = new File(UsersFolderPath + "\\Contacts.txt");
				Contacts.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		//---------------------
		File folders []  = new File[]{inbox,draft,trash,sent};
		for (File folder : folders){
			try {
				FileWriter fw = new FileWriter(folder + "\\index.txt");
				fw.write("0");
				fw.close();
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		//----------------------

		user.save_JSON(UsersFolderPath + "\\UsersInfo.json");
		if(create) {
			try {
				if(!index.isFile()) {
					try {
						index.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\index.txt",true)));
				pw.println(user.getEmail());
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.add_user(user.getEmail());
		return create;
	}
	public boolean addContact(String Email , String Contact) throws IOException {
		if(verifyEmail(Contact)){
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+Email+"\\Contacts.txt",true)));
			pw.println(Contact);
			pw.close();
			return true;
		}
		return false;
	}
	public String[] displayContacts(String Email) throws FileNotFoundException {
		ArrayList<String> Contacts = new ArrayList<String>();
		File ContactsFile = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+Email+"\\Contacts.txt");
		Scanner fr = new Scanner(ContactsFile);
        while(fr.hasNext()) {
            Contacts.add(fr.next());
        }
		fr.close();
		return Contacts.toArray(String[]::new);
	}
	public void removeContact(String Email , String Contact){
		File Contacts = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+Email+"\\Contacts.txt");
		File tempContacts = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+Email+"\\tempContacts.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Contacts));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempContacts));

			String lineToRemove = Contact;
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) continue;
			    writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Contacts.delete();
		boolean successful = tempContacts.renameTo(Contacts);
	}
	private void adjustIndex(String Email) {
		File index = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\index.txt");
		File tempIndex = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\tempIndex.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(index));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempIndex));

			String lineToRemove = Email;
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.equals(lineToRemove)) continue;
				writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index.delete();
		boolean successful = tempIndex.renameTo(index);
	}

	public void deleteUser(String Email) {
		try {
			FileUtils.deleteDirectory(new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+Email));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adjustIndex(Email);
		db.delete_user(Email);
	}

	public void modifyUser(User user) {
		JSONParser parser = new JSONParser();
		JSONObject modifiedUser = new JSONObject();
		try {

			FileReader reader = new FileReader(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\" + user.getEmail() + "\\UsersInfo.json");
			Object obj = parser.parse(reader);
			JSONObject preModificationUser = (JSONObject) obj;

			modifiedUser.put("Email", user.getEmail());
			if(user.getName()==null) {
				modifiedUser.put("Name", preModificationUser.get("Name"));
			}
			else {
				modifiedUser.put("Name",user.getName());
			}
			if(user.getPassword()==null) {
				modifiedUser.put("Password", preModificationUser.get("Password"));
			}
			else {
				modifiedUser.put("Password",user.getPassword());
			}

			FileWriter fw_JSON = new FileWriter(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\"+user.getEmail() + "\\UsersInfo.json");
			fw_JSON.write(modifiedUser.toJSONString());
			fw_JSON.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}