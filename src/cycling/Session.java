package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Session --- A class to store all the created teams and races as well as ID counters
 * for every object that requires a unique ID.
 * This makes the saving and loading of the portal easier as only the session object
 * within the portal has to be loaded or saved.
 * Contains the following attributes:
 * allTeams (ArrayList<Team>) - An ArrayList of every team created
 * allRaces (ArrayList<Race>) - An ArrayList of every created race
 * nextTeamId (int) - The next ID to be assigned to a team
 * nextRiderId(int) - The next ID to be assigned to a rider
 * nextRaceId (int) - The next ID to be assigned to a race
 * nextStageId (int) - The next ID to be assigned to a stage
 * nextSegmentId (int) - The next ID to be assigned to a segment
 * 
 * @author Matt Trenchard
 * @version 1.1
 */

public class Session implements Serializable{
    private ArrayList<Team> allTeams;
    /**
     * Gets an ArrayList of every created team.
     * @return ArrayList of every created team.
     */
    public ArrayList<Team> getAllTeams(){
        return allTeams;
    }
    private int nextTeamId;
    /**
     * Gets the next team ID to be used.
     * @return Next team ID to be used.
     */
    public int getNextTeamId(){
        return nextTeamId;
    }

    private int nextRiderId;
    /**
     * Gets the next rider ID to be used.
     * @return Next rider ID to be used.
     */
    public int getNextRiderId(){
        return nextRiderId;
    }

    private ArrayList<Race> allRaces;
    /**
     * Gets every race in the system
     * @return ArrayList of every race in the system
     */
    public ArrayList<Race> getAllRaces(){
        return allRaces;
    }
    private int nextRaceId;
    /**
     * Gets the next unused race ID
     * @return Next unused race ID
     */
    public int getNextRaceId(){
        return nextRaceId;
    }

    private int nextStageId;
    /**
     * Gets the next unused stage ID
     * @return Next unused stage ID
     */
    public int getNextStageId(){
        return nextStageId;
    }

    private int nextSegmentId;
    /**
     * Gets the next unused segment ID
     * @return Next unused segment ID
     */
    public int getNextSegmentId(){
        return nextSegmentId;
    }

    /**
     * Adds a team to the ArrayList of teams when one is created
     * @param team   The team that has been created.
     */
    public void appendTeam(Team team){
        allTeams.add(team);
    }

    /**
     * Removes a team from the ArrayList of created teams.
     * @param team   The team to be removed.
     */
    public void deleteTeam(Team team){
        allTeams.remove(team);
    }

    /**
     * Adds a race to the list of created races
     * @param race   The race to be added
     */
    public void appendRace(Race race){
        allRaces.add(race);
    }

    /**
     * Removes a race from the list of created races
     * @param race   The race to be removed
     */
    public void removeRace(Race race){
        allRaces.remove(race);
    }

    /**
     * Increments the team ID counter
     */
    public void incrementTeamId(){
        nextTeamId++;
    }

    /**
     * Increments the rider ID counter
     */
    public void incrementRiderId(){
        nextRiderId++;
    }

    /**
     * Increments the race ID counter
     */
    public void incrementRaceId(){
        nextRaceId++;
    }

    /**
     * Increments the stage ID counter
     */
    public void incrementStageId(){
        nextStageId++;
    }

    /**
     * Increments the segment ID counter
     */
    public void incrementSegmentId(){
        nextSegmentId++;
    }
    /**
     * Creates a new session with an empty list of teams and races and ID counters of 1
     */
    public Session(){
        allTeams= new ArrayList<Team>();
        nextTeamId=1;
        nextRiderId=1;
        nextRaceId=1;
        nextSegmentId=1;
        nextStageId=1;
        allRaces= new ArrayList<Race>();
    }
}