package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.network.codec.GameServerPacketDecoder;
import fr.redboxing.wakfu.proxy.network.codec.PacketEncoder;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.SSLUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

public class GameServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ClientSession session;
    private final boolean ssl;

    public GameServerInitializer(ClientSession session, boolean ssl) {
        this.session = session;
        this.ssl = ssl;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();

        if(ssl) {
            SslHandler sslHandler = SSLUtils.generateSelfSignedCertificateForClient().newHandler(ch.alloc());
            pl.addLast(sslHandler);
        }

        pl.addLast("decoder", new GameServerPacketDecoder());
        pl.addLast("encoder", new PacketEncoder());
        pl.addLast("handler", new GameServerHandler(this.session));
    }
}
