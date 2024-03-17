/**
 * Consumes discharged patients from the emergency department.
 *
 * @author ngeard@unimelb.edu.au
 * @date 13 February 2024 
 *
 */

public class Consumer extends Thread {

    // the foyer where patients are discharged from the ED
    private Foyer foyer;

    // creates a new consumer for the given foyer
    Consumer(Foyer newFoyer) {
        this.foyer = newFoyer;
    }

    // repeatedly collect discharged patients from the foyer
    public void run() {
        while (!isInterrupted()) {
            try {
                // remove a discharged patient
                this.foyer.departFromED();

                // let some time pass before the next patient departs
                sleep(Params.departurePause());
            }
            catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
