package com.atguigu.eduservice.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送请求，服务器给予响应
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {
        //创建一组线程池
        //主线程池：用于接收客服端的请求链接，不做任何处理
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        //从线程池：主线程池的任务交给从线程池
        EventLoopGroup minorGroup = new NioEventLoopGroup();
        try {
            //创建服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置主从线程组
            serverBootstrap.group(mainGroup, minorGroup)
                    //设置nio双向通道
                    .channel(NioServerSocketChannel.class)
                    //添加子处理器，用于处理从线程池的任务
                    .childHandler(new NettyServerInitializer());
            //启动服务并设置端口号，启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            //监听关闭的channel,设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            minorGroup.shutdownGracefully();
        }

    }

}
