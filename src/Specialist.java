/**
 * A specialist, who works in the hospital and treats patients in the treatment room.
 *
 * @name Yucheng Su
 * @studentId 1503107
 */
public class Specialist extends Thread {
    // the treatment room where the specialist treats patients
    Treatment treatment;

    // initialize the specialist with an instance of Treatment
    public Specialist(Treatment treatment) {
        this.treatment = treatment;
    }

    // simulate behaviour of the specialist
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // complete other duties in the hospital
                Thread.sleep(Params.SPECIALIST_AWAY_TIME);

                // enter the treatment room to treat a patient
                treatment.specialistEnters();

                // leave the treatment room after treatment complete
                treatment.specialistLeaves();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
