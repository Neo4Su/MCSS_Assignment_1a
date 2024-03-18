public class Orderlies {
    //空闲的orderlies数量
    private volatile int availableOrderlies;

    //根据Params中的参数，初始化orderlies
    public Orderlies(){
        this.availableOrderlies = Params.ORDERLIES;
    }

    //用于招募orderlies
    public synchronized void recruitOrderlies(int NurseId) throws InterruptedException {
        //如果没有空闲的orderlies，则一直等待
        while (availableOrderlies < Params.TRANSFER_ORDERLIES){
            wait();
        }
        //招募orderlies
        availableOrderlies -= Params.TRANSFER_ORDERLIES;
        System.out.println("Nurse "+NurseId+" recruits "+Params.TRANSFER_ORDERLIES +
                " orderlies ("+availableOrderlies+ " free).");
    }

    //orderlies协助nurse护送病人后，用于释放orderlies资源
    public synchronized void releaseOrderlies(int NurseId){
        availableOrderlies += Params.TRANSFER_ORDERLIES;
        System.out.println("Nurse "+NurseId+" releases "+Params.TRANSFER_ORDERLIES +
                " orderlies ("+availableOrderlies+ " free).");
        notifyAll();
    }
}
