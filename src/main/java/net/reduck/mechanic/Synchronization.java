package net.reduck.mechanic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Gin
 * @since 2023/8/1 15:49
 */
public class Synchronization {

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
        Test test = new Test();

        executor.execute(() -> test.test1());
        executor.execute(() -> test.test2());

        Thread.sleep(2000);
        System.exit(0);
    }
    public static class Test {
        public synchronized void test1() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(333);
        }

        public synchronized void test2() {
            System.out.println(444);
        }
    }
}
