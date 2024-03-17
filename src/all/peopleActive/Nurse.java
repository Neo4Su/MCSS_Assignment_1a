package all.peopleActive;

import all.Params;
import all.Patient;
import all.locations.Foyer;
import all.locations.Treatment;
import all.locations.Triage;
import all.peopleVar.Orderlies;

public class Nurse extends Thread{
    private int id;
    private Foyer foyer;
    private Triage triage;
    private Treatment treatment;
    private Orderlies orderlies;
    private Patient patient = null;

    public Nurse(int id, Foyer foyer, Triage triage, Orderlies orderlies, Treatment treatment) {
        this.id = id;
        this.foyer=foyer;
        this.triage=triage;
        this.orderlies=orderlies;
        this.treatment=treatment;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try{
                //admit a patient and allocate a nurse to a patient
                patient = foyer.allocateNurse();
                System.out.println(patient + " admitted to ED.");
                System.out.println(patient + " allocated to Nurse " + id +".");

                //进入triage前招募orderlies
                int availableOrderlies = orderlies.recruitOrderlies();
                System.out.println("Nurse "+id+" recruits "+Params.TRANSFER_ORDERLIES +
                        " orderlies ("+availableOrderlies+ " free).");
                //护送病人到triage
                foyer.transferToTriage();
                //模拟护送病人到triage的时间
                sleep(Params.TRANSFER_TIME);

                //到达triage
                triage.arriveAtTriage(patient);
                //释放orderlies
                availableOrderlies = orderlies.releaseOrderlies();
                System.out.println("Nurse "+id+" releases "+Params.TRANSFER_ORDERLIES +
                        " orderlies ("+availableOrderlies+ " free).");
                //模拟诊断病人的时间
                sleep(Params.TRIAGE_TIME);
                //诊断完成, 招募orderlies并离开triage
                availableOrderlies = orderlies.recruitOrderlies();
                System.out.println("Nurse "+id+" recruits "+Params.TRANSFER_ORDERLIES +
                        " orderlies ("+availableOrderlies+ " free).");
                triage.departFromTriage();
                //模拟护送病人到foyer/treatment的时间
                sleep(Params.TRANSFER_TIME);

                //根据是否严重决定去向
                if (patient.Severe()){
                    //病人严重，送到treatment
                    treatment.arriveAtTreatment(patient);
                    //释放orderlies
                    availableOrderlies = orderlies.releaseOrderlies();
                    System.out.println("Nurse "+id+" releases "+Params.TRANSFER_ORDERLIES +
                            " orderlies ("+availableOrderlies+ " free).");
                    //由专家进行治疗
                    treatment.treatPatient();

                    //治疗完成，招募orderlies离开，回到foyer
                    availableOrderlies = orderlies.recruitOrderlies();
                    System.out.println("Nurse "+id+" recruits "+Params.TRANSFER_ORDERLIES +
                            " orderlies ("+availableOrderlies+ " free).");
                    treatment.departFromTreatment();
                    //模拟护送病人到foyer的时间
                    sleep(Params.TRANSFER_TIME);
                    //到达foyer，准备离开
                    foyer.prepareToLeaveED(patient);
                    //释放orderlies
                    availableOrderlies = orderlies.releaseOrderlies();
                    System.out.println("Nurse "+id+" releases "+Params.TRANSFER_ORDERLIES +
                            " orderlies ("+availableOrderlies+ " free).");
                    //护士结束任务，可以分配新的病人
                    System.out.println("Nurse "+id+" releases "+patient+".");
                    foyer.releasePatient();

                } else {
                    //病人不严重，返回foyer
                    //到达foyer，准备离开
                    foyer.prepareToLeaveED(patient);
                    //释放orderlies
                    availableOrderlies = orderlies.releaseOrderlies();
                    System.out.println("Nurse "+id+" releases "+Params.TRANSFER_ORDERLIES +
                            " orderlies ("+availableOrderlies+ " free).");
                    //护士结束任务，可以分配新的病人
                    System.out.println("Nurse "+id+" releases "+patient+".");
                    foyer.releasePatient();
                }

            }catch (InterruptedException e){
                this.interrupt();
            }


            /*
            to do:
            1. 病人admit要和allocate一起吗
            2. 释放病人前可以先discharge吗
            */
        }
    }

}
