package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.network.codec.ClientPacketDecoder;
import fr.redboxing.wakfu.proxy.network.codec.GameClientPacketDecoder;
import fr.redboxing.wakfu.proxy.network.codec.PacketEncoder;
import fr.redboxing.wakfu.proxy.utils.SSLUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

public class GameClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();

        SslHandler sslHandler = SSLUtils.generateSelfSignedCertificateForServer().newHandler(ch.alloc());
        pl.addLast(sslHandler);
        pl.addLast("decoder", new GameClientPacketDecoder());
        pl.addLast("encoder", new PacketEncoder());
        pl.addLast("handler", new GameClientHandler());
    }
}
