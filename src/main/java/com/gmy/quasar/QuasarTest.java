package com.gmy.quasar;

import co.paralleluniverse.fibers.Fiber;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Author Guanmengyuan
 * @Date Created in 17:36 2020-04-12
 */
public class QuasarTest {

    BlockingQueue<Integer> queue = new ArrayBlockingQueue(100);

    public void doPut() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            Fiber<Integer> fiber = new Fiber<>(()->{
                Thread.sleep(1);
                System.out.println("------");
                return 1;
            }).start();
            Integer result = fiber.get();
            queue.offer(result);
        }
    }

    public void doGet(){
        while (true) {
            try {
                Integer take = queue.take();
                System.out.println(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        QuasarTest quasarTest = new QuasarTest();
        try {
            quasarTest.doPut();
            quasarTest.doGet();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
