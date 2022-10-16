package indi.qjw.mx.server.gameserver;

import indi.qjw.mx.server.gameoption.GameOption;
import indi.qjw.mx.server.net.NetWorkManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 关服
 * @author: QJW
 * @date : 2022/10/15 10:59
 */
@Slf4j
public class GameCloseThread extends Thread {
    public GameCloseThread() {
        super();
    }

    @Override
    public void run() {
        log.error("GameServer Stop...");
        GameOption.getInstance().gameBaseOption.setClosed(true);

        log.info("close connection...");
        closeConnection();

        log.info("close netWork...");
        closeNetWork();
        
        log.info("save data...");
        saveData();

        log.info("exit");
        Runtime.getRuntime().halt(0);
    }

    public void closeConnection() {

    }

    public void closeNetWork() {
        NetWorkManager.getInstance().stopNetWork();
    }

    public void saveData() {

    }
}
