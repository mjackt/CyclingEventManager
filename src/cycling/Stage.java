package cycling;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import cycling.StageType;

/**
 * Stage --- A class to represent a stage in a race. Can contain intermediate sprints
 * and climb checkpoints.
 * Contains attributes:
 * segments(ArryList<Segment>) - All the segments that are in a stage
 * type(StageType) - An enum which can be FLAT,MEDIUM_MOUNTAIN,HIGH_MOUNTAIN,TT
 * id(int) - Unique to each stage
 * name(String) - The name of the stage
 * desc(String) - The description of the stage
 * length(double) - The length of the stage
 * startTime(LocalDateTime) - The date and time at which the stage starts
 * state(String) - Represents if the stage is in the prep phase or waiting for results
 * finishResults(ArrayList<RiderResult>) - An ArrayList of the times at which riders finished the stage
 * startTimes(ArrayList<RiderResult>) - An ArrayList of the times at which riders started the stage
 * 
 * @author Matt Trenchard
 * @version 1.0
 */
public class Stage implements Serializable{
    private ArrayList<Segment> segments;
    /**
     * Gets all the segments from the stage
     * @return All the segments in the stage
     */
    public ArrayList<Segment> getSegments(){
        return segments;
    }
    private StageType type;
    /**
     * Gets the type of the stage
     * @return The stage type
     */
    public StageType getType(){
        return type;
    }
    private int id;
    /**
     * Gets the ID of the stage
     * @return The stage ID
     */
    public int getId(){
        return id;
    }
    private String name;
    /**
     * Gets the name of the stage
     * @return Name of the stage
     */
    public String getName(){
        return name;
    }
    private String desc;
    /**
     * Gets the stage description
     * @return The stage description
     */
    public String getDesc(){
        return desc;
    }
    private double length;
    /**
     * Gets the length of the stage
     * @return The stage length
     */
    public double getLength(){
        return length;
    }
    private LocalDateTime startTime;
    /**
     * Gets the date and time of the stage start
     * @return Start time of the stage
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }
    private String state;
    /**
     * Gets the current stage state
     * @return The stage's state. Either "wait" or "prep".
     */
    public String getState(){
        return state;
    }
    private ArrayList<RiderResult> finishResults;
    /**
     * Gets all the finish times for the stage
     * @return ArrayList of finish times
     */
    public ArrayList<RiderResult> getFinishResults(){
        return finishResults;
    }
    private ArrayList<RiderResult> startTimes;
    /**
     * Gets all the rider start times for the stage
     * @return ArrayList of start times
     */
    public ArrayList<RiderResult> getStartTimes(){
        return startTimes;
    }

    /**
     * Used to add a start time of a rider to the stage. It is inserted based on the time.
     * @param result   The rider result being added
     */
    public void insertStartTime(RiderResult result){
        if(startTimes.size()==0){
            startTimes.add(result);
        }
        else if(startTimes.get(startTimes.size()-1).getTime().isBefore(result.getTime())){//If the time to be added is the slowest it is appended
            startTimes.add(result);
        }
        else if(startTimes.get(startTimes.size()-1).getTime().equals(result.getTime())){//If the time is equal to the slowest it is appened
            startTimes.add(result);
        }
        else{
            for(int i=0;i<startTimes.size();i++){//Iterates through all current times until a time that is equal or after the time to be added is found
                if(result.getTime().isBefore(startTimes.get(i).getTime())){
                    startTimes.add(i,result);
                    break;
                }
                else if(result.getTime().equals(startTimes.get(i).getTime())){
                    startTimes.add(i,result);
                    break;
                }
            }
        }
    }

    /**
     * Used to find a rider's result in a stage
     * @param riderId   The ID of the rider whose result you want to find
     * @return The RiderResult object corresponding to the riderId.
     *         If no result is found a null value is returned
     */
    public RiderResult findRiderResult(int riderId){
        for(int i=0;i<finishResults.size();i++){
            if(finishResults.get(i).getRiderId()==riderId){
                return finishResults.get(i);
            }
        }
        return null;
    }

