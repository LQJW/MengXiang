package indi.qjw.mx.server.gameserver;

import indi.qjw.mx.common.gameserverinfo.option.ServerArgs;

/**
 * @desc : 服务器信息
 * @author: QJW
 * @date : 2022/8/25 20:11
 */
public class GameServer {
    public GameServer() {

    }

    public static void initOption(String[] args) {
        ServerArgs.ArgsBuilder.newBuilder().setArgs(args).build();
    }

    public static void init() {

    }
}
