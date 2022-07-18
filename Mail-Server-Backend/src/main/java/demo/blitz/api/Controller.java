package demo.blitz.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import demo.blitz.model.Mail;
import demo.blitz.model.User;
import demo.blitz.service.Mail_Manipulation_Service;
import demo.blitz.service.Mail_Service;
import demo.blitz.service.ProtectionProxy;
import demo.blitz.service.User_Service;

@RestController
@CrossOrigin()
public class Controller {
	public final User_Service User ;
	public final Mail_Service mail_service ;
	public final ProtectionProxy protectionProxy;
	public final Mail_Manipulation_Service mail_manipulation_service;

	@Autowired
	public Controller() throws FileNotFoundException {
		mail_service = new Mail_Service();
		User = new User_Service();
		protectionProxy = new ProtectionProxy();
		mail_manipulation_service = new Mail_Manipulation_Service();
	}
	@PostMapping("/openFile/")
	public void OpenFile(@RequestBody() JSONObject Path) throws IOException{
		String Desktop = System.getProperty("user.home") + "\\Desktop\\";
		File source = new File(((String)System.getProperty("user.dir")+Path.get("path")).substring(1));
		File dest = new File(Desktop+(String)Path.get("name"));
		FileSystemUtils.copyRecursively(source, dest);
			}
	@PostMapping("/verifyUser")
	public boolean verifyUser(@RequestBody User user) throws FileNotFoundException {
		return protectionProxy.verifyUser(user.getEmail(), user.getPassword());
	}
	@GetMapping("getUsersInfo/{Email}")
	public JSONObject getUserInfo(@PathVariable("Email") String Email) {
		return User.getUserInfo(Email);
	}
	@PostMapping("/allowUserAccess")
	public boolean allowUserAccess(@RequestBody User user) {
		return protectionProxy.allowUserAccess(user.getEmail());
	}
	@PostMapping("/createUser")
	public boolean createUser(@RequestBody User user) {
		return User.createUser(user);
	}
	@PostMapping("/modifyUser")
	public void modifyUser(@RequestBody User user) {
		User.modifyUser(user);
	}
	 @DeleteMapping(path = "{Email}")
	 public void deleteUser(@PathVariable("Email") String Email) {
		 User.deleteUser(Email);
	 }
	 /*@PostMapping("/compose")
	public String [] compose(@RequestBody Mail email) throws IOException {
		return mail_service.compose(email);
	}

	  */

	@PostMapping("/draft")
	public void draft(@RequestParam("Sender") String sender, @RequestParam("Receivers") String[] receivers,
					  @RequestParam("Priority") int priority, @RequestParam("Subject") String subject,
					  @RequestParam("Body") String body, @RequestParam("Date") String date,
					  @RequestParam(value = "files[]", required = false) ArrayList<MultipartFile> files) throws IOException {
		String[] attachments;
		if (files != null) {
			attachments = new String[files.size()];
			for (int i = 0; i < files.size(); i++) {
				File outputFile = new File(System.getProperty("java.io.tmpdir"), files.get(i).getOriginalFilename());
				files.get(i).transferTo(outputFile);
				System.out.println(outputFile.getAbsolutePath());
				attachments[i] = outputFile.getAbsolutePath();
			}
		}
		else {
			attachments = new String[0];
		}
		Mail mail = new Mail(subject,date,body,sender,priority,attachments,receivers);
		mail_service.draft(mail);
	}

	@PostMapping("/send_draft")
	public String[]  send_draft(@RequestBody JSONObject Path_Date) throws IOException, ParseException {
		return mail_service.send_draft(Path_Date.get("Path").toString(),Path_Date.get("Date").toString());
	}

	@PostMapping("/delete")
	public void delete(@RequestParam("Path") String Path) throws IOException {
	   mail_service.delete(Path);
	}
	@PostMapping("/undo_delete")
	public void undo_delete(@RequestParam("Path") String Path){
	   mail_service.undo_delete(Path);
	}

	@PostMapping(value = "/compose")
	public String[] compose(@RequestParam("Sender") String sender, @RequestParam("Receivers") String[] receivers,
						    @RequestParam("Priority") int priority, @RequestParam("Subject") String subject,
						    @RequestParam("Body") String body, @RequestParam("Date") String date,
						    @RequestParam(value = "files[]", required = false) ArrayList<MultipartFile> files) throws IOException {
		String[] attachments;
		if (files != null) {
			attachments = new String[files.size()];
			for (int i = 0; i < files.size(); i++) {
				File outputFile = new File(System.getProperty("java.io.tmpdir"), files.get(i).getOriginalFilename());
				files.get(i).transferTo(outputFile);
				System.out.println(outputFile.getAbsolutePath());
				attachments[i] = outputFile.getAbsolutePath();
			}
		}
		else {
			attachments = new String[0];
		}
		Mail mail = new Mail(subject,date,body,sender,priority,attachments,receivers);
		return mail_service.compose(mail);
	}


	@PostMapping("/display")
	public ArrayList<Mail> display(@RequestParam("Path") String path, @RequestParam("Page") int page) throws Exception {
		return mail_manipulation_service.display(path, page);
	}

	@PostMapping("/manipulate")
	public ArrayList<Mail> manipulate(@RequestParam("Path") String path, @RequestParam("Arr") String[] arr,
									  @RequestParam("Page") int page) throws Exception {
		return mail_manipulation_service.manipulate(path,arr,page);
	}

	@PostMapping("/filter")
	public ArrayList<Mail> filter(@RequestParam("Path") String path, @RequestParam("Type") String type,
								  @RequestParam("Query") String[] query, @RequestParam("Page") int page) throws Exception {
		return mail_manipulation_service.filter(path,type,query,page);
	}
	@CrossOrigin()
	@GetMapping("/getCurrentPath")
	public String getCurrentPath(){
		return System.getProperty("user.dir");
	}
	@PostMapping("/addContact")
	public boolean addContact(@RequestBody JSONObject Contacts) throws IOException {
		return User.addContact((String)Contacts.get("Email"),(String)Contacts.get("Contact"));
	}
	@DeleteMapping("/removeContact")
	public void removeContact(@RequestBody JSONObject Contacts){
		System.out.println(Contacts.get("Email"));
		User.removeContact((String)Contacts.get("Email"),(String)Contacts.get("Contact"));
	}
	@GetMapping("/displayContacts/{Email}")
	public String[] displayContacts(@PathVariable("Email") String Email) throws FileNotFoundException {
		return User.displayContacts(Email);
	}
	@PostMapping("/download/")
	public @ResponseBody byte[] getFile(@RequestBody JSONObject Path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get((String)Path.get("Attachement")));
		return bytes;
	}
	





}
