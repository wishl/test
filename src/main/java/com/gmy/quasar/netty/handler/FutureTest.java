package com.gmy.quasar.netty.handler;

import java.util.concurrent.*;

/**
 * @Author Guanmengyuan
 * @Date Created in 08:53 2020-04-15
 */
public class FutureTest {

    private static ExecutorService executor =  Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Callable callable = ()->{
            return "success";
        };
        Future future = executor.submit(callable);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
