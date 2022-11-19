package indi.qjw.mx.common.message;

/**
 * @desc : 消息接口
 * @author: QJW
 * @date : 2022/10/20 22:13
 */
public interface Message {
    int getId();

    void decode(byte[] var1);

    byte[] encode();

    short getSequence();
}
