package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;
import java.util.Comparator;

public class Sort_Body extends ISort{

    @Override
    public ArrayList<Mail> sort(ArrayList<Mail> mails) {
        mails.sort(new SortByBody());
        return mails;
    }

    class SortByBody implements Comparator<Mail> {

        @Override
        public int compare(Mail mail1, Mail mail2) {
            return mail1.getBody().compareTo(mail2.getBody());
        }
    }
}
