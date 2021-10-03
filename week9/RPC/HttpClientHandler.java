package week9.RPC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import javax.management.InstanceNotFoundException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HttpClientHandler extends ChannelInboundHandlerAdapter {


    private Object reader;

    public void HttpClientHandler() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String className = "week9.RPC.ByteBufToBytes";
        Class<?>clazz = Class.forName(className);
        reader = clazz.newInstance();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse){
            HttpResponse response = (HttpResponse)msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaderNames.CONTENT_TYPE));
            if(HttpUtil.isContentLengthSet(response)){
                reader = new ByteBufToBytes((int)HttpUtil.getContentLength(response));
            }
        }
        if (msg instanceof HttpContent){
            HttpContent httpContent = (HttpContent)msg;
            ByteBuf content = httpContent.content();
            //reader.reading(content);
            reader.getClass().getMethod("reading",ByteBuf.class).invoke(reader,content);
            content.release();
            String resultStr = reader.getClass().getMethod("readFull",null).toString();
            System.out.println("Server said: " + resultStr);
            ctx.close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpClientHandler Active");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpClientHandler channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
