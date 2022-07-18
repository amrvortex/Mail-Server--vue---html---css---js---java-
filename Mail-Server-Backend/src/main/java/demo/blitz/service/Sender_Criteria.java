package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Sender_Criteria implements Criteria {

    private String sender;

    public Sender_Criteria(String sender){
        this.sender = sender;
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> senderMails = new ArrayList<Mail>();
        for (Mail mail : mails){
            if (mail.getSender().equalsIgnoreCase(this.sender))
                senderMails.add(mail);
        }
        return senderMails;
    }

}
