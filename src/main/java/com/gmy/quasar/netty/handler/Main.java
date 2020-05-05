package com.gmy.quasar.netty.handler;

import com.gmy.quasar.netty.NettyServer;

/**
 * @Author Guanmengyuan
 * @Date Created in 15:40 2020-04-14
 */
public class Main {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(8088);
        nettyServer.startServer();
    }

}
