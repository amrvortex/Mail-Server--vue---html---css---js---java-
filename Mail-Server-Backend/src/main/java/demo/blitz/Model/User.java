package demo.blitz.model;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
	private String Name;
	private String Email;
	private String Password;
	
	public User(@JsonProperty("Name") String Name ,@JsonProperty("Email") String Email ,@JsonProperty("Password") String Password) {
		this.Name = Name;
		this.Email = Email;
		this.Password = Password;
	}
	
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public void save_JSON(String Path) {
		JSONObject userJSON = new JSONObject();
		userJSON.put("Name", this.Name);
		userJSON.put("Email", this.Email);
		userJSON.put("Password", this.Password);        
        try {
        	FileWriter fw_JSON = new FileWriter(Path);
			fw_JSON.write(userJSON.toJSONString());
			fw_JSON.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	

}
