package com.pingcap.tikv;

/**
 * Created by zhaoziming on 2017/6/23.
 */
public class TikvTest {
    public static void main(String[] args) {
        TikvClient client = new TikvClient("127.0.0.1:2379");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            long istart = System.currentTimeMillis();
            client.set("test","dsadasdfasdfasdfasdfasdfasdf".getBytes());
            long iend = System.currentTimeMillis();
            System.out.println("----------------complete:"+i+" time:"+(iend-istart));
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0+"s");
    }
}
