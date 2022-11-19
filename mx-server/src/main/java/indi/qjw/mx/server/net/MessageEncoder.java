package indi.qjw.mx.server.net;

import indi.qjw.mx.common.message.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 编码
 * @author: QJW
 * @date : 2022/10/20 22:43
 */
@Slf4j
public class MessageEncoder extends MessageToByteEncoder<BaseMessage> {
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf out) {
        try {
            byte[] content = msg.encode();
            int length = 10 + content.length;
            msg.setLength(length);
            out.writeInt(length);
            out.writeInt(msg.getId());
            out.writeShort(msg.getSequence());
            out.writeBytes(content);
            //log.debug("Message Encode Success, msg:{}", msg);
        } finally {
            log.error("Message Encode Failed, msg:{}", msg);
        }
    }
}
