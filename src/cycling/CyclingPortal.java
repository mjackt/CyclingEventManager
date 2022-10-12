package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.io.*;

/**
 * CyclingPortal --- A class implementing MiniCyclingPortalInterface.
 * Contains the attribute:
 * Session - A Session object containing all the created objects and other trackers.
 * 
 * @author Matt Trenchard
 * @version 1.0
 */

public class CyclingPortal implements MiniCyclingPortalInterface{
    private Session session;

    /**
     * Used to find a team from the list of created teams.
     * @param id   The id of the team you wish to find.
     * @return If the team is found a team object with matching id is returned.
     *         Otherwise a null value is returned
     */
    public Team findTeam(int id){
        ArrayList<Team> teams=session.getAllTeams();//List of all created teams
        for(int i=0; i<teams.size();i++){
            if (teams.get(i).getId()==id){//Comparing search id and id of team being iterated
                Team team=teams.get(i);
                assert(team.getId()==id);
                return team;
            }
        }
        return null;
    }

    /**
     * Used to see if a rider exists in the system.
     * 
     * @param riderId   The id the rider you want to search for
     * @return A boolean value. True if the rider exists. False if not.
     */
    public boolean doesRiderExist(int riderId){
        boolean foundRider=false;
        for (int i=0;i<session.getAllTeams().size();i++){//Iterates through all the teams in system
            if (foundRider){
                break;
            }
            Team teamToSearch=session.getAllTeams().get(i);
            for (int j=0;j<teamToSearch.getRiders().size();j++){//Iterates through the riders riding for the team being searched
                if (teamToSearch.getRiders().get(j).getId()==riderId){
                    foundRider=true;
                    break;
                }
            }
        }
        return foundRider;
    }

    /**
     * Used to find a race in the system from a raceId
     * @param id   The ID of the race you want to find
     * @return If the race exists, the race object with the correspnding ID.
     *         Otherwise a null value is returned.
     */
    public Race findRace(int id){
        ArrayList<Race> races=session.getAllRaces();//List of all created races
        for(int i=0; i<races.size();i++){
            if (races.get(i).getId()==id){//Comparing search id and id of race being iterated
                Race race=races.get(i);
                assert(race.getId()==id);
                return race;
            }
        }
        return null;
    }

    /**
     * Used to find a stage in the system from a stage ID
     * @param stageId   The ID of the stage you want to find.
     * @return If the stage exists, the stage object corresponding the stageId is returned
     *         If not, a null value is returned
     */
    public Stage findStageInRace(int stageId){
        ArrayList<Race> races=session.getAllRaces();
        for (int i=0;i<races.size();i++){//Iterates through every created race
            if (races.get(i).findStage(stageId)!=null){//Utilises race's findStage method to see if the stage is in the race being searched
                return races.get(i).findStage(stageId);
            }
        }
        return null;
    }

    /**
     * Used to find a segment in the system with a segment ID
     * @param segmentId   The ID of the segment you want to find
     * @return If the segment exists the segment object is returned.
     *         If not, a null value is returned
     */
    public Segment findSegmentInStage(int segmentId){
        ArrayList<Race> races=session.getAllRaces();
        for (int i=0;i<races.size();i++){//Iterates through all the races in the system
            ArrayList<Stage> stages=races.get(i).getAllStages();
            for (int j=0;j<stages.size();j++){//Iterates through all the stages within race i
                Segment segment=stages.get(j).findSegment(segmentId);//Utilises stage's method findSegment to see if the segment is in stage j
                if(segment!=null){
                    return segment;
                }
            }
        }
        return null;
    }

    /**
     * Creates a team with the name and description specified
     * 
     * @param name   The name of the team
     * @param description   The description of the team
     * @return The ID of the created team
     * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the new name is null, empty, has more than
	 *                              30 characters.
     */
    public int createTeam(String name, String description) throws IllegalNameException,InvalidNameException{
        for(int i=0;i<session.getAllTeams().size();i++){
            if(session.getAllTeams().get(i).getName().equals(name)){//Searching all created teams to check for name conflicts
                throw new IllegalNameException("Name already used");
            }
        }
        if(name==null || name.equals("") || name.length()>30 || name.contains(" ")){
            throw new InvalidNameException("Invalid name");
        }
        int nextId=session.getNextTeamId();//Fetches the next unused team ID
        session.incrementTeamId();//Increments ID counter to ensure uniqueness
        session.appendTeam(new Team(name,description,nextId));//Adds new team to list of created teams
        return nextId;
        }

