package com.pingcap.tikv;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaoziming on 2017/6/23.
 */
public class TikvTest implements Runnable{
    static  AtomicInteger count = new AtomicInteger();
    TikvClient client = new TikvClient("10.101.35.23:2379");
    CountDownLatch latch;
    int loopCount;
    public TikvTest(int loopCount,CountDownLatch latch){
        this.loopCount = loopCount;
        this.latch = latch;
    }

    public static void main(String[] args) {

        int tcount = 1;
        int loopCount =10;
        ExecutorService pool = Executors.newFixedThreadPool(tcount);


        CountDownLatch latch = new CountDownLatch(tcount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < tcount; i++) {
            TikvTest r = new TikvTest(loopCount,latch);
            pool.execute(r);
        }
        try {
            latch.await();
            System.out.println("=-------------------------------------=");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("========================" + (end - start) / 1000.0 + "s");
    }



    @Override
    public void run() {
        for (int i = 0; i < loopCount; i++) {
            client.set(Thread.currentThread().getName() + "test" + i, "dsadasdfasdfasdfasdfasdfasdf".getBytes());
//            System.out.println(Thread.currentThread().getName()+":"+i);
            count.incrementAndGet();
        }
        System.out.println(count.get());
        latch.countDown();
    }
}
