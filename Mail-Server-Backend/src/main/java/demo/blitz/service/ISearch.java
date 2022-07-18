package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public abstract class ISearch implements IFunctionality{

    @Override
    public ArrayList<Mail> manipulate(ArrayList<Mail> mails) {
        return search(mails);
    }

    public abstract ArrayList<Mail> search(ArrayList<Mail> mails);
}
