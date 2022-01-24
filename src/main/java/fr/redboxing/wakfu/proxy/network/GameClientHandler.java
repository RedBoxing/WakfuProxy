package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class GameClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final AttributeKey<ClientSession> CLIENTSESS_ATTR = AttributeKey.newInstance("ClientSession2");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(CLIENTSESS_ATTR).setIfAbsent(new ClientSession(ctx.channel()));

        ClientSession session = ctx.channel().attr(CLIENTSESS_ATTR).get();
        session.connectToWakfuGameServer();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientSession sess = ctx.channel().attr(CLIENTSESS_ATTR).get();
        sess.disconnectFromWakfuGameServer();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
