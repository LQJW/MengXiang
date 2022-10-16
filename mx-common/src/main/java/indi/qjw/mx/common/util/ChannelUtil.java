package indi.qjw.mx.common.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @desc : channel
 * @author: QJW
 * @date : 2022/10/15 10:02
 */
public class ChannelUtil {
    public static <T> T get(Channel channel, AttributeKey<T> key) {
        return channel.attr(key).get();
    }

    public static <T> void set(Channel channel, AttributeKey<T> key, T value) {
        channel.attr(key).set(value);
    }
}
