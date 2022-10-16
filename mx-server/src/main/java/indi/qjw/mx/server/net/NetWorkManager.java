package indi.qjw.mx.server.net;

import indi.qjw.mx.server.gameoption.GameNetOption;
import indi.qjw.mx.server.gameoption.GameOption;
import lombok.Getter;

/**
 * @desc : 网络管理
 * @author: QJW
 * @date : 2022/10/5 10:02
 */
@Getter
public class NetWorkManager {
    private NetWorkManager() {

    }

    private static final NetWorkManager INSTANCE = new NetWorkManager();

    public static NetWorkManager getInstance() {
        return INSTANCE;
    }

    public void createNetwork() {
        NetWork network = new NetWork();
        network.create();
    }

    public void startNetwork() {
        GameNetOption gameNetOption = GameOption.getInstance().getGameNetOption();
        NetWork n = gameNetOption.getNetWork();
        n.start();
    }

    public void stopNetWork() {
        GameNetOption gameNetOption = GameOption.getInstance().getGameNetOption();
        gameNetOption.getNetWork().close();
    }
}
