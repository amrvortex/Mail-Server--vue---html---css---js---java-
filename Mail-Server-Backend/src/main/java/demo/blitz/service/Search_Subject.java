package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Search_Subject extends ISearch {

    private String query;

    public Search_Subject(String query) {
        this.query = query.toLowerCase();
    }

    @Override
    public ArrayList<Mail> search(ArrayList<Mail> mails) {
        ArrayList<Mail> result = new ArrayList<Mail>();
        for (Mail mail : mails) {
            String content = mail.getSubject().toLowerCase();
            if (content.contains(this.query))
                result.add(mail);
        }
        return result;
    }
}
