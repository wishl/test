package com.gmy.quasar.netty.handler;

import com.gmy.quasar.netty.NettyClient;

/**
 * @Author Guanmengyuan
 * @Date Created in 16:19 2020-04-14
 */
public class ClientMain {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient(8088,"127.0.0.1");
        nettyClient.start();
    }

}
