package demo.blitz.service;

import demo.blitz.model.Mail;

import java.util.ArrayList;

public class AndCriteria implements Criteria{

    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public ArrayList<Mail> meetCriteria(ArrayList<Mail> mails) {
        ArrayList<Mail> firstCriteriaMails = criteria.meetCriteria(mails);
        return otherCriteria.meetCriteria(firstCriteriaMails);
    }
}
