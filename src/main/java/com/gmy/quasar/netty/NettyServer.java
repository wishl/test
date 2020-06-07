package com.gmy.quasar.netty;

import com.gmy.quasar.netty.handler.InBoundHandler;
import com.gmy.quasar.netty.handler.InBoundHandler2;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author Guanmengyuan
 * @Date Created in 10:10 2020-04-14
 */
public class NettyServer {


    private InBoundHandler inBoundHandler;

    private InBoundHandler2 inBoundHandler2;

    private int port;

    private volatile boolean flag;

    public synchronized void startServer(){
        if(flag)
            throw new RuntimeException("服务端已启动,port:"+port);
        // 保证其他线程无法启动
        this.flag = true;
        // 1.定义server启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 2.创建eventLoop,boss 用于接收请求,work用于处理读写
        EventLoopGroup bossGroup =  new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        // 定义工作组
        serverBootstrap.group(bossGroup,workGroup);
        // 设置channel
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 设置channel的handler
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
//                pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[]{0x7e}),
//                        Unpooled.copiedBuffer(new byte[]{0x7e,0x7e})));
//                pipeline.addLast(new StringEncoder());
                pipeline.addLast(inBoundHandler);
                pipeline.addLast(inBoundHandler2);
            }
        }).option(ChannelOption.SO_BACKLOG,2048);
        try {
            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind("0.0.0.0", this.port).sync();
            System.out.println("server端");
            // 监听关闭
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private void init(){
        this.inBoundHandler = new InBoundHandler();
        this.inBoundHandler2 = new InBoundHandler2();
    }


    public NettyServer(int port){
        init();
        this.port = port;
    }

}
