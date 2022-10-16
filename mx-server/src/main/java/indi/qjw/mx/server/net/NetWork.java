package indi.qjw.mx.server.net;

import indi.qjw.mx.server.gameoption.GameNetOption;
import indi.qjw.mx.server.gameoption.GameOption;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * @desc : 网络
 * @author: QJW
 * @date : 2022/10/5 10:08
 */
@Slf4j
@Getter
public class NetWork {
    private NetWorkState state = NetWorkState.Wait;

    public void create() {
        try {
            int port = GameOption.getInstance().getGameBaseOption().getPort();
            int bossCount = 4;
            int workCount = Math.max(Runtime.getRuntime().availableProcessors(), 8);
            NetWorkEventListener listener = new NetWorkEventListener();

            NetWorkServiceOption netWorkServiceOption = new NetWorkServiceOption();
            netWorkServiceOption.setPort(port);
            netWorkServiceOption.setBossLoopGroupCount(bossCount);
            netWorkServiceOption.setWorkLoopGroupCount(workCount);
            netWorkServiceOption.setListener(listener);
            NetWorkService netWorkService = netWorkServiceOption.createService();

            GameNetOption gameNetOption = GameOption.getInstance().getGameNetOption();
            gameNetOption.setNetWork(this);
            gameNetOption.setNetWorkService(netWorkService);
        } catch (Exception e) {
            log.error("NetWork create Failed", e);
        }
        state = NetWorkState.Create;
    }

    public void start() {
        GameNetOption gameNetOption = GameOption.getInstance().getGameNetOption();
        gameNetOption.getNetWorkService().start();
        state = NetWorkState.Start;
    }

    public void close() {
        GameNetOption gameNetOption = GameOption.getInstance().getGameNetOption();
        gameNetOption.getNetWorkService().close();
        state = NetWorkState.Stop;
    }
}
