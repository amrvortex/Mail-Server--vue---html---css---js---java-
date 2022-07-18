package demo.blitz.service;

import demo.blitz.model.Mail;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.FileHandler;

public class Search_Attachments extends ISearch{

    private String query;

    public Search_Attachments(String query) {
        this.query = query.toLowerCase();
    }

    @Override
    public ArrayList<Mail> search(ArrayList<Mail> mails) {
        ArrayList<Mail> result = new ArrayList<Mail>();
        for (Mail mail : mails) {
            for (String attachment : mail.getAttachments()) {
                File file = new File(attachment);
                String content = file.getName().toLowerCase();
                if (content.contains(this.query)) {
                    result.add(mail);
                    break;
                }
            }
        }
        return result;
    }

}
