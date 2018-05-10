package com.gf.netty.test9;

import com.gf.netty.test8.SubscribeReqProto;
import com.gf.netty.test8.SubscribeRespProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
		if("Lilinfeng".equalsIgnoreCase(req.getUserName())){
			System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
			
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
		
	}

	private SubscribeRespProto.SubscribeResp resp(int subReqID) {
		SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setRespCode(0);
		builder.setDesc("Netty book order succeed, 3 days later, sent to the designted address");
		return builder.build();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();// 发生异常，关闭链路
	}
	
}
