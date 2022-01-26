package fr.redboxing.wakfu.proxy.network;

import fr.redboxing.wakfu.proxy.crypto.RSACertificateManager;
import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.client.*;
import fr.redboxing.wakfu.proxy.network.packets.server.*;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;

public class GameServerHandler extends AbstractPacketHandler {
    private static final Logger LOGGER = LogManager.getLogger(GameServerHandler.class.getSimpleName());
    private final ClientSession session;
    private byte[] officialKey;

    public GameServerHandler(ClientSession session) {
        this.session = session;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        super.channelRead0(ctx, msg);
        this.session.write(msg);
    }

    @Override
    public void handle(PacketVersion packet) {}

    @Override
    public void handle(PacketVersionResult packet) {
        LOGGER.info("Received version result packet: {build={}.{}.{} ({}), status={}}", new Object[]{ packet.getMajor(), packet.getMinor(), packet.getPatch(), packet.getBuildVersion(), packet.isAccepted() });
    }

    @Override
    public void handle(PacketRequestRSA packet) {}

    @Override
    public void handle(PacketRSAResult packet) {
        this.officialKey = packet.getKey();
        packet.setKey(RSACertificateManager.INSTANCE.getPublicKey());
    }

    @Override
    public void handle(PacketSetIP packet) {
        try {
            LOGGER.info("Received set ip packet: { ip: {} }", new Object[] { InetAddress.getByName(String.valueOf(packet.getIp())).getHostAddress() });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handle(PacketAuthentication packet) {
        LOGGER.info("Received login packet: { username: {}, password: {} }", new Object[] { packet.getUsername(), packet.getPassword()});
        packet.setPublicKey(this.officialKey);
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
        packet.getProxies().values().forEach(proxy -> {
            proxy.getServers().getUber().setAddress("127.0.0.1");
        });
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
