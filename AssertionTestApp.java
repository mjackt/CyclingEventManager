import cycling.CyclingPortal;
import cycling.SegmentType;
import cycling.StageType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class AssertionTestApp {
    public static void main(String[] args){
        CyclingPortal portal = new CyclingPortal();
        try{
            assert (portal.getRaceIds().length == 0);
            assert (portal.getTeams().length == 0);
            portal.createTeam("Mercedes", "Silver");
            portal.createTeam("Ferrari", "The red team");
            portal.createTeam("Williams", "goated");
            portal.createTeam("Aston-Martin", "Green");
            portal.createTeam("Red-Bull", "red and blue");
        }
        catch(Exception e){
            System.out.println(e);
        }

        assert(portal.getTeams().length==5);

        try{
            portal.createRider(1, "Hamilton", 1980);
            portal.createRider(1, "Russell", 2000);
            portal.createRider(1, "Merc Driver", 1990);
            portal.createRider(2, "Leclerc", 1995);
            portal.createRider(2, "Sainz", 1990);
            portal.createRider(2, "Gio", 1992);
            portal.createRider(3, "Albono", 1992);
            portal.createRider(3, "Latifi", 1992);
            portal.createRider(3, "Chadwick", 1992);
            portal.createRider(4, "Hulkenburg", 1992);
            portal.createRider(4, "Stroll", 1992);
            portal.createRider(4, "Vettel", 1992);
            portal.createRider(5, "Verstappen", 1992);
            portal.createRider(5, "Perez", 1992);
            portal.createRider(5, "More Red Bull", 1992);
            portal.createRider(5, "Red bull again", 1992);
            portal.createRider(1,"to go",1900);
            portal.removeRider(17);
            assert(portal.getTeamRiders(1).length==3);
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            portal.createRace("The-one-", "A race");
            portal.createRace("To-go","gone");
            assert(portal.getRaceIds().length==2);
            portal.removeRaceById(2);
            assert(portal.getRaceIds().length==1);
            assert(portal.getNumberOfStages(1)==0);
            portal.createRace("Numero-3","grefe");
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            portal.addStageToRace(1, "Plymouth-Exeter", "coastal", 200, LocalDateTime.of(2022, Month.MARCH, 19, 15, 25), StageType.FLAT);
            portal.addStageToRace(1, "Exeter-Taunton", "its coming home", 120, LocalDateTime.of(2022, Month.MARCH, 20, 13, 25), StageType.MEDIUM_MOUNTAIN);
            portal.addStageToRace(1, "Keynsham-TT", "Noice", 35, LocalDateTime.of(2022, Month.MARCH, 22, 9, 00), StageType.TT);
            portal.addStageToRace(1, "Taunton-Bristol", "Farmer", 320, LocalDateTime.of(2022, Month.MARCH, 21, 14, 30), StageType.HIGH_MOUNTAIN);
            portal.addStageToRace(3, "to-go", "description", 53, LocalDateTime.of(2022, Month.MARCH, 21, 14, 30), StageType.HIGH_MOUNTAIN);
            portal.removeStageById(5);
            assert(portal.getRaceStages(1).length==4);
            assert(portal.getRaceStages(3).length==0);
            assert(portal.getStageLength(4)==320);
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            portal.saveCyclingPortal("test.ser");
            portal.eraseCyclingPortal();
            portal.loadCyclingPortal("test.ser");
            portal.addCategorizedClimbToStage(1, 45.0, SegmentType.C1, 10.0, 5.0);
            portal.addCategorizedClimbToStage(1, 25.0, SegmentType.C2, 2.0, 15.0);
            portal.addCategorizedClimbToStage(1, 125.0, SegmentType.C3, 1.0, 15.0);
            portal.addCategorizedClimbToStage(1, 155.0, SegmentType.C4, 2.0, 5.0);
            portal.addCategorizedClimbToStage(1, 165.0, SegmentType.HC, 20.0, 2.0);
            portal.addIntermediateSprintToStage(2, 13.0);
            portal.addIntermediateSprintToStage(4, 12.0);
            portal.removeSegment(7);
            assert(portal.getStageSegments(1).length==5);
            assert(portal.getStageSegments(4).length==0);
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            portal.concludeStagePreparation(1);
            portal.registerRiderResultsInStage(1, 1, LocalTime.of(0, 0, 0),LocalTime.of(0, 0, 54),LocalTime.of(0, 0, 24),LocalTime.of(0, 32, 52),LocalTime.of(0, 47, 16),LocalTime.of(0, 58, 28),LocalTime.of(1, 24, 22));
            portal.deleteRiderResultsInStage(1, 1);
            portal.registerRiderResultsInStage(1, 1, LocalTime.of(0, 0, 0),LocalTime.of(0, 23, 54),LocalTime.of(0, 42, 24),LocalTime.of(1, 32, 52),LocalTime.of(1, 47, 16),LocalTime.of(1, 58, 28),LocalTime.of(2, 24, 22));
            portal.registerRiderResultsInStage(1, 2, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 34),LocalTime.of(0, 43, 45),LocalTime.of(1, 32, 56),LocalTime.of(1, 46, 46),LocalTime.of(1, 58, 23),LocalTime.of(2, 24, 22,3000));
            portal.registerRiderResultsInStage(1, 3, LocalTime.of(0, 0, 0),LocalTime.of(0, 23,58),LocalTime.of(0, 44, 22),LocalTime.of(1, 33, 46),LocalTime.of(1, 46, 36),LocalTime.of(1, 57, 56),LocalTime.of(2, 24, 23,1000));
            portal.registerRiderResultsInStage(1, 4, LocalTime.of(0, 0, 0),LocalTime.of(0, 24,22),LocalTime.of(0, 42, 28),LocalTime.of(1, 33, 22),LocalTime.of(1, 48, 16),LocalTime.of(1, 56, 34),LocalTime.of(2, 24, 24));
            portal.registerRiderResultsInStage(1, 5, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 16),LocalTime.of(0, 43, 44),LocalTime.of(1, 33, 46),LocalTime.of(1, 47, 23),LocalTime.of(1, 59, 27),LocalTime.of(2, 24, 23,5000));
            portal.registerRiderResultsInStage(1, 6, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 47),LocalTime.of(0, 44, 34),LocalTime.of(1, 32, 22),LocalTime.of(1, 46, 34),LocalTime.of(1, 57, 34),LocalTime.of(2, 26, 42));
            portal.registerRiderResultsInStage(1, 7, LocalTime.of(0, 0, 0),LocalTime.of(0, 23,12),LocalTime.of(0, 44, 54),LocalTime.of(1, 34, 12),LocalTime.of(1, 47, 39),LocalTime.of(1, 59, 22),LocalTime.of(2, 26, 42,5000));
            portal.registerRiderResultsInStage(1, 8, LocalTime.of(0, 0, 0),LocalTime.of(0, 23,43),LocalTime.of(0, 44, 32),LocalTime.of(1, 32, 58),LocalTime.of(1, 45, 19),LocalTime.of(1, 58, 56),LocalTime.of(2, 26, 43));
            portal.registerRiderResultsInStage(1, 9, LocalTime.of(0, 0, 0),LocalTime.of(0, 23,32),LocalTime.of(0, 44, 27),LocalTime.of(1, 33, 16),LocalTime.of(1, 47, 10),LocalTime.of(1, 58, 45),LocalTime.of(2, 26, 52));
            portal.registerRiderResultsInStage(1, 10, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 56),LocalTime.of(0, 44, 26),LocalTime.of(1, 32, 16),LocalTime.of(1, 48, 13),LocalTime.of(1, 58, 12),LocalTime.of(2, 27, 22));
            portal.registerRiderResultsInStage(1, 11, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 42),LocalTime.of(0, 44, 30),LocalTime.of(1, 32, 26),LocalTime.of(1, 47, 58),LocalTime.of(1, 57, 07),LocalTime.of(2, 26, 32));
            portal.registerRiderResultsInStage(1, 12, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 59),LocalTime.of(0, 44, 20),LocalTime.of(1, 32, 36),LocalTime.of(1, 49, 37),LocalTime.of(1, 58, 12),LocalTime.of(2, 26, 42));
            portal.registerRiderResultsInStage(1, 13, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 22),LocalTime.of(0, 44, 21),LocalTime.of(1, 33, 26),LocalTime.of(1, 47, 46),LocalTime.of(1, 59, 46),LocalTime.of(2, 26, 52));
            portal.registerRiderResultsInStage(1, 14, LocalTime.of(0, 0, 0),LocalTime.of(0, 24, 21),LocalTime.of(0, 42, 22),LocalTime.of(1, 32, 32),LocalTime.of(1, 46, 29),LocalTime.of(1, 58, 56),LocalTime.of(2, 27, 42));
            portal.registerRiderResultsInStage(1, 15, LocalTime.of(0, 0, 0),LocalTime.of(0, 23, 34),LocalTime.of(0, 43, 33),LocalTime.of(1, 32, 44),LocalTime.of(1, 47, 16),LocalTime.of(1, 58, 45),LocalTime.of(2, 27, 32));
            portal.registerRiderResultsInStage(1, 16, LocalTime.of(0, 0, 0),LocalTime.of(0, 25, 01),LocalTime.of(0, 45, 24),LocalTime.of(1, 34, 56),LocalTime.of(1, 49, 28),LocalTime.of(1, 59, 59),LocalTime.of(2, 28, 42));
            assert(portal.getRidersRankInStage(1).length==16);
            assert(portal.getRiderAdjustedElapsedTimeInStage(1, 1).equals(LocalTime.of(02,24,22)));
            assert(portal.getRiderAdjustedElapsedTimeInStage(1, 4).equals(LocalTime.of(02,24,22)));
            assert(portal.getRiderAdjustedElapsedTimeInStage(1, 8).equals(LocalTime.of(02,26,42)));
            assert(portal.getRiderAdjustedElapsedTimeInStage(3, 8)==null);
            portal.concludeStagePreparation(3);
            System.out.println(portal.viewRaceDetails(1));
            portal.removeRider(1);
            assert(portal.getRidersRankInStage(1).length==15);
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            System.out.println("fin");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
