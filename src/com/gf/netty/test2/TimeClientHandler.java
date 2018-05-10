package com.gf.netty.test2;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{

	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
	
	private final ByteBuf firstMessage;
	
	public TimeClientHandler(){
		byte[] req = "QUERY TINE ORDER".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(firstMessage);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req ,"UTF-8");
		System.out.println("Now is : " + body);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//释放资源
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());
		ctx.close();
	}
	
}
