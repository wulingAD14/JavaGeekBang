package week9.RPC;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpClient {

    private String host;
    private int port;

    public HttpClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{
                            //客户端收到HttpResponse响应
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            //客户端发送HttpRequest请求
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new HttpClientHandler());
                        }
                    });
            //连接到服务器，connect是异步连接，在调用同步等待sync
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            URI uri = new URI("http://127.0.0.1:8080");
            String msg = "are you OK?";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes()));
            //构建http请求
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.CONNECTION);
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            request.headers().set("messageType","normal");
            request.headers().set("businessType","testServerState");
            //发送http请求
            channelFuture.channel().write(request);
            channelFuture.channel().flush();
            //阻塞知道客户端通道关闭
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new HttpClient("127.0.0.1", 8080).start();
    }

}
