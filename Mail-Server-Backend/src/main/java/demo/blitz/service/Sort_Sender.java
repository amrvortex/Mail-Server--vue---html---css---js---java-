package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;
import java.util.Comparator;

public class Sort_Sender extends ISort {
    @Override
    public ArrayList<Mail> sort(ArrayList<Mail> mails) {
        mails.sort(new SortBySender());
        return mails;
    }

    class SortBySender implements Comparator<Mail> {

        @Override
        public int compare(Mail mail1, Mail mail2) {
            return mail1.getSender().compareTo(mail2.getSender());
        }
    }
}
