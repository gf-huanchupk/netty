package com.gf.netty.test9;

import java.util.ArrayList;
import java.util.List;

import com.gf.netty.test8.SubscribeReqProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter{
	
	public SubReqClientHandler(){
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	private SubscribeReqProto.SubscribeReq subReq(int i) {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(i);
		builder.setUserName("Lilinfeng");
		builder.setProductName("Netty Book For Protobuf");
		List<String> address = new ArrayList<>();
		address.add("NanJing YuhuaTai");
		address.add("BeiJin LiuLiChang");
		address.add("ShenZhen HongShuLin");
		builder.addAllAddress(address);
		
		return builder.build();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Receive server response : [" + msg + "]");
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
	
}
