package indi.qjw.mx.server.gameoption;

/**
 * @desc : 配置
 * @author: QJW
 * @date : 2022/8/25 20:54
 */
public class GameOption {
    public GameOption() {

    }

    private static final GameOption INSTANCE = new GameOption();

    public static GameOption getInstance() {
        return INSTANCE;
    }

    public void build() {
        try {

        } catch (Exception e) {
            throw new RuntimeException("服务器初始配置文件读取错误，启动失败......", e);
        }
    }
}
