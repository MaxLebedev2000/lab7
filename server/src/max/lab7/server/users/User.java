package max.lab7.server.users;

public class User {

    public final String login;
    public final String email;
    private String password;


    public User(String login, String email, String password){
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }


}
