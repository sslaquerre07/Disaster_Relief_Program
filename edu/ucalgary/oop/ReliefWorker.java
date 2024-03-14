package edu.ucalgary.oop;

public class ReliefWorker extends UserInteraction{
    private Integer workerID;
    private static Integer count = 0;

    public ReliefWorker(){
        this.workerID = count;
        count++;
    } 

    public DisasterVictim enterVictim(DisasterVictim victim, Location location){
        //Align all of this input with some database access and this interface should work great!
        return victim;
    }

    @Override
    public <T> void getTerminalInput() {
        //Through terminal input, do the following
        //1. General Info
        //2. Relationships
        //3. Medical Records and the Location they were administered in
    }
}
