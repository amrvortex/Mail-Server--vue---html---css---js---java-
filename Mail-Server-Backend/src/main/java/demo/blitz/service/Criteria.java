package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public interface Criteria {
    public ArrayList<Mail> meetCriteria (ArrayList<Mail> mails);
}
