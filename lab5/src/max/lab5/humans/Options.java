package max.lab5.humans;

public abstract class Options{
    private String name;
    private Status status;
    private double height;
    private double headsize;
    private double nosesize;
    public enum Status {ChiefPoliceOfficer, OfficerAssistant, Jailbird, Suspect}


    public Status getStatus(){ return status; }
    public String getName(){ return name; }
    public double getHeight(){
        return height;
    }
    public double getHeadSize(){
        return headsize;
    }
    public double getNoseSize(){
        return this.nosesize;
    }

    public Options(){}
    /**------------------------------------------------------------------------------------------------------------ */
    public Options(String name, Status status){
        this.name = name;
        this.status = status; }
    /**------------------------------------------------------------------------------------------------------------ */
    public Options (String name, Status status, double height, double headsize, double nosesize){
        this.name = name;
        this.status = status;
        this.height = height;
        this.headsize = headsize;
        this.nosesize = nosesize;
         }
    /**------------------------------------------------------------------------------------------------------------ */
}
