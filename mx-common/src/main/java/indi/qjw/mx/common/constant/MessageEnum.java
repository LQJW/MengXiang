package indi.qjw.mx.common.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc : 消息
 * @author: QJW
 * @date : 2022/10/20 23:04
 */
@Getter
public enum MessageEnum {
    //大组id * 1000 + 小组id
    ReqLoginMessage(1001, "请求登录"),
    ResLoginMessage(1002, "返回登录"),
    ;

    private int msgId;
    private String desc;


    MessageEnum(int msgId, String desc) {
        this.msgId = msgId;
        this.desc = desc;
    }

    private static Map<Integer, MessageEnum> map = new HashMap<>();

    static {
        for (MessageEnum messageEnum : values()) {
            map.put(messageEnum.getMsgId(), messageEnum);
        }
    }

    public static MessageEnum get(int msgId) {
        return map.get(msgId);
    }
}
