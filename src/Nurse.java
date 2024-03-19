/**
 * A nurse, who works in the hospital and accompanies a patient through the ED.
 *
 * @name Yucheng Su
 * @studentId 1503107
 */

public class Nurse extends Thread {
    // a unique identifier for this nurse
    private int id;

    // the foyer where patients are admitted and discharged
    private Foyer foyer;

    // the triage where patients are diagnosed
    private Triage triage;

    // the treatment room where patients are treated
    private Treatment treatment;

    // the orderlies who assist the nurse with patient transfers
    private Orderlies orderlies;

    // the patient allocated to this nurse
    private Patient patient = null;

    // initialize the nurse with id and instances of Foyer, Triage, Orderlies, and Treatment
    public Nurse(int id, Foyer foyer, Triage triage, Orderlies orderlies, Treatment treatment) {
        this.id = id;
        this.foyer = foyer;
        this.triage = triage;
        this.orderlies = orderlies;
        this.treatment = treatment;
    }

    // simulate behaviour of the nurse
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                /* actions in the foyer
                 * 1. allocate the nurse to a patient
                 * 2. recruit orderlies before leaving
                 * 3. transfer to triage
                 */
                foyer.allocateNurse(this);
                orderlies.recruitOrderlies(id);
                foyer.transferToTriage();
                sleep(Params.TRANSFER_TIME);

                /*
                 * actions in the triage
                 * 1. arrive at triage
                 * 2. release orderlies
                 * 3. undergo triage
                 * 4. recruit orderlies before leaving
                 * 5. leave triage for another location
                 */
                triage.arriveAtTriage(patient);
                orderlies.releaseOrderlies(id);
                sleep(Params.TRIAGE_TIME);
                orderlies.recruitOrderlies(id);
                triage.departFromTriage();
                sleep(Params.TRANSFER_TIME);

                // if patient is severe, go to treatment room
                if (patient.Severe()) {
                    /* actions in the treatment room:
                     * 1. arrive at treatment
                     * 2. release orderlies
                     * 3. let specialist treat patient
                     * 4. recruit orderlies before leaving
                     * 5. leave treatment room for foyer
                     */
                    treatment.arriveAtTreatment(patient);
                    orderlies.releaseOrderlies(id);
                    treatment.treatPatient();
                    orderlies.recruitOrderlies(id);
                    treatment.departFromTreatment();
                    sleep(Params.TRANSFER_TIME);

                    // arrive at foyer and release orderlies
                    foyer.arriveForDischarge(patient);
                    orderlies.releaseOrderlies(id);

                    // nurse releases patient, task done, patient is free to be discharged
                    foyer.releasePatient(id);

                } else {
                    // if the patient is not severe, return to foyer
                    // arrive at foyer and release orderlies
                    foyer.arriveForDischarge(patient);
                    orderlies.releaseOrderlies(id);

                    // nurse releases patient, task done, patient is free to be discharged
                    foyer.releasePatient(id);
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    // set the patient allocated to this nurse
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // get the id of this nurse
    public int getNurseId() {
        return id;
    }
}