    /**
     * Used to find a rider's start time in a stage
     * @param riderId   The ID of the rider whose start time you want to find
     * @return The RiderResult object corresponding to the riderId.
     *         If no start time is found a null value is returned
     */
    public RiderResult findRiderStart(int riderId){
        for(int i=0;i<startTimes.size();i++){
            if(startTimes.get(i).getRiderId()==riderId){
                return startTimes.get(i);
            }
        }
        return null;
    }

    /**
     * Inserts a rider's finishing result. Result is inserted based on time.
     * @param result   The finishing result to be inserted
     */
    public void insertFinish(RiderResult result){
        if(finishResults.size()==0){
            finishResults.add(result);
        }
        else if(finishResults.get(finishResults.size()-1).getTime().isBefore(result.getTime())){//If the time to be added is the slowest it is appended
            finishResults.add(result);
        }
        else if(finishResults.get(finishResults.size()-1).getTime().equals(result.getTime())){//If the time is equal to the slowest it is appened
            finishResults.add(result);
        }
        else{
            for(int i=0;i<finishResults.size();i++){//Iterates through all current times until a time that is equal or after the time to be added is found
                if(result.getTime().isBefore(finishResults.get(i).getTime())){
                    finishResults.add(i,result);
                    break;
                }
                else if(result.getTime().equals(finishResults.get(i).getTime())){
                    finishResults.add(i,result);
                    break;
                }
            }
        }
    }

    /**
     * Removes a rider's start time
     * @param result   The start result to be removed
     */
    public void removeRiderStartTime(RiderResult result){
        startTimes.remove(result);
    }

    /**
     * Removes a rider's finish time
     * @param result   The finish time to be removed
     */
    public void removeFinishResult(RiderResult result){
        finishResults.remove(result);
    }

    /**
     * Adds a segment to the stage. Segments are inserted based on their location within the stage
     * @param segment   The segment to be added
     */
    public void insertSegment(Segment segment){
        if (segments.size()==0){
            segments.add(segment);
        }
        else{
            boolean sorted=false;
            for (int i=0;i<segments.size();i++){//Inserts with insertion sort logic
                if (segment.getLocation()<segments.get(i).getLocation()){
                    segments.add(i,segment);
                    sorted=true;
                    break;
                }
            }
            if(sorted==false){//If it has not yet been added it means it is the last segment so is appended
                segments.add(segment);
            }
        }
    }

    /**
     * Chnages the stage state from preperation to waiting for results
     */
    public void concludePrep(){
        state="wait";
    }

    /**
     * Used to find a segment within a stage
     * @param id   The ID of the segment you want to find
     * @return If the segment is found, the segment object is returned.
     *         If not, a null value is returned
     */
    public Segment findSegment(int id){
        ArrayList<Segment> segments=getSegments();//List of all created segments
        for(int i=0; i<segments.size();i++){
            if (segments.get(i).getId()==id){//Comparing search id and id of segment being iterated
                Segment segment=segments.get(i);
                return segment;
            }
        }
        return null;
    }

    /**
     * Removes a segment from a stage
     * @param segment   The segment to be removed
     */
    public void removeSegment(Segment segment){
        segments.remove(segment);
    }


    /**
     * Creates a new stage
     * @param id   ID of the stage
     * @param name   Name of the stage
     * @param desc   Description of the stage
     * @param length    Length of the stage
     * @param startTime   The date and time of the stage start
     * @param type   The stage type
     */
    public Stage(int id, String name, String desc, double length, LocalDateTime startTime, StageType type){
        this.id=id;
        this.desc=desc;
        this.name=name;
        this.length=length;
        this.startTime=startTime;
        this.type=type;
        segments= new ArrayList<Segment>();
        finishResults= new ArrayList<RiderResult>();
        startTimes=new ArrayList<RiderResult>();
        state="prep";
    }
}
