package indi.qjw.mx.server.net;

/**
 * @desc : 状态
 * @author: QJW
 * @date : 2022/10/5 10:10
 */
public enum NetWorkState {
    Wait(0),
    Create(1),
    Start(2),
    Stop(3);

    private int value;

    NetWorkState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
