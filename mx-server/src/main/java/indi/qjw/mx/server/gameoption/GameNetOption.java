package indi.qjw.mx.server.gameoption;

import indi.qjw.mx.server.net.NetWork;
import indi.qjw.mx.server.net.NetWorkService;
import lombok.Getter;
import lombok.Setter;

/**
 * @desc : 网络配置
 * @author: QJW
 * @date : 2022/8/25 20:58
 */
@Getter
@Setter
public class GameNetOption {
    private NetWork netWork;
    private NetWorkService netWorkService;
}
