package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.client.*;
import fr.redboxing.wakfu.proxy.network.packets.server.*;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class GameClientHandler extends AbstractPacketHandler {
    private ClientSession session;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.session = new  ClientSession(ctx.channel());
        this.session.connectToWakfuGameServer();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        this.session.disconnectFromWakfuGameServer();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        super.channelRead0(ctx, msg);
        this.session.sendToServer(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handle(PacketVersion packet) {

    }

    @Override
    public void handle(PacketVersionResult packet) {

    }

    @Override
    public void handle(PacketRequestRSA packet) {

    }

    @Override
    public void handle(PacketRSAResult packet) {

    }

    @Override
    public void handle(PacketSetIP packet) {

    }

    @Override
    public void handle(PacketAuthentication packet) {

    }

    @Override
    public void handle(PacketDispatchAuthenticationResult packet) {

    }

    @Override
    public void handle(PacketAuthenticationResult packet) {

    }

    @Override
    public void handle(PacketAuthenticationTokenRequest packet) {

    }

    @Override
    public void handle(PacketAuthenticationTokenResult packet) {

    }

    @Override
    public void handle(PacketAuthenticationTokenRedeem packet) {

    }

    @Override
    public void handle(PacketProxiesRequest packet) {

    }

    @Override
    public void handle(PacketProxiesResult packet) {

    }

    @Override
    public void handle(PacketProxyRelayError packet) {

    }

    @Override
    public void handle(PacketWorldSelectionResult packet) {

    }

    @Override
    public void handle(PacketClientSystemConfiguration packet) {

    }

    @Override
    public void handle(PacketClientCalendarSynchronization packet) {

    }

    @Override
    public void handle(PacketClientAdditionalCharacterSlotsUpdate packet) {

    }

    @Override
    public void handle(PacketFreeCompanionBreedId packet) {

    }

    @Override
    public void handle(PacketEquipmentInventory packet) {

    }

    @Override
    public void handle(PacketCharacterList packet) {

    }

    @Override
    public void handle(PacketLanguage packet) {

    }
}
