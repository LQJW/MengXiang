package indi.qjw.mx.server.net;

import lombok.Getter;
import lombok.Setter;

/**
 * @desc : 网络创建参数
 * @author: QJW
 * @date : 2022/10/15 9:17
 */
@Getter
@Setter
public class NetWorkServiceOption {
    private int port;
    private int bossLoopGroupCount;
    private int workLoopGroupCount;

    private NetWorkEventListener listener;

    public NetWorkService createService() {
        return new NetWorkService(this);
    }
}
