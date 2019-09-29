package max.lab5.humans;

public interface IPoliceman extends IHuman {
    Card setCard(Human h);
    boolean askBribe(double bribe, double money);}
