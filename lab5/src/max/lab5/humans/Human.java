package max.lab5.humans;

public class Human extends Options implements IHuman{
        private Eyes eyes;
        private Hair hair;

        private double money;
        private double bribe;
        public Human(){}
        /**------------------------------------------------------------------------------------------------------------ */
        public Human (String name, Status status){
            super(name, status);
            this.eyes = Eyes.Blue;
            this.hair = Hair.Blond;
        }
        /**------------------------------------------------------------------------------------------------------------ */
        public Human (String name, Status status,double bribe){
            super(name, status);
             this.bribe = bribe;
            this.eyes = Eyes.Blue;
            this.hair = Hair.Blond;
        }
        /**------------------------------------------------------------------------------------------------------------ */
        public Human (String name, Status status, double height, double headsize, double nosesize, Eyes eyes, Hair hair,double money){
            super(name, status, height, headsize, nosesize);
            this.eyes = eyes;
            this.hair = hair;
            this.money = money;
        }


        public Eyes getEyes(){ return eyes; }
        public Hair getHair(){
            return hair;
        }
        public double getMoney()throws MoneyException {
            if (money<0) throw new MoneyException("Количество денег у "+ this.getName()+" не может быть меньше нуля ", this.money);
        return money; }
        public double getBribe() throws BribePlusException {
            if (bribe<0) throw new BribePlusException("Количество денег у "+ this.getName()+" не может быть меньше нуля ", this.bribe);return bribe; }
        public void sayPhrase( String phrase) {System.out.println(this.getStatus()+ " " + this.getName() +": "+ phrase); }

        public boolean Compare(Card first, Card second){
            return first.equals(second);
        }
        public void sayDifference(Card first, Card  second){
            if (first.getName().equals(second.getName())) this.sayPhrase("Имена разные");
            if (first.getHeight() != second.getHeight()) this.sayPhrase("Разный рост");
            if (first.getHeadSize() != second.getHeadSize()) this.sayPhrase("Разный размер головы");
            if (first.getNoseSize() != second.getNoseSize()) this.sayPhrase("Разный размер носа");
            this.sayPhrase("Посмотрим на фотографии");
            if (first.getPhoto().getEyes() != second.getPhoto().getEyes()) this.sayPhrase("Разные глаза");
            if (first.getPhoto().getHair() != second.getPhoto().getHair()) this.sayPhrase("Разные волосы");
        }
    }