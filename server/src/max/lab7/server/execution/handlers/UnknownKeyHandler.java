package max.lab7.server.execution.handlers;

import max.lab7.server.execution.KeyHandler;
import max.lab7.server.mail.MailWorker;
import max.lab7.server.mail.PasswordMaker;
import max.lab7.server.sql.JDBCWorker;
import max.lab7.server.sql.tables.UserTable;
import max.lab7.server.users.Connection;
import max.lab7.server.users.Type;
import max.lab7.server.users.User;

import java.nio.channels.SelectionKey;

public class UnknownKeyHandler extends TCPHandler implements KeyHandler {

    private MailWorker mail;
    private UserTable users;

    public UnknownKeyHandler(){
        this.mail = new MailWorker();
        this.users = JDBCWorker.instance().getUsers();
    }


    @Override
    public void handle(SelectionKey key) throws Exception {
        String come = super.receive(key);
        String[] array = come.split(";");
        if (array.length != 3){
            super.send(key, "incorrect");
            return;
        }
        String respond;
        if (array[0].trim().equals("signup")){

            String login = array[1];
            String email = array[2];
            if (this.users.exists(new User(login, email, ""))){
                respond = "userExists";
            } else {
                String pass = PasswordMaker.getRandomString();
                String password = PasswordMaker.getHexDigest(pass);
                mail.send("Регистрация нового пользователя",
                        "Пароль: " + pass + ";\n" +
                                "Логин: " + login + ";",
                        email);
                users.put(new User(login, email, password));
                respond = "regdone";
                ((Connection)key.attachment()).setLogin(login);
                ((Connection)key.attachment()).setType(Type.COMMANDS);
            }

            super.send(key, respond);

        } else if (array[0].trim().equals("signin")){

            String login = array[1];
            String password = array[2];

            if (users.exists(new User(login, "", ""))){

                if (users.passwordCorrect(login, PasswordMaker.getHexDigest(password))){

                    ((Connection)key.attachment()).setType(Type.COMMANDS);
                    ((Connection)key.attachment()).setLogin(login);
                    respond = "logdone";

                } else {
                    respond = "incorrectPassword";
                }

            } else {
                respond = "noSuchUser";
            }

            super.send(key, respond);


        } else {
            respond = "incorrect";
            super.send(key, respond);
        }

    }
}
