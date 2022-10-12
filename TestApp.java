import cycling.*;
import java.time.Month;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TestApp {
    public static void main(String[] args){
        CyclingPortal portal= new CyclingPortal();
        try{
        portal.createTeam("Mercedes", "fast");
        portal.createTeam("Ferrari", "red");
        portal.createRider(1, "hamilton", 1990);
        portal.createRider(1, "russel", 2000);
        portal.createRider(2, "leclerc", 1999);
        portal.createRider(2, "sainz", 1995);
        portal.createRace("Somerset", "Somerset GP");
        portal.createRace("Wales", "Welsh GP");
        portal.addStageToRace(1,"Keynsham-Bristol", "sivdshu", 12.2,LocalDateTime.of(2022,Month.APRIL,11,19,30,00), StageType.FLAT);
        portal.addStageToRace(2,"Cardiff-Swansea", "Sheep", 11.2,LocalDateTime.of(2022,Month.MAY,4,12,0,30), StageType.TT);
        portal.addStageToRace(1,"Bristol-Bath", "hyof", 6.0,LocalDateTime.of(2022,Month.APRIL,10,9,30,0), StageType.HIGH_MOUNTAIN);
        portal.addIntermediateSprintToStage(1, 4.0);
        portal.addCategorizedClimbToStage(1, 3.5, SegmentType.C3, 2.0, 3.0);
        portal.concludeStagePreparation(1);
        portal.concludeStagePreparation(2);
        portal.registerRiderResultsInStage(1, 1,LocalTime.of(19, 30, 00),LocalTime.of(19, 30, 20), LocalTime.of(20, 14, 24),LocalTime.of(20, 31, 29,3000));
        portal.registerRiderResultsInStage(1, 2,LocalTime.of(19, 30, 00),LocalTime.of(19, 40, 30), LocalTime.of(20, 11, 24),LocalTime.of(20, 31, 22, 8000));
        portal.registerRiderResultsInStage(1, 3,LocalTime.of(19, 30, 00),LocalTime.of(19, 40, 20), LocalTime.of(20, 12, 24),LocalTime.of(20, 27, 23,6000));
        portal.registerRiderResultsInStage(1, 4,LocalTime.of(19, 30, 00),LocalTime.of(19, 40, 50), LocalTime.of(20, 10, 24),LocalTime.of(20, 31, 28,2000));
        portal.registerRiderResultsInStage(2, 1,LocalTime.of(19, 30, 00),LocalTime.of(19, 40, 40));
        portal.registerRiderResultsInStage(2, 2,LocalTime.of(19, 40, 00),LocalTime.of(19, 51, 30));
        portal.registerRiderResultsInStage(2, 3,LocalTime.of(19, 50, 00),LocalTime.of(19, 55, 20));
        System.out.println(Arrays.toString(portal.getRidersRankInStage(2)));
        System.out.println(Arrays.toString(portal.getRidersPointsInStage(2)));
        System.out.println(Arrays.toString(portal.getRidersRankInStage(1)));
        System.out.println(Arrays.toString(portal.getRidersPointsInStage(1)));
        System.out.println(Arrays.toString(portal.getRidersMountainPointsInStage(1)));
        portal.saveCyclingPortal("test.ser");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
