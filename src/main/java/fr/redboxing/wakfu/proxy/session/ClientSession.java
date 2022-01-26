package fr.redboxing.wakfu.proxy.session;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.GameServerInitializer;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

public class ClientSession {
    private Channel channel;
    private EventLoopGroup workerGroup;
    private EventLoopGroup workerGroup2;
    private Channel serverChannel;
    private SocketAddress address;

    public ClientSession(Channel channel) {
        this.channel = channel;
        this.address = channel.remoteAddress();
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void connectToWakfuServer() {
        this.workerGroup = new NioEventLoopGroup(1);
        try {
            final Bootstrap b = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class).handler(new GameServerInitializer(this, true));

            this.serverChannel = b.connect("dispatch.platforms.wakfu.com", 5558).sync().channel();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disconnectFromWakfuServer() {
        this.workerGroup.shutdownGracefully();
    }

    public void connectToWakfuGameServer() {
        this.workerGroup2 = new NioEventLoopGroup(1);
        try {
            final Bootstrap b = new Bootstrap();
            b.group(workerGroup2).channel(NioSocketChannel.class).handler(new GameServerInitializer(this, true));

            this.serverChannel = b.connect("pandora.platforms.wakfu.com", 5556).sync().channel();
            WakfuProxy.getInstance().getLogger().info("Connected to wakfu game server");
        }  catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disconnectFromWakfuGameServer() {
        this.workerGroup2.shutdownGracefully();
    }

    public void write(Packet packet) {
      //  WakfuProxy.getInstance().getLogger().info("[SERVER->CLIENT]: {} ({}).", packet.opcode, packet.getClass().getSimpleName());
        channel.writeAndFlush(packet);
    }

    public void sendToServer(Packet packet) {
     //   WakfuProxy.getInstance().getLogger().info("[CLIENT->SERVER]: {} ({})", packet.opcode, packet.getClass().getSimpleName());
        try {
            serverChannel.writeAndFlush(packet);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
