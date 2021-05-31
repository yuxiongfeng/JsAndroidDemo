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

    private int ticketNum = 50;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (ticketNum > 0) {
                try {
                    Thread.sleep(100);
                    sellTicket();

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    sellTicket();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    sellTicket();
                }
            }
        }).start();
    }

    //sell
    private synchronized void sellTicket() {
        if (ticketNum>1) {
            try{
                ticketNum--;
                Logger.w("ticket num : %d thread :%s", ticketNum,Thread.currentThread().getName());
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
