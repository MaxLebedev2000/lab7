package max.lab5.humans;

public class MoneyException extends Exception {
    private  double money;
    public  double getMoney(){return money;}
    public MoneyException(String message, double money) {
        super(message);
        this.money = money;
    }
}
