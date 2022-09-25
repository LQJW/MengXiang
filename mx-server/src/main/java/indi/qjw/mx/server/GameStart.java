package indi.qjw.mx.server;

import indi.qjw.mx.server.gameserver.GameServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 游戏启动入口
 * @author: QJW
 * @date : 2022/8/25 19:28
 */
@Slf4j
public class GameStart {
    public static void main(String[] args) {
        try {
            log.info("GameServer Starting");
            GameServer.initOption(args);
            GameServer.init();

        } catch (Exception e) {
            log.error("GameServer Fail",e);
            System.exit(-1);
        }
    }
}
