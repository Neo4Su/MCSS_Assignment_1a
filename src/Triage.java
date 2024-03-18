

public class Triage {
    // 表示是否有病人在triage
    Patient currentPatient = null;

    // 病人到达triage
    public synchronized void arriveAtTriage(Patient patient) throws InterruptedException {
        // 如果有病人在triage，则一直等待
        while (currentPatient != null) {
            wait();
        }
        currentPatient = patient;
        System.out.println(patient + " enters triage.");
        // 通知有病人到达triage
        notifyAll();
    }

    //诊断完成并招募orderlies后，病人离开triage
    public synchronized void departFromTriage() {
        System.out.println(currentPatient + " leaves triage.");
        currentPatient = null;
        // 通知有病人离开triage
        notifyAll();
    }
}
