package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import cycling.SegmentType;

/**
 * Segment --- A class to represent an intermediate sprint or climb within a stage
 * Contains attributes:
 * id(int) - The ID of the segment
 * location(double) - The location in km of the climb peak or sprint checkpoint
 * type(SegmentType) - An enum which can be SPRINT,C4,C3,C2,C1,HC
 * avgGrad(double) - The average gradient of the segment. If it's a sprint this value will be 0
 * checkpointResults(ArrayList<RiderResult>) - A list of each riders time they reached the segment checkpoint at
 */
public class Segment implements Serializable{
    private int id;
    /**
     * Gets the ID of the segment
     * @return The segment ID
     */
    public int getId(){
        return id;
    }
    private double location;
    /**
     * Gets the location of the segment in the stage
     * @return The location of the segment
     */
    public double getLocation(){
        return location;
    }
    private SegmentType type;
    /**
     * Gets the segment type
     * @return The segment type
     */
    public SegmentType getType(){
        return type;
    }
    private double avgGrad;
    /**
     * Gets the average gradient of the segment
     * @return Average gradient of the segment
     */
    public double getAvgGrad(){
        return avgGrad;
    }

    private ArrayList<RiderResult> checkpointResults;
    /**
     * Gets all the results of riders at the segment checkpoint
     * @return ArrayList of rider's checkpoint times
     */
    public ArrayList<RiderResult> getCheckpointResults(){
        return checkpointResults;
    }

    /**
     * Adds a rider's time they reached the segment checkpoint at. Inserts based on time
     * @param result
     */
    public void insertCheckpoint(RiderResult result){
        if(checkpointResults.size()==0){
            checkpointResults.add(result);
        }
        else if (checkpointResults.get(checkpointResults.size()-1).getTime().isBefore(result.getTime())){//If the time to be added is the slowest it is appended
            checkpointResults.add(result);
        }
        else if (checkpointResults.get(checkpointResults.size()-1).getTime().equals(result.getTime())){//If the time is equal to the slowest it is appened
            checkpointResults.add(result);
        }
        else{
            for(int i=0;i<checkpointResults.size();i++){//Iterates through all current times until a time that is equal or after the time to be added is found
                if(result.getTime().isBefore(checkpointResults.get(i).getTime())){
                    checkpointResults.add(i,result);
                    break;
                }
                else if(result.getTime().equals(checkpointResults.get(i).getTime())){
                    checkpointResults.add(i,result);
                    break;
                }
            }
        }
    }

    /**
     * Removes a rider's time from the list of recorded times
     * @param result   The result to be removed
     */
    public void removeCheckpointResult(RiderResult result){
        checkpointResults.remove(result);
    }

    /**
     * Used to find a rider's result from a segment
     * @param riderId   The rider you want to find a result for
     * @return A null value if the result isn't found.
     *         If the result is found the result is returned
     */
    public RiderResult findRiderResult(int riderId){
        for(int i=0;i<checkpointResults.size();i++){
            if(checkpointResults.get(i).getRiderId()==riderId){
                return checkpointResults.get(i);
            }
        }
        return null;
    }


    /**
     * Creates a new segment. This constructor contains an avgerage gradient parameter as it is 
     * used for creating climb segments
     * @param id   The ID of the segment to be created
     * @param location   The location of the segment in the stage
     * @param avgGrad   The avergae gradient of the climb
     * @param type   The type of the climb. C4,C3,C2,C1 or HC
     */
    public Segment(int id, double location, double avgGrad, SegmentType type){
        this.id=id;
        this.location=location;
        this.avgGrad=avgGrad;
        this.type=type;
        checkpointResults= new ArrayList<RiderResult>();
    }

    /**
     * Creates a new segment. This constructor contains no avgerage gradient parameter as it is 
     * used for creating sprint segments
     * @param id   The ID of the segment to be created
     * @param location   The location of the segment in the stage
     * @param type   The type of the segment
     */
    public Segment(int id, double location, SegmentType type){
        this.id=id;
        this.location=location;
        this.type=type;
        avgGrad=0;
        checkpointResults= new ArrayList<RiderResult>();
    }
}
