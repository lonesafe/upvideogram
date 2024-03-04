package com.roubsite.trans.server;

import com.alibaba.fastjson.JSONObject;
import com.roubsite.trans.Trans;
import com.roubsite.trans.TransUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class Handle extends SimpleChannelInboundHandler {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String s = buf.toString(CharsetUtil.UTF_8);
        System.out.println("收到消息：" + s);
        String message;
        switch (s) {
            case "getNowList":
                message = (JSONObject.toJSONString(Trans.fileList));
                break;
            case "getNowTrans":
                Map<String, Object> map = new HashMap<>();
                map.put("p", TransUtils.COMPLETE);
                map.put("f", TransUtils.nowFile);
                message = (JSONObject.toJSONString(map));
                break;
            default:
                message = ("错误的参数");
                break;

        }
        ctx.channel().writeAndFlush(message);
        System.out.println(message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务开启成功");
    }

    // 所有异常都在这里处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();  // 一般情况下出现异常就关闭该连接
    }
}
