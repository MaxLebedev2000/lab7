package max.lab7.server.users;

public class Connection {

    private int id;
    private Type type;
    private String login;

    public Connection(int id){
        this.type = Type.UNKNOWN;
    }

    public int id(){
        return id;
    }

    public Type type(){
        return type;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return login;
    }


}
