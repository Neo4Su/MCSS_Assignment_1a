/**
 * Triage where patients undergo triage.
 *
 * @name Yucheng Su
 * @studentId 1503107
 */

public class Triage {
    // current patient in triage
    Patient currentPatient = null;

    // patient arrives at triage
    public synchronized void arriveAtTriage(Patient patient) throws InterruptedException {
        // if there is already a patient in triage, keep waiting
        while (currentPatient != null) {
            wait();
        }
        currentPatient = patient;
        System.out.println(patient + " enters triage.");

        // notify that a patient has arrived
        notifyAll();
    }

    // patient leaves triage
    public synchronized void departFromTriage() {
        System.out.println(currentPatient + " leaves triage.");
        currentPatient = null;
        // notify that a patient has left
        notifyAll();
    }
}
