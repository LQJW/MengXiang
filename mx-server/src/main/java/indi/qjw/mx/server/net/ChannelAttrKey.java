package indi.qjw.mx.server.net;

import io.netty.util.AttributeKey;

/**
 * @desc : key
 * @author: QJW
 * @date : 2022/10/15 10:25
 */
public class ChannelAttrKey {
    public static final AttributeKey<Session> CONNECT = AttributeKey.newInstance("CONNECT");
    public static final AttributeKey<Boolean> DISCONNECT = AttributeKey.newInstance("DISCONNECT");
}
