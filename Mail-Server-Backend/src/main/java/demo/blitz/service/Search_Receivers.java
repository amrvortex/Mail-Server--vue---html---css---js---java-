package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Search_Receivers extends ISearch {

    private String query;

    @Override
    public ArrayList<Mail> search(ArrayList<Mail> mails) {
        for (int i = 0; i < mails.size(); i++) {
            boolean isExist = false;
            for (String Receiver : mails.get(i).getReceivers()) {
                isExist = isExist || Receiver.equalsIgnoreCase(this.query);
                if (isExist)
                    break;
            }
            if (!isExist)
                mails.remove(i);
        }
        return mails;
    }

    public Search_Receivers(String query) {
        this.query = query;
    }
}
