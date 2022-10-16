package indi.qjw.mx.server.gameserver;

import indi.qjw.mx.common.gameserverinfo.option.ServerArgs;
import indi.qjw.mx.server.gameoption.GameOption;
import indi.qjw.mx.server.net.NetWorkManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 服务器信息
 * @author: QJW
 * @date : 2022/8/25 20:11
 */
@Slf4j
public class GameServer {
    public GameServer() {

    }

    public static void initOption(String[] args) {
        ServerArgs.ArgsBuilder.newBuilder().setArgs(args).build();
        GameOption.getInstance().build();
    }

    public static void init() {
        //初始化网络服务
        NetWorkManager.getInstance().createNetwork();
        NetWorkManager.getInstance().startNetwork();

        Runtime.getRuntime().addShutdownHook(new GameCloseThread());
        log.error("GameServer startup completed", Thread.currentThread().getId());
    }
}
