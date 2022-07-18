package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;
import java.util.Date;

public class Search_Date extends ISearch {

    private String  query;

    @Override
    public ArrayList<Mail> search(ArrayList<Mail> mails) {
        ArrayList<Mail> result = new ArrayList<Mail>();
        for (Mail mail : mails) {
            if (mail.getDate().equals(this.query))
                result.add(mail);
        }
        return result;
    }

    public Search_Date(String query) {
        this.query = query;
    }

}
