package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.network.codec.PacketDecoder;
import fr.redboxing.wakfu.proxy.network.codec.PacketEncoder;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.utils.SSLUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();

        SslHandler sslHandler = SSLUtils.generateSelfSignedCertificateForServer().newHandler(ch.alloc());
        pl.addLast(sslHandler);
        pl.addLast("decoder", new PacketDecoder(Packet.PacketType.CLIENT_MSG));
        pl.addLast("encoder", new PacketEncoder());
        pl.addLast("handler", new ClientHandler());
    }
}
