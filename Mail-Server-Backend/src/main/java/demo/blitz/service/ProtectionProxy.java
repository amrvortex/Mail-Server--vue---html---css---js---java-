package demo.blitz.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ProtectionProxy {
	private static ArrayList<String> LoginQueue = new ArrayList<String>();
	
	public boolean verifyUser(String Email , String Password) throws FileNotFoundException {
		User_Service users = new User_Service();
		if(users.verifyUser(Email, Password)) {
			LoginQueue.add(Email);
			return true;
		}
		return false;
	}
	public boolean allowUserAccess(String Email) {
		if(LoginQueue.contains(Email)) {
			LoginQueue.remove(Email);
			return true;
		}
		return false;
	}

}
