public class Nurse extends Thread {
    private int id;
    private Foyer foyer;
    private Triage triage;
    private Treatment treatment;
    private Orderlies orderlies;
    private Patient patient = null;

    public Nurse(int id, Foyer foyer, Triage triage, Orderlies orderlies, Treatment treatment) {
        this.id = id;
        this.foyer = foyer;
        this.triage = triage;
        this.orderlies = orderlies;
        this.treatment = treatment;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //allocate a nurse to a patient
                foyer.allocateNurse(this);

                //进入triage前招募orderlies, 为保证打印顺序，将id传入，以便在同步块中打印
                orderlies.recruitOrderlies(id);

                //护送病人到triage
                foyer.transferToTriage();
                //模拟护送病人到triage的时间
                sleep(Params.TRANSFER_TIME);

                //到达triage
                triage.arriveAtTriage(patient);
                //释放orderlies
                orderlies.releaseOrderlies(id);

                //模拟诊断病人的时间
                sleep(Params.TRIAGE_TIME);
                //诊断完成, 招募orderlies并离开triage
                orderlies.recruitOrderlies(id);

                triage.departFromTriage();
                //模拟护送病人到foyer/treatment的时间
                sleep(Params.TRANSFER_TIME);

                //根据是否严重决定去向
                if (patient.Severe()) {
                    //病人严重，送到treatment
                    treatment.arriveAtTreatment(patient);
                    //释放orderlies
                    orderlies.releaseOrderlies(id);

                    //由专家进行治疗
                    treatment.treatPatient();

                    //治疗完成，招募orderlies离开，回到foyer
                    orderlies.recruitOrderlies(id);

                    treatment.departFromTreatment();
                    //模拟护送病人到foyer的时间
                    sleep(Params.TRANSFER_TIME);
                    //到达foyer，准备离开
                    foyer.prepareToLeaveED(patient);
                    //释放orderlies
                    orderlies.releaseOrderlies(id);

                    //护士结束任务，可以分配新的病人
                    foyer.releasePatient(id);

                } else {
                    //病人不严重，返回foyer
                    //到达foyer，准备离开
                    foyer.prepareToLeaveED(patient);
                    //释放orderlies
                    orderlies.releaseOrderlies(id);

                    //护士结束任务，可以分配新的病人
                    foyer.releasePatient(id);
                }

            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getNurseId() {
        return id;
    }
}
