package indi.qjw.mx.common.gameserverinfo.option;

import lombok.Getter;

/**
 * @desc : 参数信息
 * @author: QJW
 * @date : 2022/8/25 20:48
 */
@Getter
public class ServerArgs {
    public static String[] args;
    public static String path;

    public ServerArgs() {
    }

    public static class ArgsBuilder {
        private String[] args;

        public ArgsBuilder() {
        }

        public static ArgsBuilder newBuilder() {
            return new ArgsBuilder();
        }

        public ArgsBuilder setArgs(String[] args) {
            this.args = args;
            return this;
        }

        public void build() {
            ServerArgs.args = args;
            ServerArgs.path = args[0];
        }
    }
}
