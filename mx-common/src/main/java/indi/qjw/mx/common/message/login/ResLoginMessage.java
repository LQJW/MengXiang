package indi.qjw.mx.common.message.login;

import com.google.protobuf.InvalidProtocolBufferException;
import indi.qjw.mx.common.constant.MessageEnum;
import indi.qjw.mx.common.message.BaseMessage;
import indi.qjw.mx.common.proto.LoginProto;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 返回登录
 * @author: QJW
 * @date : 2022/10/28 23:08
 */
@Slf4j
public class ResLoginMessage extends BaseMessage {
    public LoginProto.ResLogin proto;

    @Override
    public int getId() {
        return MessageEnum.ResLoginMessage.getMsgId();
    }

    @Override
    public void decode(byte[] bytes) {
        try {
            this.proto = LoginProto.ResLogin.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            log.error("Message Decode Failed：" + this.getId(), e);
        }
    }

    @Override
    public byte[] encode() {
        return proto.toByteArray();
    }
}
