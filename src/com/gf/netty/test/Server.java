package com.gf.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) throws Exception {
		
		//1 第一个线程组 是用于接收Client端连接的
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//2 第二个线程组 是用于实际的业务处理操作的
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		//3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的的配置
		ServerBootstrap b = new ServerBootstrap();
		//把两个线程组加入进来
		b.group(bossGroup, workerGroup)
		//我指定使用NioServerSocketChannel这中类型的通道
		.channel(NioServerSocketChannel.class)
		//一定要使用 childHandler 去绑定具体的 事件处理
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new ServerHandler());
			}
		});
		
		//绑定指定的端口 进行监听
		ChannelFuture f = b.bind(8765).sync();
		
		f.channel().closeFuture().sync();
		
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		
	}
	
}
