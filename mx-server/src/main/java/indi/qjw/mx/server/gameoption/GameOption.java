package indi.qjw.mx.server.gameoption;

import indi.qjw.mx.common.gameserverinfo.option.ServerArgs;
import indi.qjw.mx.common.util.IOUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * @desc : 配置
 * @author: QJW
 * @date : 2022/8/25 20:54
 */
@Getter
@Setter
public class GameOption {
    public GameNetOption gameNetOption = new GameNetOption();
    public GameBaseOption gameBaseOption = new GameBaseOption();

    private static final GameOption INSTANCE = new GameOption();

    public static GameOption getInstance() {
        return INSTANCE;
    }

    public GameOption() {
    }

    public void build() {
        try {
            Properties properties = IOUtil.getProperties(ServerArgs.path);
            gameBaseOption.ip = properties.getProperty("game.ip");
            gameBaseOption.port = Integer.parseInt(properties.getProperty("game.port"));
        } catch (Exception e) {
            throw new RuntimeException("服务器初始配置文件读取错误，启动失败......", e);
        }
    }
}
