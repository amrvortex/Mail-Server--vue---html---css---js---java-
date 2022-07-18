package demo.blitz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Repository
public class database {
    public ArrayList<String> users = new ArrayList<String>();
    private static database singleton = null;

    private database() throws FileNotFoundException {
        File index = new File(".\\src\\main\\java\\demo\\blitz\\Data\\Users\\index.txt");
        if(!index.exists()){
        	  try {
				index.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
        Scanner fr = new Scanner(index);
        while(fr.hasNext()) {
            users.add(fr.next());
        }
        fr.close();
        }
    }

    public static database getSingleton() throws FileNotFoundException {
        if (singleton == null){
            singleton = new database();
        }
        return singleton;
    }

    public Boolean verify_user(String Email){
        for(int i=0 ; i <users.size();i++) {
    	if (users.get(i).equalsIgnoreCase(Email)) {
    		return true;
    	}
        }
        return false;
    }
    public void add_user(String Email) {
    	users.add(Email);
    }
    public void delete_user(String Email) {
    	users.remove(Email);
    }
}
