package indi.qjw.mx.server.net;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * @desc : 会话
 * @author: QJW
 * @date : 2022/10/15 10:20
 */
@Getter
@Setter
public class Session {
    private Channel channel;
}