    /**
     * Removes a team from the list of created teams.
     * @param teamID   The ID of the team to be removed
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
     */
    public void removeTeam(int teamId) throws IDNotRecognisedException{
        Team team=findTeam(teamId);
        if (team==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        session.deleteTeam(team);
        assert(findTeam(teamId)==null);
    }

    /**
     * Get the list of teams created.
	 * @return The list of IDs of the created teams.
     */
    public int[] getTeams(){
        ArrayList<Team> teams=session.getAllTeams();
        int[] teamIds = new int[teams.size()];
        for (int i=0;i<teams.size();i++){//Fetches ID of every created team and puts into an array
            teamIds[i]=teams.get(i).getId();
        }
        return teamIds;
    }
	/**
	 * Creates a rider with the specified parameters
	 * 
	 * @param teamID   The ID rider's team.
	 * @param name     The name of the rider.
	 * @param yearOfBirth   The year of birth of the rider.
	 * @return The ID of the rider in the system.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 * @throws IllegalArgumentException If the name of the rider is null or the year
	 *                                  of birth is less than 1900.
	 */
    public int createRider(int teamId, String name, int yearOfBirth) throws IDNotRecognisedException,IllegalArgumentException{
        Team team=findTeam(teamId);
        if (team==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        else if (name==null || yearOfBirth<1900){
            throw new IllegalArgumentException("Invalid attributes");
        }
        int nextId=session.getNextRiderId();
        session.incrementRiderId();
        team.appendRider(new Rider(name, yearOfBirth, nextId));//Adds new rider to system
        return nextId;
    }

    /**
	 * Removes a rider.
	 * @param riderId   The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any rider in the
	 *                                  system.
	 */
    public void removeRider(int riderId) throws IDNotRecognisedException{
        ArrayList<Team> teams=session.getAllTeams();//Every created team
        boolean found=false;
        for (int i=0;i<teams.size();i++){
            if(found){
                break;
            }
            ArrayList<Rider> riders=teams.get(i).getRiders();//Every rider in team i
            for (int j=0;j<riders.size();j++){
                if (riders.get(j).getId()==riderId){//Comparing search id against iterated id
                    for(int k=0;k<session.getAllRaces().size();k++){//Will loop through every race
                        for(int l=0;l<session.getAllRaces().get(k).getAllStages().size();l++){//Loops through every stage in a race to delete all the rider's results
                            deleteRiderResultsInStage(session.getAllRaces().get(k).getAllStages().get(l).getId(), riderId);
                        }
                    }
                    teams.get(i).deleteRider(riders.get(j));//Deleted if there is a match
                    found=true;
                    break;
                }
            }
        }
        if(found==false){
            throw new IDNotRecognisedException("ID not recognised");
        }
        assert(doesRiderExist(riderId)==false);
    }

	/**
	 * Gets every rider registered to a team.
	 * @param teamId   The ID of the team being queried.
	 * @return A list with riders' ID.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException{
        if(findTeam(teamId)==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        ArrayList<Rider> riders=findTeam(teamId).getRiders();
        int[] riderIds = new int[riders.size()];
        for (int i=0;i<riders.size();i++){//Goes through selected team and fetches every riders ID
            riderIds[i]=riders.get(i).getId();
        }
        return riderIds;
    }

    /**
     * Get the races currently created in the platform.
	 * 
	 * @return An array of race IDs in the system or an empty array if none exists.
     */
    public int[] getRaceIds(){
        ArrayList<Race> races=session.getAllRaces();
        int[] raceIds = new int[races.size()];//Creates array to hold all race IDs
        for (int i=0;i<races.size();i++){
            raceIds[i]=races.get(i).getId();
        }
        return raceIds;
    }

	/**
	 * The method creates a staged race in the platform with the given name and
	 * description.
	 * 
	 * @param name        Race's name.
	 * @param description Race's description (can be null).
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 * @return the unique ID of the created race.
	 * 
	 */
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException{
        for(int i=0;i<session.getAllRaces().size();i++){//Searching current races for any name conflict
            if(session.getAllRaces().get(i).getName().equals(name)){
                throw new IllegalNameException("Name already used");
            }
        }
        if(name==null || name.equals("") || name.length()>30 || name.contains(" ")){
            throw new InvalidNameException("Invalid name");
        }

        int nextId=session.getNextRaceId();//Gets next unique race ID
        session.incrementRaceId();
        session.appendRace(new Race(nextId,name,description));
        return nextId;
    }

    /**
	 * Get the details from a race.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A string of format - id, name, description, number of stages, total length.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException{
        Race race=findRace(raceId);
        if(race==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        ArrayList<Stage>stages=race.getAllStages();
        double length=0;
        for(int i=0;i<stages.size();i++){//Loop sums the length of all stages
            length+=stages.get(i).getLength();
        }
        String details = String.format("ID: %d\nName: %s\nDescription: %s\nNumber of Stages: %d\nLength: %.2f",race.getId(),race.getName(),race.getDesc(),stages.size(),length);
        return details;
    }

    /**
	 * The method removes the race and all its related information, i.e., stages,
	 * segments, and results.
	 * 
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    public void removeRaceById(int raceId) throws IDNotRecognisedException{
        Race race=findRace(raceId);
        if(race==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        session.removeRace(race);
        assert(findRace(raceId)==null);
    }

    /**
	 * The method queries the number of stages created for a race.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages created for the race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException{
        Race race=findRace(raceId);
        if(race==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        return race.getAllStages().size();
    }

    /**
	 * Creates a new stage and adds it to the race.
	 * 
	 * @param raceId      The race which the stage will be added to.
	 * @param stageName   An identifier name for the stage.
	 * @param description A descriptive text for the stage.
	 * @param length      Stage length in kilometres.
	 * @param startTime   The date and time in which the stage will be raced. It
	 *                    cannot be null.
	 * @param type        The type of the stage. This is used to determine the
	 *                    amount of points given to the winner.
	 * @return the unique ID of the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 * @throws IllegalNameException     If the name already exists in the platform.
	 * @throws InvalidNameException     If the new name is null, empty, has more
	 *                                  than 30.
	 * @throws InvalidLengthException   If the length is less than 5km.
	 */
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type)throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException{
        Race race=findRace(raceId);
        if (race==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        for (int i=0;i<session.getAllRaces().size();i++){
            Race raceToSearch=session.getAllRaces().get(i);
            for (int j=0;j<raceToSearch.getAllStages().size();j++){
                if (raceToSearch.getAllStages().get(j).getName().equals(stageName)){
                    throw new IllegalNameException("Name already used");
                }
            }
        }
        if(stageName==null || stageName.equals("") || stageName.length()>30 || stageName.contains(" ")){
            throw new InvalidNameException("Invalid name");
        }
        else if(length<5){
            throw new InvalidLengthException("Length must be more than 5km");
        }
        int nextId=session.getNextStageId();//Gets the next unique stage ID
        session.incrementStageId();
        race.insertStage(new Stage(nextId,stageName,description,length,startTime,type));
        return nextId;
    }

    /**
	 * Retrieves the list of stage IDs of a race.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return The list of stage IDs ordered (from first to last) by their sequence in the
	 *         race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    public  int[] getRaceStages(int raceId) throws IDNotRecognisedException{
        Race race=findRace(raceId);
        if(race==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        ArrayList<Stage> stages=race.getAllStages();
        int[] stageIds= new int[stages.size()];
        for (int i=0;i<stages.size();i++){//Loops through every stage in selected race and fetches the stage ID
            stageIds[i]=stages.get(i).getId();
        }
        return stageIds;
    }

    /**
	 * Gets the length of a stage in a race, in kilometres.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The stage's length.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    public double getStageLength(int stageId) throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if(stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        return stage.getLength();
    }

    /**
	 * Removes a stage and all its related data, i.e., segments and results.
	 * 
	 * @param stageId The ID of the stage being removed.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    public void removeStageById(int stageId) throws IDNotRecognisedException{
        ArrayList<Race> races=session.getAllRaces();
        boolean removed=false;
        for (int i=0;i<races.size();i++){
            if (races.get(i).findStage(stageId)!=null){
                races.get(i).removeStage(races.get(i).findStage(stageId));
                removed=true;
                break;
            }
        }
        if (removed==false){//If nothing has been removed it means the ID has not been found
            throw new IDNotRecognisedException("ID not recognised");
        }
        assert(findStageInRace(stageId)==null);
    }

    /**
	 * Adds a climb segment to a stage.
     * 
	 * @param stageId         The ID of the stage to which the climb segment is
	 *                        being added.
	 * @param location        The kilometre location where the climb finishes within
	 *                        the stage.
	 * @param type            The category of the climb - {@link SegmentType#C4},
	 *                        {@link SegmentType#C3}, {@link SegmentType#C2},
	 *                        {@link SegmentType#C1}, or {@link SegmentType#HC}.
	 * @param averageGradient The average gradient for the climb.
	 * @param length          The length of the climb in kilometre.
	 * @return The ID of the segment created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    segment.
	 */
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length)throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
    InvalidStageTypeException{
        Stage stage=findStageInRace(stageId);
        if(stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        else if (location>stage.getLength() || location<=0){
            throw new InvalidLocationException("Invalid peak location");
        }
        else if (stage.getState().equals("wait")){
            throw new InvalidStageStateException("Stage is out of prep phase");
        }
        else if (stage.getType()==StageType.TT){
            throw new InvalidStageTypeException("Time trials cannot contain climbs");
        }
        int id = session.getNextSegmentId();
        session.incrementSegmentId();
        stage.insertSegment(new Segment(id, location, averageGradient, type));

        return id;
    }

    /**
	 * Adds an intermediate sprint to a stage.
	 * 
	 * @param stageId  The ID of the stage to which the intermediate sprint segment
	 *                 is being added.
	 * @param location The kilometre location where the intermediate sprint finishes
	 *                 within the stage.
	 * @return The ID of the segment created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    segment.
	 */
    public int addIntermediateSprintToStage(int stageId, double location)throws IDNotRecognisedException,
    InvalidLocationException, InvalidStageStateException, InvalidStageTypeException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        else if (location>stage.getLength() || location<=0){
            throw new InvalidLocationException("Invalid sprint location");
        }
        else if (stage.getState().equals("wait")){
            throw new InvalidStageStateException("Stage is out of prep phase");
        }
        else if (stage.getType()==StageType.TT){
            throw new InvalidStageTypeException("Time trials cannot contain sprints");
        }

        int id = session.getNextSegmentId();
        session.incrementSegmentId();
        stage.insertSegment(new Segment(id, location, SegmentType.SPRINT));

        return id;
    }

    /**
	 * Retrieves the list of segment (mountains and sprints) IDs of a stage.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The list of segment IDs ordered (from first to last) by their location in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        ArrayList<Segment> segments=stage.getSegments();
        int[] segmentIds= new int[segments.size()];
        for(int i=0;i<segmentIds.length;i++){//Goes through each segment is selected stage and gets the segment ID
            segmentIds[i]=segments.get(i).getId();
        }

        return segmentIds;
    }

    /**
	 * Removes a segment from a stage.
	 * 
	 * @param segmentId The ID of the segment to be removed.
	 * @throws IDNotRecognisedException   If the ID does not match to any segment in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
    public void removeSegment(int segmentId)throws IDNotRecognisedException, InvalidStageStateException{
        boolean found=false;
        ArrayList<Race> races=session.getAllRaces();
        for (int i=0;i<races.size();i++){
            if (found==true){//If segment has been found in the previously searched race the search is ended
                break;
            }
            ArrayList<Stage> stages=races.get(i).getAllStages();
            for (int j=0;j<stages.size();j++){
                Segment segment=stages.get(j).findSegment(segmentId);
                if (segment!=null){
                    if (stages.get(j).getState()=="wait"){
                        throw new InvalidStageStateException("Stage is waiting for results");
                    }
                    stages.get(j).removeSegment(segment);
                    found=true;
                    break;
                }
            }
        }
        if (found==false){
            throw new IDNotRecognisedException("ID not recognised");
        }
        assert(findSegmentInStage(segmentId)==null);
    }

    /**
	 * Record the times of a rider in a stage.
	 * 
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpoints An array of times at which the rider reached each of the
	 *                    segments of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointsException Thrown if the length of checkpoints is
	 *                                     not equal to n+2, where n is the number
	 *                                     of segments in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)throws IDNotRecognisedException,
    DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("Stage ID not recognised");
        }
        else if (stage.getState().equals("prep")){
            throw new InvalidStageStateException("Stage in preperation phase");
        }
        else if (checkpoints.length != stage.getSegments().size()+2){
            throw new InvalidCheckpointsException("Incorrect number of checkpoints submitted");
        }

        if (doesRiderExist(riderId)==false){
            throw new IDNotRecognisedException("Rider ID not recognised");
        }

        boolean foundResult=false;
        for (int i=0;i<stage.getFinishResults().size();i++){//Search for any existing result for rider in stage
            if (stage.getFinishResults().get(i).getRiderId()==riderId){
                foundResult=true;
            }
        }
        if (foundResult){
            throw new DuplicatedResultException("Rider already has result registered");
        }

        ArrayList<Segment> segments=stage.getSegments();
        stage.insertStartTime(new RiderResult(riderId, checkpoints[0]));//Adds riders start time to stage
        for (int i=0;i<segments.size();i++){//Goes through each segment in stage and adds the time at which the rider finish each segment
            segments.get(i).insertCheckpoint(new RiderResult(riderId, checkpoints[i+1]));
        }
        stage.insertFinish(new RiderResult(riderId, checkpoints[checkpoints.length-1]));//Adds riders finish time to stage
    }


    /**
	 * Get the riders finished position in a a stage.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        if (stage.getType()!=StageType.TT){//If the stage is not a time trial then the finish reuslts of the stage are fetched which are already sorted
            int[] rank= new int[stage.getFinishResults().size()];
            for (int i=0;i<stage.getFinishResults().size();i++){
                rank[i]=stage.getFinishResults().get(i).getRiderId();
            }
            return rank;
        }
        else{//If the stage is a time trial the time of completion must be calculated and sorted for each rider
            ArrayList<Long> times= new ArrayList<Long>();
            ArrayList<Integer> riderIds= new ArrayList<Integer>();
            //Two lists are created which contain times and riders ids. The rider id at index n will always be the rider who has the time at index n in list times
            for (int i=0;i<stage.getFinishResults().size();i++){//Iterates through the stages finish results
                LocalTime finishTime = stage.getFinishResults().get(i).getTime();
                int riderId=stage.getFinishResults().get(i).getRiderId();
                LocalTime startTime = stage.findRiderStart(riderId).getTime();
                long seconds = ChronoUnit.SECONDS.between(startTime, finishTime);//Calculates the time the rider completed TT in
                if(times.size()==0){//If the lists are empty just add time and rider
                    times.add(seconds);
                    riderIds.add(riderId);
                }
                else if(seconds>=times.get(times.size()-1)){//If the time is longer than the final element it is added onto the end of the list
                    times.add(seconds);
                    riderIds.add(riderId);
                }
                else{
                    for (int j=0;j<times.size();j++){//Inserts with insertion sort logic
                        if (seconds<=times.get(j)){
                            times.add(j,seconds);
                            riderIds.add(j,riderId);
                            break;
                        }
                    }
                }
            }
            int[] rank= new int[riderIds.size()];//Converts riderId list into array to be returned
            for(int i=0;i<rank.length;i++){
                rank[i]=riderIds.get(i);
            }
            return rank;
        }
    }


    /**
	 * Concludes the preparation of a stage. After conclusion the stages state will be "wait"
     * which stands for 'waiting for results'
	 * 
	 * @param stageId The ID of the stage to be concluded.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is waiting for results.
	 */
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException,InvalidStageStateException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        else if (stage.getState().equals("wait")){
            throw new InvalidStageStateException("Stage is already waiting for results");
        }
        stage.concludePrep();
    }

