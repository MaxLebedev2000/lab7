package max.lab5.humans;

public class Policeman extends Human implements IPoliceman{
    /**------------------------------------------------------------------------------------------------------------ */
    public Policeman(String name, Status status, double bribe){
        super(name, status, bribe);
    }
    /**------------------------------------------------------------------------------------------------------------ */
    public Policeman(String name, Status status){
        super(name, status);
    }
    public Card setCard(Human h){
        return new Card(50,100,h, "");
    }
    /**------------------------------------------------------------------------------------------------------------ */
    @Override
    public boolean Compare(Card first, Card second){
        return ( (first.getHeadSize() == second.getHeadSize()) && (first.getHeight() == second.getHeight()) && (first.getNoseSize() == second.getNoseSize()));
        }
    @Override
    public boolean askBribe(double bribe,double money) throws  BribeException
    {System.out.println("Давай ты мне " + (int)bribe +" баксов и иди на все 4 стороны, а вместо тебя посажу какого-нибудь левого бедолагу.");
        if (bribe <= money) throw new  BribeException("Ну короче денег у Незнайки больше, чем хочет Мигль ",money,bribe);
    return (bribe > money);}
}