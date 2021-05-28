package com.yxf.jsdemo;

import com.orhanobut.logger.Logger;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/27 13:49
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/27 13:49
 * 0
 */
public class MulThreadTest {
    private int ticketNum = 100;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (ticketNum > 0) {
                try {
                    Thread.sleep(100);
                    sellTicket();
                    Logger.w("ticket num : %d", ticketNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * three thread sell at same time
     */
    public void startSell() {
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
//        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
//        t3.start();
    }

    //sell
    private void sellTicket() {
        //Logger.w("sell one ticket by ---%s", Thread.currentThread().getName());
        ticketNum--;
    }
}
