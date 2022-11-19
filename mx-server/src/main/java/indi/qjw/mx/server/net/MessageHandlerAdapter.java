package indi.qjw.mx.server.net;

import indi.qjw.mx.common.constant.MessageEnum;
import indi.qjw.mx.common.message.BaseMessage;
import indi.qjw.mx.common.message.login.ResLoginMessage;
import indi.qjw.mx.common.proto.LoginProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 消息处理
 * @author: QJW
 * @date : 2022/10/15 8:59
 */
@Slf4j
public class MessageHandlerAdapter extends ChannelInboundHandlerAdapter {
    protected NetWorkEventListener listener;

    public MessageHandlerAdapter(NetWorkEventListener listener) {
        this.listener = listener;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (((BaseMessage)msg).getId() == MessageEnum.ReqLoginMessage.getMsgId()) {
            log.error("登录成功");
            ResLoginMessage loginMessage = new ResLoginMessage();
            loginMessage.proto = LoginProto.ResLogin.newBuilder().setUser("Success").build();
            Channel channel = ctx.channel();
            channel.writeAndFlush(loginMessage);
            log.error("登录成功");
        }
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onConnected(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onDisconnected(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception:" + ctx.channel(), cause);
        ctx.close();
    }
}
