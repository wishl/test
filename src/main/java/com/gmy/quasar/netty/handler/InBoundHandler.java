package com.gmy.quasar.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;


/**
 * @Author Guanmengyuan
 * @Date Created in 10:19 2020-04-14
 */
@ChannelHandler.Sharable
public class InBoundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println(Arrays.toString(bytes));
        byte[] response = new byte[]{(byte)1,(byte)2,(byte)3,(byte)4,(byte)5,(byte)6,(byte)7,(byte)8,(byte)9};
        ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(response)).sync();
        if(future.isSuccess()){
            System.out.println("success!");
        }else{
            System.out.println("error");
        }
    }
}
