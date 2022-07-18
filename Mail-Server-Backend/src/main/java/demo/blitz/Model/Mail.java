package demo.blitz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;


public class Mail {
    private String Subject,Date,Body,Sender,ID;
    private int Priority;
    private String [] Attachments , Receivers;

    public Mail(@JsonProperty("Subject") String subject,
                @JsonProperty("Date") String date,
                @JsonProperty("Body") String body,
                @JsonProperty("Sender") String sender,
                @JsonProperty("Priority") int priority,
                @JsonProperty("Attachments") String[] attachments,
                @JsonProperty("Receivers") String[] receivers) {
        this.Subject = subject;
        this.Date = date;
        this.Body = body;
        this.Sender = sender;
        this.Priority = priority;
        this.Attachments = attachments;
        this.Receivers = receivers;
        this.ID = "";
    }

    public void save_json(String path) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("Subject", this.Subject);
        obj.put("Date", this.Date);
        obj.put("Body", this.Body);
        obj.put("Priority", this.Priority);
        obj.put("Sender", this.Sender);
        obj.put("ID",this.ID);
        JSONArray receivers = new JSONArray();
        Collections.addAll(receivers, getReceivers());
        obj.put("Receivers", receivers);
        JSONArray attachments = new JSONArray();
        Collections.addAll(attachments, getAttachments());
        obj.put("Attachments", attachments);
        FileWriter fw_JSON = new FileWriter(path);
        fw_JSON.write(obj.toJSONString());
        fw_JSON.flush();
        fw_JSON.close();
    }

    public static Mail parseMail(String path) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(path);
        Object obj = jsonParser.parse(reader);
        JSONObject mailObject = (JSONObject) obj;
        JSONArray attachmentsList = (JSONArray)mailObject.get("Attachments");
        String[] attachments = new String[attachmentsList.size()];
        for (int i = 0 ; i < attachmentsList.size();i++){
            attachments[i]= (String)attachmentsList.get(i);
        }
        JSONArray receiversList = (JSONArray)mailObject.get("Receivers");
        String[] receivers = new String[receiversList.size()];
        for (int i = 0 ; i < receiversList.size();i++){
            receivers[i]= (String)receiversList.get(i);
        }
        Mail mail = new Mail((String)mailObject.get("Subject"),
                (String)mailObject.get("Date"),
                (String)mailObject.get("Body"),
                (String)mailObject.get("Sender"),
                Integer.parseInt(mailObject.get("Priority").toString()),
                attachments,
                receivers);
        //mail.setID(Integer.parseInt(mailObject.get("ID").toString()));
        reader.close();
        return mail;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public int getPriority() {
        return Priority;
    }

    public String[] getAttachments() {
        if (this.Attachments==null) return new String[]{};
        else return Attachments;
    }

    public void changeElementAttachments(int index,String new_element){
        this.Attachments[index] = new_element;
    }

    public void setAttachments(String[] attachments) {
        Attachments = attachments;
    }

    public String[] getReceivers() {
        if (this.Receivers==null) return new String[]{};
        else return Receivers;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
