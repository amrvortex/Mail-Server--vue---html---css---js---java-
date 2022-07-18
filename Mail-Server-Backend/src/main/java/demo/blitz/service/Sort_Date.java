package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;
import java.util.Comparator;

public class Sort_Date extends ISort{
    @Override
    public ArrayList<Mail> sort(ArrayList<Mail> mails) {
        mails.sort(new SortByDate());
        return mails;
    }

    class SortByDate implements Comparator<Mail> {

        @Override
        public int compare(Mail mail1, Mail mail2) {
            return mail1.getDate().compareTo(mail2.getDate());
        }
    }
}
