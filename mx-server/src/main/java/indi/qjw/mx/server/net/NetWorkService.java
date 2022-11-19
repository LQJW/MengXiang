package indi.qjw.mx.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc : 网络服务
 * @author: QJW
 * @date : 2022/10/15 8:36
 */
@Slf4j
public class NetWorkService {
    private int port;
    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    /**
     * 可以尝试ssl加密
     * @param netWorkServiceOption
     */
    NetWorkService(NetWorkServiceOption netWorkServiceOption) {
        port = netWorkServiceOption.getPort();
        bossGroup = new NioEventLoopGroup(netWorkServiceOption.getBossLoopGroupCount());
        workerGroup = new NioEventLoopGroup(netWorkServiceOption.getWorkLoopGroupCount());

        this.serverBootstrap.group(bossGroup, workerGroup);
        this.serverBootstrap.channel(NioServerSocketChannel.class);
        this.serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        this.serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        this.serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                MessageHandlerAdapter messageHandlerAdapter = new MessageHandlerAdapter(netWorkServiceOption.getListener());
                pipeline.addLast(new MessageEncoder());
                pipeline.addLast(new MessageDecoder());
                pipeline.addLast(messageHandlerAdapter);
            }
        });
    }

    public void start() {
        try {
            this.serverBootstrap.bind(port).sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.error("Netty Server start, port:{}", port);
    }

    public void close() {
        try {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        } catch (Exception e) {
            log.error("Netty Server close failed", e);
        }
        log.error("Netty Server close, port:{}", port);
    }
}
