package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public abstract class ISort implements IFunctionality{
    @Override
    public ArrayList<Mail> manipulate(ArrayList<Mail> mails) {
        return sort(mails);
    }

    public abstract ArrayList<Mail> sort(ArrayList<Mail> mails);
}
