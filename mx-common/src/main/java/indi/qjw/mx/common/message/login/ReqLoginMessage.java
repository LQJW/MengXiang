package indi.qjw.mx.common.message.login;

import com.google.protobuf.InvalidProtocolBufferException;
import indi.qjw.mx.common.constant.MessageEnum;
import indi.qjw.mx.common.message.BaseMessage;
import indi.qjw.mx.common.proto.LoginProto;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 请求登录
 * @author: QJW
 * @date : 2022/10/22 11:03
 */
@Slf4j
public class ReqLoginMessage extends BaseMessage {
    private LoginProto.ReqLogin proto;

    @Override
    public int getId() {
        return MessageEnum.ReqLoginMessage.getMsgId();
    }

    @Override
    public void decode(byte[] bytes) {
        try {
            this.proto = LoginProto.ReqLogin.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            log.error("Message Decode Failed：" + this.getId(), e);
        }
    }

    @Override
    public byte[] encode() {
        return proto.toByteArray();
    }
}
