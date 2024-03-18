public class Specialist extends Thread {
    Treatment treatment;

    public Specialist(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // 完成在医院的其它职责
                Thread.sleep(Params.SPECIALIST_AWAY_TIME);
                // 忙完后进入treatment room
                treatment.specialistEnters();
                //等待治疗完成, 离开treatment room
                treatment.specialistLeaves();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
