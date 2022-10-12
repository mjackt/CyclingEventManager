package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * RiderResult --- A class to hold a riders result at a start, checkpoint or finish line.
 * Contains the following attributes:
 * riderId(int) - The ID of the rider whom the time corresponds to
 * time(LocalTime) - The time at which the rider reached the start,checkpoint or finish.
 * 
 * @author Matt Trenchard
 * @version 1.0
 */
public class RiderResult implements Serializable{
    private int riderId;
    /**
     * Gets the id of the rider whos result is stored
     * @return The rider's ID
     */
    public int getRiderId(){
        return riderId;
    }
    private LocalTime time;
    /**
     * Gets the time stored in the result.
     * @return The time stored
     */
    public LocalTime getTime(){
        return time;
    }

    /**
     * Creates a new result
     * @param riderId ID of the rider to create a result for
     * @param time The time for the result
     */
    public RiderResult(int riderId,LocalTime time){
        this.riderId=riderId;
        this.time=time;
    }
}
