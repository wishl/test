package com.gmy.quasar.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.util.Arrays;

@ChannelHandler.Sharable
public class InBoundHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        ByteBuf byteBuf = (ByteBuf) msg;
        byteBuf.resetReaderIndex();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println(Arrays.toString(bytes));
        byte[] response = new byte[]{(byte)1,(byte)2,(byte)3,(byte)4,(byte)5,(byte)6,(byte)7,(byte)8,(byte)9};
        ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(response)).sync();
        if(future.isSuccess()){
            System.out.println("handler2!");
        }else{
            System.out.println("handler2");
        }
    }

}
