package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Search_Priority extends ISearch {

    private int query;

    @Override
    public ArrayList<Mail> search(ArrayList<Mail> mails) {
        ArrayList<Mail> result = new ArrayList<Mail>();
        for (Mail mail : mails) {
            if (mail.getPriority() == this.query)
                result.add(mail);
        }
        return result;
    }

    public Search_Priority(String query) {
        this.query = Integer.parseInt(query);
    }

}
