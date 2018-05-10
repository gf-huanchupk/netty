package com.gf.netty.test10;

import com.gf.netty.test9.MarshallingCodeCFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SubReqClient {

	public void connect(int port , String host) throws Exception{
		// 配置客户端 Nio 线程组
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					ch.pipeline().addLast(new SubReqClientHandler());
				}
			});
			
			// 发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();
			
			// 等待客户端链路关闭
			f.channel().closeFuture().sync();
			
		} finally {
			// 优雅的退出，释放 NIO 线程组
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		try {
			if(args != null && args.length > 0){
				port = Integer.valueOf(args[0]);
			}
		} catch (Exception e) {
			// 采用默认值
		}
		
		new SubReqClient().connect(port,"127.0.0.1");
	}
	
}
