package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class Mail_Manipulation_Service {

    Load_Data loader = new Load_Data();
    Criteria criteria;
    IFunctionality functionality;
    FunctionalityFactory functionalityFactory = new FunctionalityFactory();

    public ArrayList<Mail> display(String path, int page) throws Exception {
        ArrayList<Mail> mails = loader.getAll(path);
        return loader.getPage(mails,page);
    }
    public ArrayList<Mail> filter(String path, String type, String[] query, int page) throws Exception {
        ArrayList<Mail> mails = loader.getAll(path);
        if (type.equals("Subject")){
            criteria = new Subject_Criteria(query[0]);
            return criteria.meetCriteria(mails);
        }
        else if (type.equals("Sender")){
            criteria = new Sender_Criteria(query[0]);
            return criteria.meetCriteria(mails);
        }
        else if (type.equals("And")){
            Criteria firstCriteria = new Sender_Criteria(query[0]);
            Criteria secondCriteria = new Subject_Criteria(query[1]);
            criteria = new AndCriteria(firstCriteria, secondCriteria);
            return loader.getPage(criteria.meetCriteria(mails),page);
        }
        else
            return mails;
    }
    public ArrayList<Mail> manipulate(String path, String[] arr, int page) throws Exception {
        ArrayList<Mail> mails = loader.getAll(path);
        for (int i = 0; i < arr.length-1; i += 3) {
            functionality = functionalityFactory.create(arr[i],arr[i+1],arr[i+2]);
            mails = functionality.manipulate(mails);
        }
        return loader.getPage(mails,page);
    }



}
