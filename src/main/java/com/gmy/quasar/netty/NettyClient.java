package com.gmy.quasar.netty;

import com.gmy.quasar.netty.handler.ClientInBoundHandler;
import com.gmy.quasar.netty.handler.InBoundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author Guanmengyuan
 * @Date Created in 15:53 2020-04-14
 */
public class NettyClient {

    private String host;

    private int port;

    private ClientInBoundHandler clientInBoundHandler;

    public void start(){
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
//                pipeline.addLast(new StringDecoder());
//                pipeline.addLast(new StringEncoder());
                pipeline.addLast(clientInBoundHandler);
            }
        });
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("client启动");
            Channel channel = future.channel();
            channel.writeAndFlush(Unpooled.copiedBuffer(new byte[]{0x7e,0x77,0x7e}));
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        clientInBoundHandler = new ClientInBoundHandler();
    }

    public NettyClient(int port,String host){
        init();
        this.host = host;
        this.port = port;
    }

}
