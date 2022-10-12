package cycling;

import java.io.Serializable;

/**
 * Rider --- A class to represent a rider.
 * Contains attributes:
 * id (int) - Unique to each rider
 * name (string) - The name of the rider
 * yearOfBirth (int) - The year the rider was born
 * 
 * @author Matt Trenchard
 * @version 1.0
 */

public class Rider implements Serializable{
    private int id;
    /**
     * Gets a rider's ID.
     * @return Rider's id.
     */
    public int getId(){
        return id;
    }
    private String name;
    /**
     * Gets a rider's name.
     * @return Rider's name.
     */
    public String getName(){
        return name;
    }
    private int yearOfBirth;
    /**
     * Gets a rider's birth year.
     * @return Rider's year of birth.
     */
    public int getYearOfBirth(){
        return yearOfBirth;
    }

    /**
     * Creates a rider with the specified parameters.
     * @param name   Name of the rider.
     * @param yearOfBirth   Year the rider was born.
     * @param id   The unique id of the rider.
     */
    public Rider(String name, int yearOfBirth, int id){
        this.id=id;
        this.name=name;
        this.yearOfBirth=yearOfBirth;
    }
}