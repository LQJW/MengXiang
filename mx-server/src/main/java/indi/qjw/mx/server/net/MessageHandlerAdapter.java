package indi.qjw.mx.server.net;

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

    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onConnected(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onDisconnected(ctx);
    }
}
