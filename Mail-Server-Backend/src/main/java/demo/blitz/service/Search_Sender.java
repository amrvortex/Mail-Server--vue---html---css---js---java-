package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Search_Sender extends ISearch {

    private String query;

    @Override
    public ArrayList<Mail> search(ArrayList<Mail> mails) {
        ArrayList<Mail> result = new ArrayList<Mail>();
        for (Mail mail : mails) {
            String content = mail.getSubject().toLowerCase();
            if (mail.getSender().equalsIgnoreCase(this.query))
                result.add(mail);
        }
        return result;
    }

    public Search_Sender(String query) {
        this.query = query;
    }

}
