package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Sort_Priority extends ISort {
    @Override
    public ArrayList<Mail> sort(ArrayList<Mail> mails) {
        PriorityQueue<Mail> pq = new PriorityQueue<Mail>(new SortByPriority());
        for (Mail mail : mails)
            pq.add(mail);
        mails.clear();
        int size = pq.size();
        for (int i = 0; i < size; i++)
            mails.add(pq.remove());
        return mails;
    }

    class SortByPriority implements Comparator<Mail> {

        @Override
        public int compare(Mail mail1, Mail mail2) {
            return mail1.getPriority()-mail2.getPriority();
        }
    }
}
