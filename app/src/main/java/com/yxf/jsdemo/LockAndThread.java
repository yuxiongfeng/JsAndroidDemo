package com.yxf.jsdemo;

import com.orhanobut.logger.Logger;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/27 9:38
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/27 9:38
 */
public class LockAndThread {

    public void testThread(){
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Logger.w("t1 thread running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.w("t2 thread running");
            }
        });
        t2.start();

    }

    public int fSum(int n){
        if (n==1) {
            return 1;
        }else{
            return fSum(n-1)+n;
        }
    }


}
