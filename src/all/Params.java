package all;

import java.util.Random;

/**
 * Parameters that influence the behaviour of the system.
 * 
 * @author ngeard@unimelb.edu.au
 * @date 13 February 2024
 */

public class Params {

  //the number of nurses in the emergency department
  public final static int NURSES = 3;
  
  //the number of orderlies in the emergency department
  public final static int ORDERLIES = 8;
  
  //the number of orderlies required to transfer a patient
  public final static int TRANSFER_ORDERLIES = 3;
  
  //the proportion of patients whose condition is severe
  public final static double SEVERE_PROPORTION = 0.65;

  //the time it takes for a patient to undergo triage
  public final static int TRIAGE_TIME = 300;

  //the time it takes for a patient to be treated
  public final static int TREATMENT_TIME = 800;
  
  //the time it takes for a nurse (+ orderlies) to transfer a patient between all.locations
  public final static int TRANSFER_TIME = 200;
  
  //the time a specialist spends elsewhere between treating patients
  public final static int SPECIALIST_AWAY_TIME = 500;
  
  //the maximum amount of time between patient arrivals at ED (before admission)
  public final static int MAX_ARRIVE_INTERVAL = 2000;

  //the maximum amount of time between departures from ED (after discharge)
  public final static int MAX_DEPART_INTERVAL = 500;


/**
 * For simplicity, we assume uniformly distributed time lapses.
 */

  public static int arrivalPause() {
    Random random = new Random();
    return random.nextInt(MAX_ARRIVE_INTERVAL);
  }

  public static int departurePause() {
    Random random = new Random();
    return random.nextInt(MAX_DEPART_INTERVAL);
  }

}