    /**
	 * Get the times of a rider in a stage.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The array of times at which the rider reached each of the segments of
	 *         the stage and the total elapsed time. The elapsed time is the
	 *         difference between the finish time and the start time. Return an
	 *         empty array if there is no result registered for the rider in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("Stage ID not recognised");
        }

        if (doesRiderExist(riderId)==false){
            throw new IDNotRecognisedException("Rider ID not recognised");
        }

        boolean isResult= false;
        if(stage.findRiderResult(riderId)!=null){//Finding if there is a result for the rider
                isResult=true;
        }

        if (isResult){
            LocalTime finishTime=stage.findRiderResult(riderId).getTime();
            LocalTime[] times = new LocalTime[stage.getSegments().size()+1];
            for (int i=0;i<stage.getSegments().size();i++){
                Segment segment=stage.getSegments().get(i);
                times[i]=segment.findRiderResult(riderId).getTime();
            }
            LocalTime riderStart = stage.findRiderStart(riderId).getTime();
            long seconds=ChronoUnit.SECONDS.between(riderStart,finishTime);
            times[times.length-1]=LocalTime.ofSecondOfDay(seconds);
            return times;
            //returns in format [checkpoints,elapsed time]
        }
        else{
            return new LocalTime[0];
        }
    }

    /**
     * If a rider finishes within a second of the rider ahead of them they geet the time of the rider ahead.
     * So if 100 riders finish together with no gap bigger than 1 second between any two riders all the riders
     * get the same time.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
     * @return The adjusted elapsed time for the rider in the stage. Return an empty
	 *         array if there is no result registered for the rider in the stage.
	 * @throws IDNotRecognisedException   If the ID does not match to any rider or
	 *                                    stage in the system.
	*/
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId,int riderId)throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if(stage==null){
            throw new IDNotRecognisedException("Stage ID not recognised");
        }
        if (doesRiderExist(riderId)==false){
            throw new IDNotRecognisedException("Rider ID not recognised");
        }
        RiderResult startResult = stage.findRiderStart(riderId);
        RiderResult finishResult = stage.findRiderResult(riderId);

        if(startResult==null){//If no result exists for the rider a null value is returned
            return null;
        }
        
        LocalTime riderStart = startResult.getTime();
        LocalTime finishTime = finishResult.getTime();

        if (stage.getType()!=StageType.TT){//If the stage is a time trial no adjustments to finishing time are made
            boolean loop=true;
            int count=stage.getFinishResults().indexOf(stage.findRiderResult(riderId));//Gets the index of the riders result
            while(loop==true && count>0){//Will loop until the gap to the rider ahead is more than one second or the number 1 rider has been reached
                if(ChronoUnit.SECONDS.between(stage.getFinishResults().get(count-1).getTime(),stage.getFinishResults().get(count).getTime())<1){
                //Calculates gap between current iterated rider's time and the rider 1 position ahead.
                //If the gap is less than 1 second the next rider is looked at
                    count-=1;
                }
                else{
                    loop=false;
                }
                finishTime=stage.getFinishResults().get(count).getTime();
                //The new finish time of the rider is adjusted every time a rider that finished ahead is found to be in their riding group
            }
        }

        long seconds=ChronoUnit.SECONDS.between(riderStart,finishTime);
        return LocalTime.ofSecondOfDay(seconds);
    }

    /**
	 * Removes the stage results from the rider.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("Stage ID not recognised");
        }
        if (doesRiderExist(riderId)==false){
            throw new IDNotRecognisedException("Rider ID not recognised");
        }
        if (stage.findRiderResult(riderId)!=null){//If a result exists for rider it will delete it
            stage.removeFinishResult(stage.findRiderResult(riderId));
            stage.removeRiderStartTime(stage.findRiderStart(riderId));
            for (int i=0;i<stage.getSegments().size();i++){
                Segment segment=stage.getSegments().get(i);
                segment.removeCheckpointResult(segment.findRiderResult(riderId));
            }
        }
    }

    /**
	 * Get the adjusted elapsed times of riders in a stage.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         will match the riders ad order returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException{
        Stage stage=findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        int[] ranking = getRidersRankInStage(stageId);
        LocalTime[] adjustedElapsedTimes= new LocalTime[stage.getFinishResults().size()];
        for(int i=0;i<stage.getFinishResults().size();i++){
            adjustedElapsedTimes[i]=getRiderAdjustedElapsedTimeInStage(stageId, ranking[i]);
            //Uses ranking array to know which rider Id is at each position
            //Then calculates the corresponding adjusted time for that rider
        }
        return adjustedElapsedTimes;
    }

    /**
	 * Get the number of points obtained by each rider in a stage.
     * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each riders received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points will match the riders and order returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException{
        Stage stage = findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        int[] rank = getRidersRankInStage(stageId);
        int[] ridersPoints = new int[rank.length];//An array to store riders points. Index i in this array will contain the points of the rider at position i in the rank array
        int[] interSprintPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
        int[] stagePoints;
        if (stage.getType()==StageType.FLAT){
            stagePoints=new int[] {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
        }
        else if(stage.getType()==StageType.MEDIUM_MOUNTAIN){
            stagePoints=new int[] {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
        }
        else{
            stagePoints=new int[] {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
        }
        if (stage.getType()==StageType.TT){//If its a TT there are no sprints so points are simply assigned by finishing position
            for (int i=0;i<ridersPoints.length;i++){
                ridersPoints[i]=stagePoints[i];
            }
        }
        else{
            for(int i=0;i<stage.getFinishResults().size();i++){//Iterates through every finish result registered
                if (i==15){//Points are only assigned for first 15 finishers
                    break;
                }
                int scoringId=stage.getFinishResults().get(i).getRiderId();//Gets the ith finishing riders id
                for(int j=0;j<rank.length;j++){//Goes through the rank array to find the scoring rider and awards them their points
                    if(rank[j]==scoringId){
                        ridersPoints[j]+=stagePoints[i];
                    }
                }
            }
            for(int i=0;i<stage.getSegments().size();i++){//Iterates through every segment for a stage
                Segment segment=stage.getSegments().get(i);
                if (segment.getType()==SegmentType.SPRINT){//If the segment is a sprint then sprint points need to be awarded
                    for (int j=0; j<segment.getCheckpointResults().size();j++){//Iterates through all registered results for a segment
                        if(j==15){//Points only awarded for first 15 finishers
                            break;
                        }
                        int scoringId=segment.getCheckpointResults().get(j).getRiderId();//The id of the rider due points
                        for(int k=0;k<rank.length;k++){//Iterates to find the correct rider and then assigns points
                            if(rank[k]==scoringId){
                                ridersPoints[k]+=interSprintPoints[j];
                            }
                        }
                    }
                }   
            }
        }
        return ridersPoints;
    }

    /**
	 * Get the number of mountain points obtained by each rider in a stage.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each riders received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points will match the riders and order returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException{
        Stage stage = findStageInRace(stageId);
        if (stage==null){
            throw new IDNotRecognisedException("ID not recognised");
        }
        int[] rank = getRidersRankInStage(stageId);
        int[] ridersPoints = new int[rank.length];//New array to store points of riders. Index n will store the points of the rider at index n in rank array
        int[] climbPoints;//Array which will store the points availible for each climb
        for(int i=0;i<stage.getSegments().size();i++){
            Segment segment=stage.getSegments().get(i);
            boolean mountain =false;
            //If the segemnt is a climb then the points availible are assigned and the variable mountain is set to true
            if (segment.getType()==SegmentType.C1){
                climbPoints= new int[] {10,8,6,4,2,1};
                mountain=true;
            }
            else if (segment.getType()==SegmentType.C2){
                climbPoints= new int[] {5,3,2,1};
                mountain=true;
            }
            else if (segment.getType()==SegmentType.C3){
                climbPoints= new int[] {2,1};
                mountain=true;
            }
            else if (segment.getType()==SegmentType.C4){
                climbPoints= new int[] {1};
                mountain=true;
            }
            else if (segment.getType()==SegmentType.HC){
                climbPoints= new int[] {20,15,12,10,8,6,4,2};
                mountain=true;
            }
            else{
                climbPoints= new int[0];
            }
            if (mountain){
                for (int j=0; j<segment.getCheckpointResults().size();j++){//Gets the checkpoint times for the segment
                    if(j==climbPoints.length){//If j is at the length of the array of availible points it means all the points have been awarded
                        break;
                    }
                    int scoringId=segment.getCheckpointResults().get(j).getRiderId();//The rider that reached peak in position j that is due points
                    for(int k=0;k<rank.length;k++){//Searching for the rider to assign their points
                        if(rank[k]==scoringId){
                            ridersPoints[k]+=climbPoints[j];
                        }
                    }
                }
            }
        }
        return ridersPoints;
    }

    /**
     * Erases all the data stored for the portal and creates a new session
     */
    public void eraseCyclingPortal(){
        session= new Session();
    }

    /**
	 * Method saves this MiniCyclingPortalInterface contents into a serialised file,
	 * with the filename given in the argument.
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
    public void saveCyclingPortal(String filename) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream(filename));
        out.writeObject(session);
        out.close();
    }

    /**
	 * Method should load and replace this MiniCyclingPortalInterface contents with the
	 * serialised contents stored in the file given in the argument.
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        Object obj = in.readObject();
        if (obj instanceof Session){
            session= (Session) obj;
        }
        in.close();
    }

    /**
     * Creates a new portal. Also creates a fresh session.
     */
    public CyclingPortal(){
        this.session = new Session();
    }
}