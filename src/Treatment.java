
public class Treatment {
    private Patient currentPatient = null;
    private boolean specialistAvailable = false;

    //病人到达treatment room
    public synchronized void arriveAtTreatment(Patient patient) throws InterruptedException {
        //如果当前有病人在接受治疗，则一直等待
        while (currentPatient != null) {
            wait();
        }
        currentPatient = patient;
        System.out.println(patient + " enters treatment room.");
        //通知有病人来了
        notifyAll();
    }

    //专家进入treatment room
    public synchronized void specialistEnters() {
        specialistAvailable = true;
        System.out.println("Specialist enters treatment room.");
        notifyAll();
    }

    //专家离开treatment room 在治疗一个病人前不能离开
    public synchronized void specialistLeaves() throws InterruptedException {
        //如果专家还未治疗好病人，则一直等待
        //专家调用此函数前肯定已进入，specialistAvailable为true
        //故循环结束说明治疗已完成，specialistAvailable被treatPatient设置为false
        while (specialistAvailable == true) {
            wait();
        }
        System.out.println("Specialist leaves treatment room.");
    }

    //由护士调用，治疗病人
    public synchronized void treatPatient() throws InterruptedException {
        //如果当前没有病人或专家不在，则一直等待
        while (!specialistAvailable) {
            wait();
        }
        System.out.println(currentPatient + " treatment started.");
        //模拟治疗病人的时间
        Thread.sleep(Params.TREATMENT_TIME);

        System.out.println(currentPatient + " treatment complete.");
        //设置专家不可用，因为不能连续治疗两个病人
        specialistAvailable = false;
        currentPatient.setTreated(true);
        //通知专家可以离开
        notifyAll();
    }

    public synchronized void departFromTreatment() {
        System.out.println(currentPatient + " leaves treatment room.");
        currentPatient = null;
        //通知有病人离开treatment room
        notifyAll();
    }
}
