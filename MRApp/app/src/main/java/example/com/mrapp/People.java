package example.com.mrapp;


public class People {

    public int id;
    public String names;

    public People(){}

    public People(int id, String names){
        this.id = id;
        this.names = names;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNames(){
        return this.names;
    }

    public void setNames(String names){
        this.names = names;
    }
}
