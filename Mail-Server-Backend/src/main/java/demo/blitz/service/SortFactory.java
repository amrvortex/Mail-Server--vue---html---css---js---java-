package demo.blitz.service;

public class SortFactory {

    private ISort sort;

    public ISort create (String type){
        if (type.equals("3")){
            this.sort = new Sort_Date();
        }
        else if (type.equals("4")){
            this.sort = new Sort_Priority();
        }
        else if (type.equals("1")){
            this.sort = new Sort_Subject();
        }
        else if (type.equals("2")){
            this.sort = new Sort_Body();
        }
        else if (type.equals("0")){
            this.sort = new Sort_Sender();
        }
        return this.sort;
    }
}
