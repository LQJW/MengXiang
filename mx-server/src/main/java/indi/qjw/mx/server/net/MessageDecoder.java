package indi.qjw.mx.server.net;

import indi.qjw.mx.common.constant.MessageEnum;
import indi.qjw.mx.common.message.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 解码
 * @author: QJW
 * @date : 2022/10/20 22:44
 */
@Slf4j
public class MessageDecoder extends LengthFieldBasedFrameDecoder {
    private int count = 0;
    public MessageDecoder() {
        this(1048576, 0, 4, -4, 0);
    }

    private MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) {
            return null;
        } else {
            try {
                int length = in.readInt();
                int msgId = in.readInt();
                short sequence = in.readShort();
                byte[] bytes = null;
                int remainLength = in.readableBytes();
                if (remainLength > 0) {
                    bytes = new byte[remainLength];
                    in.readBytes(bytes);
                } else if (remainLength == 0) {
                    bytes = new byte[0];
                }

                if (null == MessageEnum.get(msgId)) {
                    log.error("Not Register MessageId, msgId:{}", msgId);
                    return null;
                } else {
                    return new BaseMessage(length, msgId, sequence, bytes);
                }
            } catch (Throwable e) {
                log.error(ctx.channel() + "Message Decode Failed" + e);
                return null;
            } finally {
                ReferenceCountUtil.release(in);
            }
        }
    }
}
