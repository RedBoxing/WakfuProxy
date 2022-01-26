package fr.redboxing.wakfu.proxy;

import fr.redboxing.wakfu.proxy.network.ClientInitializer;
import fr.redboxing.wakfu.proxy.network.GameClientInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WakfuProxy {
    private Logger logger = LogManager.getLogger("WakfuProxy");
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup bossGroup2 = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup2 = new NioEventLoopGroup();
    private MainForm form;
    private static WakfuProxy instance;

    public WakfuProxy() {
        instance = this;
        form = new MainForm();
    }

    public void start() {
        long start = System.currentTimeMillis();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(5558).childHandler(new ClientInitializer());

        try {
            log("Listening on port 5558.");
            log("Server took " + (System.currentTimeMillis() - start) + " milliseconds to start up.");
            b.bind().awaitUninterruptibly().channel().closeFuture().awaitUninterruptibly();
        } catch (Exception e) {
            log("Could not listen to port 5558.", e);
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log("Server Stopped.");
    }

    public void startGameProxy() {
        long start = System.currentTimeMillis();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup2, workerGroup2).channel(NioServerSocketChannel.class).localAddress(5556).childHandler(new GameClientInitializer());

        try {
            log("Listening on port 5556.");
            log("Game Server took " + (System.currentTimeMillis() - start) + " milliseconds to start up.");
            b.bind().awaitUninterruptibly().channel().closeFuture().awaitUninterruptibly();
        } catch (Exception e) {
            log("Could not listen to port 5556.", e);
        }
    }

    public void stopGameProxy() {
        this.bossGroup2.shutdownGracefully();
        this.workerGroup2.shutdownGracefully();
    }

    public void log(String str, Object... args) {
        logger.info(str, args);
        form.getLogArea().append(str + "\n");
    }

    public void error(String err, Object... args) {
        logger.error(err, args);
        form.getLogArea().append("Error: " + err + "\n");
    }

    public static void main(String[] args) {
        instance = new WakfuProxy();
    }

    public MainForm getForm() {
        return form;
    }

    public Logger getLogger() {
        return logger;
    }

    public static WakfuProxy getInstance() {
        return instance;
    }
}
