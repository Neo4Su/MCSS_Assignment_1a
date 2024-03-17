package all.peopleVar;

import all.Params;

public class Orderlies {
    //空闲的orderlies数量
    private volatile int availableOrderlies;

    //根据Params中的参数，初始化orderlies
    public Orderlies(){
        this.availableOrderlies = Params.ORDERLIES;
    }

    //用于招募orderlies，并返回剩余的空闲orderlies数量
    public synchronized int recruitOrderlies() throws InterruptedException {
        //如果没有空闲的orderlies，则一直等待
        while (availableOrderlies < Params.TRANSFER_ORDERLIES){
            wait();
        }
        availableOrderlies -= Params.TRANSFER_ORDERLIES;
        return availableOrderlies;
    }

    //orderlies协助nurse护送病人后，用于释放orderlies资源
    public synchronized int releaseOrderlies(){
        availableOrderlies += Params.TRANSFER_ORDERLIES;
        notifyAll();
        return availableOrderlies;
    }
}
