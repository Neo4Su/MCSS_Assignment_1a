/**
 * Orderlies who assist nurses in transferring patients.
 *
 * @name Yucheng Su
 * @studentId 1503107
 */

public class Orderlies {
    // number of free orderlies
    private volatile int availableOrderlies;

    // initialize the number of orderlies according to Params
    public Orderlies() {
        this.availableOrderlies = Params.ORDERLIES;
    }

    // recruit orderlies to assist nurse in transferring patient
    public synchronized void recruitOrderlies(int NurseId) throws InterruptedException {
        // if there are not enough free orderlies, keep waiting
        while (availableOrderlies < Params.TRANSFER_ORDERLIES) {
            wait();
        }
        availableOrderlies -= Params.TRANSFER_ORDERLIES;
        System.out.println("Nurse " + NurseId + " recruits " + Params.TRANSFER_ORDERLIES +
                " orderlies (" + availableOrderlies + " free).");
    }

    // release orderlies after nurse has transferred patient
    public synchronized void releaseOrderlies(int NurseId) {
        availableOrderlies += Params.TRANSFER_ORDERLIES;
        System.out.println("Nurse " + NurseId + " releases " + Params.TRANSFER_ORDERLIES +
                " orderlies (" + availableOrderlies + " free).");

        // notify that orderlies have been released
        notifyAll();
    }
}
