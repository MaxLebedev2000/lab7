package max.lab5.humans;

public class BribePlusException extends Exception {
        private  double bribe;
        public  double getBribe(){return bribe;}
        public BribePlusException(String message, double bribe) {
            super(message);
            this.bribe = bribe;
        }
}
