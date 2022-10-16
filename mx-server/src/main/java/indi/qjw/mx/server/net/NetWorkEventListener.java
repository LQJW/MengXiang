package indi.qjw.mx.server.net;

import indi.qjw.mx.common.util.ChannelUtil;
import indi.qjw.mx.server.gameoption.GameOption;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @desc : 网络事件监听器
 * @author: QJW
 * @date : 2022/10/15 9:52
 */
public class NetWorkEventListener {
    public void onConnected(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        boolean closed = GameOption.getInstance().getGameBaseOption().isClosed();
        if (closed) {
            channel.close();
            return;
        }

        Session session = ChannelUtil.get(channel, ChannelAttrKey.CONNECT);
        if (session == null) {
            ChannelUtil.set(channel, ChannelAttrKey.CONNECT, session);
        }
    }

    public void onDisconnected(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Session session = ChannelUtil.get(channel, ChannelAttrKey.CONNECT);
        if (session != null) {
            Boolean disConnect = ChannelUtil.get(session.getChannel(), ChannelAttrKey.DISCONNECT);
        }
    }
}
