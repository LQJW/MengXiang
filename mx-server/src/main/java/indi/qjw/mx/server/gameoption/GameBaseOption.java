package indi.qjw.mx.server.gameoption;

import lombok.Getter;
import lombok.Setter;

/**
 * @desc : 基础配置
 * @author: QJW
 * @date : 2022/8/25 20:57
 */
@Getter
@Setter
public class GameBaseOption {
    public String ip;
    public int port;

    /**
     * 游戏服是否关闭
     */
    private boolean closed;
}
