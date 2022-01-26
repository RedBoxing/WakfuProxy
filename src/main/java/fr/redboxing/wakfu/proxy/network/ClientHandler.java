package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.client.*;
import fr.redboxing.wakfu.proxy.network.packets.server.*;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.channel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandler extends AbstractPacketHandler {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class.getSimpleName());
    private ClientSession session;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.session = new ClientSession(ctx.channel());
        this.session.connectToWakfuServer();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)  {
        this.session.disconnectFromWakfuServer();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        super.channelRead0(ctx, msg);
        this.session.sendToServer(msg);
    }

    @Override
    public void handle(PacketVersion packet) {
        LOGGER.info("Received version packet: {build={}.{}.{} ({})}", new Object[]{ packet.getMajor(), packet.getMinor(), packet.getPatch(), packet.getBuildVersion()});
    }

    @Override
    public void handle(PacketVersionResult packet) {}

    @Override
    public void handle(PacketRequestRSA packet) {}

    @Override
    public void handle(PacketRSAResult packet) {}

    @Override
    public void handle(PacketSetIP packet) {}

    @Override
    public void handle(PacketAuthentication packet) {}

    @Override
    public void handle(PacketDispatchAuthenticationResult packet) {}

    @Override
    public void handle(PacketAuthenticationResult packet) {}

    @Override
    public void handle(PacketAuthenticationTokenRequest packet) {
        LOGGER.info("Received Authentication Token Request: " + packet);
    }

    @Override
    public void handle(PacketAuthenticationTokenResult packet) {}

    @Override
    public void handle(PacketAuthenticationTokenRedeem packet) {
        LOGGER.info("Received Authentication Token Redeem: " + packet);
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
