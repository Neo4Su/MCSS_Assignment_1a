/**
 *
 */
public class Foyer {
    // Patients who are arriving and leaving in the foyer
    private Patient arrivingPatient = null;
    private Patient departingPatient = null;

    // 病人来了，如果没有病人，开始admit patient参数是来看病的病人
    public synchronized void arriveAtED(Patient patient) throws InterruptedException {
        // If there is already a patient entering, keep waiting
        while (arrivingPatient != null) {
            wait();
        }
        arrivingPatient = patient;
        System.out.println(arrivingPatient + " admitted to ED.");
        // Notify that a patient has arrived
        notifyAll();
    }

    //called by nurse, to allocate a nurse to a patient
    public synchronized void allocateNurse(Nurse nurse) throws InterruptedException {
        // 如果没有病人或病人已分配护士，一直等待
        while (arrivingPatient == null || arrivingPatient.isAllocated()) {
            wait();
        }

        //分配护士
        arrivingPatient.setAllocated(true);
        nurse.setPatient(arrivingPatient);
        System.out.println(arrivingPatient + " allocated to Nurse " + nurse.getNurseId() + ".");
    }

    //将病人带到triage
    public synchronized void transferToTriage() throws InterruptedException {
        System.out.println(arrivingPatient + " leaves Foyer.");
        arrivingPatient = null;
        //通知有病人离开
        notifyAll();
    }

    //病人到达foyer，准备离开ED
    public synchronized void prepareToLeaveED(Patient patient) throws InterruptedException {
        //如果已有病人正在离开，则一直wait
        while (departingPatient != null) {
            wait();
        }

        departingPatient = patient;
        System.out.println(departingPatient + " enters Foyer.");
        //通知有病人来了
        notifyAll();
    }

    //病人离开
    public synchronized void departFromED() throws InterruptedException {
        //如果没有病人正在离开或护士还未释放病人，则一直wait
        while (departingPatient == null || departingPatient.isAllocated()) {
            wait();
        }
        System.out.println(departingPatient + "  discharged from ED.");
        departingPatient = null;
        //通知有病人离开
        notifyAll();
    }

    //确保护士释放病人后，病人才能离开
    public synchronized void releasePatient(int NurseId) {
        System.out.println("Nurse " + NurseId + " releases " + departingPatient + ".");
        departingPatient.setAllocated(false);
        notifyAll();
    }
}
