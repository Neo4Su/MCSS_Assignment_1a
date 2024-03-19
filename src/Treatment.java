/**
 * Treatment room where patients are treated by a specialist.
 *
 * @name Yucheng Su
 * @studentId 1503107
 */
public class Treatment {
    // current patient in treatment room
    private Patient currentPatient = null;

    // a flag indicating whether specialist is at the treatment room
    private boolean specialistAvailable = false;

    // patient arrives at treatment room
    public synchronized void arriveAtTreatment(Patient patient) throws InterruptedException {
        // if there is already a patient in treatment room, keep waiting
        while (currentPatient != null) {
            wait();
        }
        currentPatient = patient;
        System.out.println(patient + " enters treatment room.");

        // notify that a patient has arrived
        notifyAll();
    }

    // specialist enters treatment room
    public synchronized void specialistEnters() {
        specialistAvailable = true;
        System.out.println("Specialist enters treatment room.");

        //notify that specialist has entered
        notifyAll();
    }

    // specialist leaves treatment room
    public synchronized void specialistLeaves() throws InterruptedException {
        // if specialist hasn't treated a patient, keep waiting
        while (currentPatient == null || currentPatient.isTreated() == false) {
            wait();
        }
        System.out.println("Specialist leaves treatment room.");
    }

    // treat patient
    public synchronized void treatPatient() throws InterruptedException {
        // if specialist is not here, keep waiting
        while (!specialistAvailable) {
            wait();
        }
        System.out.println(currentPatient + " treatment started.");
        Thread.sleep(Params.TREATMENT_TIME); // simulate treatment time
        System.out.println(currentPatient + " treatment complete.");

        // set availability flag to false, because specialist need to leave for other duties
        specialistAvailable = false;
        currentPatient.setTreated(true);

        // notify that treatment is complete
        notifyAll();
    }

    // patient leaves treatment room
    public synchronized void departFromTreatment() {
        System.out.println(currentPatient + " leaves treatment room.");
        currentPatient = null;

        // notify that a patient has left
        notifyAll();
    }
}
