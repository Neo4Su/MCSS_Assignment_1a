/**
 * The top-level component of the emergency department simulator.
 *
 * It is responsible for:
 *  - creating all the components of the system;
 *  - starting all of the processes;
 *
 * @author ngeard@unimelb.edu.au
 *
 */

public class Main {

    public static void main(String [] args) throws InterruptedException {

        // generate the system components
        Foyer foyer = new Foyer();
        Triage triage = new Triage();
        Treatment treatment = new Treatment();
        Orderlies orderlies = new Orderlies();
        Specialist specialist = new Specialist(treatment);
        
        // generate the producer and consumer processes
        Producer producer = new Producer(foyer);
        Consumer consumer = new Consumer(foyer);

        // create arrays of Nurses
        Nurse[] nurses = new Nurse[Params.NURSES];

        // generate and start the individual nurse processes
        for (int i = 0; i < Params.NURSES; i++) {
        	nurses[i] = new Nurse(i + 1, foyer, triage, orderlies, treatment);
            nurses[i].start();
        }

        // start the remaining processes
        producer.start();
        consumer.start();
        specialist.start();

        // join all processes
        for (int i = 0; i < Params.NURSES; i++) {
            nurses[i].join();
        }
        producer.join();
        consumer.join();
        specialist.join();
    }
}