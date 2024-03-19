/**
 * Foyer where patients are admitted and discharged.
 *
 * @name Yucheng Su
 * @studentId 1503107
 */

public class Foyer {
    // Patients who are arriving and leaving in the foyer
    private Patient arrivingPatient = null;
    private Patient departingPatient = null;

    // to admit a patient to the ED
    public synchronized void arriveAtED(Patient patient) throws InterruptedException {
        // if there is already a patient arriving, keep waiting
        while (arrivingPatient != null) {
            wait();
        }
        arrivingPatient = patient;
        System.out.println(arrivingPatient + " admitted to ED.");

        // notify that an arriving patient has arrived
        notifyAll();
    }

    // called by nurse, to allocate a nurse to a patient
    public synchronized void allocateNurse(Nurse nurse) throws InterruptedException {
        // if there's no patient arriving or patient has been allocated to a nurse, keep waiting
        while (arrivingPatient == null || arrivingPatient.isAllocated()) {
            wait();
        }
        arrivingPatient.setAllocated(true);
        nurse.setPatient(arrivingPatient);
        System.out.println(arrivingPatient + " allocated to Nurse " + nurse.getNurseId() + ".");
    }

    // patient leaves the foyer for triage
    public synchronized void transferToTriage() throws InterruptedException {
        System.out.println(arrivingPatient + " leaves Foyer.");
        arrivingPatient = null;

        // notify that a patient has left
        notifyAll();
    }

    // patient arrives at the foyer for discharge
    public synchronized void arriveForDischarge(Patient patient) throws InterruptedException {
        // if there is already a patient departing, keep waiting
        while (departingPatient != null) {
            wait();
        }
        departingPatient = patient;
        System.out.println(departingPatient + " enters Foyer.");

        // notify that a departing patient has arrived
        notifyAll();
    }

    // called by Consumer, to discharge a patient from the ED
    public synchronized void departFromED() throws InterruptedException {
        // if there's no patient departing or patient hasn't been released by nurse, keep waiting
        while (departingPatient == null || departingPatient.isAllocated()) {
            wait();
        }
        System.out.println(departingPatient + "  discharged from ED.");
        departingPatient = null;

        // notify that a patient has been discharged
        notifyAll();
    }

    // let nurse release a patient
    public synchronized void releasePatient(int NurseId) {
        System.out.println("Nurse " + NurseId + " releases " + departingPatient + ".");
        departingPatient.setAllocated(false);

        // notify that a patient has been released
        notifyAll();
    }
}
