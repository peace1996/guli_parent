package com.atguigu.eduservice.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * 初始化器，channel注册后，会执行里面的响应的初始化方法
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //通过SocketChannel获得对应的管道
        ChannelPipeline pipeline = ch.pipeline();
        //通过管道添加handler
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        //添加自定义助手类，给客户端浏览器渲染hello netty
        pipeline.addLast("CustomHandler",new CustomHandler());
    }
}
