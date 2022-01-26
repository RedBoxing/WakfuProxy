package fr.redboxing.wakfu.proxy.network.packets;

import fr.redboxing.wakfu.proxy.network.packets.client.*;
import fr.redboxing.wakfu.proxy.network.packets.server.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class AbstractPacketHandler extends SimpleChannelInboundHandler<Packet> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        handlePacket(msg);
    }

    public void handlePacket(Packet packet) {
        packet.handle(this);
    }

    public abstract void handle(PacketVersion packet);
    public abstract void handle(PacketVersionResult packet);
    public abstract void handle(PacketRequestRSA packet);
    public abstract void handle(PacketRSAResult packet);
    public abstract void handle(PacketSetIP packet);
    public abstract void handle(PacketAuthentication packet);
    public abstract void handle(PacketDispatchAuthenticationResult packet);
    public abstract void handle(PacketAuthenticationResult packet);
    public abstract void handle(PacketAuthenticationTokenRequest packet);
    public abstract void handle(PacketAuthenticationTokenResult packet);
    public abstract void handle(PacketAuthenticationTokenRedeem packet);
    public abstract void handle(PacketProxiesRequest packet);
    public abstract void handle(PacketProxiesResult packet);
    public abstract void handle(PacketProxyRelayError packet);
    public abstract void handle(PacketWorldSelectionResult packet);
    public abstract void handle(PacketClientSystemConfiguration packet);
    public abstract void handle(PacketClientCalendarSynchronization packet);
    public abstract void handle(PacketClientAdditionalCharacterSlotsUpdate packet);
    public abstract void handle(PacketFreeCompanionBreedId packet);
    public abstract void handle(PacketEquipmentInventory packet);
    public abstract void handle(PacketCharacterList packet);
    public abstract void handle(PacketLanguage packet);
}
