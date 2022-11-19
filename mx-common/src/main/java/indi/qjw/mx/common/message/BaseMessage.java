package indi.qjw.mx.common.message;

/**
 * @desc : 消息基类
 * @author: QJW
 * @date : 2022/10/22 10:48
 */
public class BaseMessage implements Message {
    private int length;
    private int msgId;
    private short sequence;
    private volatile byte[] content;

    public BaseMessage(int length, int msgId, short sequence, byte[] content) {
        this.length = length;
        this.msgId = msgId;
        this.sequence = sequence;
        this.content = content;
    }

    public BaseMessage() {

    }

    @Override
    public int getId() {
        return msgId;
    }

    @Override
    public void decode(byte[] var1) {

    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public short getSequence() {
        return 0;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
