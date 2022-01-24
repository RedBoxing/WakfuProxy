package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public class PacketProxyRelayError extends Packet {
    byte reason;
    byte serverGroup;

    public PacketProxyRelayError(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        reason = packet.readByte();
        serverGroup = packet.readByte();

        WakfuProxy.getInstance().getLogger().info("Proxy Relay Error: { serverGroup: " + serverGroup + ", reason: " + reason + " }");

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ reason: " + reason + ", serverGroup: " + serverGroup + " }";
    }
}
