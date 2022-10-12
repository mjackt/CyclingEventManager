package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Race --- A class to represent a cycling race which can contain multiple stages of different types.
 * Contains the following attributes:
 * allStages(ArrayList<Stage>) - An ArrayList containing all the of the stages that make up a race
 * id(int) - An id unique to each race
 * name(String) - The name of the race
 * desc(String) - The description of the race
 * 
 * @author Matt Trenchard
 * @version 1.0
 */
public class Race implements Serializable{
    private ArrayList<Stage> allStages;
    /**
     * Gets all the stages in the race
     * @return ArrayList of stages in the race
     */
    public ArrayList<Stage> getAllStages(){
        return allStages;
    }
    private int id;
    /**
     * Gets the id of the race
     * @return The id of the race
     */
    public int getId(){
        return id;
    }
    private String name;
    /**
     * Gets the name of the race
     * @return The name of the race
     */
    public String getName(){
        return name;
    }
    private String desc;
    /**
     * Gets the description of the race
     * @return The race description
     */
    public String getDesc(){
        return desc;
    }


    /**
     * Used to add a stage to a race. The stage is inserted based on it's date.
     * @param stage   The stage being added.
     */
    public void insertStage(Stage stage){
        if(allStages.size()==0){
            allStages.add(stage);
        }
        else if(allStages.get(allStages.size()-1).getStartTime().isBefore(stage.getStartTime())){//If the stage being added is after the current last stage it is appended.
            allStages.add(stage);
        }
        else{
            for(int i=0;i<allStages.size();i++){//Inserts at position i when it finds that current position i is after the stage to be added.
                if(stage.getStartTime().isBefore(allStages.get(i).getStartTime())){
                    allStages.add(i,stage);
                    break;
                }
            }
        }
    }

    /**
     * Removes a stage from the race.
     * @param stage   The stage you want to remove.
     */
    public void removeStage(Stage stage){
        allStages.remove(stage);
    }

    /**
     * Used to find a stage within the race
     * @param id   The id of the stage you want to find
     * @return If the stage is found in the race, the corresponding stage object is returned.
     *         If not, a null value is returned.
     */
    public Stage findStage(int id){
        ArrayList<Stage> stages=getAllStages();//List of all created stages
        for(int i=0; i<stages.size();i++){
            if (stages.get(i).getId()==id){//Comparing search id and id of stage being iterated
                Stage stage=stages.get(i);
                return stage;
            }
        }
        return null;
    }


    /**
     * Creates a new race object
     * @param id   The id of the race to be created
     * @param name   The name of the race to be created
     * @param desc   The description of the race to be created
     */
    public Race(int id, String name, String desc){
        this.id=id;
        this.name=name;
        this.desc=desc;
        allStages = new ArrayList<Stage>();
    }
}
