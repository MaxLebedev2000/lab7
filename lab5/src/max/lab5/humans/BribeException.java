package max.lab5.humans;

public class BribeException extends RuntimeException {
    private  double money;
    public  double getMoney(){return money;}
    private  double bribe;
    public  double getBribe(){return bribe;}
    public BribeException(String message, double money, double bribe) {
        super(message);
        this.money = money;
        this.bribe = bribe;
    }
}
