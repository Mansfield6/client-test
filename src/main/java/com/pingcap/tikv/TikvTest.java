package com.pingcap.tikv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class TikvTest {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/ilovesoup1/workspace/pingcap/elem.txt");
        FileOutputStream os = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(os);
        TikvClient client = new TikvClient("127.0.0.1:2379");
        Set<Long> existing = new TreeSet<>();
        Random rnd = new Random();
        long start = System.currentTimeMillis();
        for (long i = 0; i < 10000000000L; i++) {
            long v = rnd.nextLong() % 1000000;
            if (existing.contains(v)) {
                byte[] val = client.get(Long.toString(v));
                if (val == null) {
                    pw.println("getting key is null: " + v);
                    continue;
                }
                String lVal = new String(val);
                if (!lVal.equals(Long.toString(v + 1))) {
                    pw.println("error getting value of key: " + v);
                }
                pw.println("got value " + v);
                client.del(Long.toString(v));
                existing.remove(v);
                pw.println("delete " + v);
            } else {
                client.set(Long.toString(v), Long.toString(v + 1).getBytes());
                existing.add(v);
                pw.println("add " + v);
            }
            pw.flush();
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0+"s");
    }
}
