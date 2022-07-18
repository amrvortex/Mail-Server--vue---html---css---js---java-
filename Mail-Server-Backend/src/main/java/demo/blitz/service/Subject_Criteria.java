package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Subject_Criteria implements Criteria {

    private String subject;

    public Subject_Criteria(String subject){
        this.subject = subject.toLowerCase();
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> subjectMails = new ArrayList<Mail>();
        for (Mail mail : mails){
            String content = mail.getSubject().toLowerCase();
            if (content.contains(this.subject))
                subjectMails.add(mail);
        }
        return subjectMails;
    }

}
