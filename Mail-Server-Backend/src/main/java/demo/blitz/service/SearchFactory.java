package demo.blitz.service;

public class SearchFactory {

    private ISearch search;

    public ISearch create (String type, String query) {
        if (type.equals("Date")){
            this.search = new Search_Date(query);
        }
        else if (type.equals("Priority")){
            this.search = new Search_Priority(query);
        }
        else if (type.equals("Subject")){
            this.search = new Search_Subject(query);
        }
        else if (type.equals("Body")){
            this.search = new Search_Body(query);
        }
        else if (type.equals("Sender")){
            this.search = new Search_Sender(query);
        }
        else if (type.equals("Receivers")){
            this.search = new Search_Receivers(query);
        }
        else if (type.equals("Attachments")){
            this.search = new Search_Attachments(query);
        }
        return this.search;
    }
}
