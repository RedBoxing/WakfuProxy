package fr.redboxing.wakfu.proxy.network.packets.in;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public class PacketAuthenticationTokenRedeem extends Packet {
    String ticket;

    public PacketAuthenticationTokenRedeem(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        byte[] data = new byte[packet.readInt()];
        packet.readBytes(data);
        ticket = new String(data);

        WakfuProxy.getInstance().getLogger().info("AuthenticationTokenRedeem: " + toString());

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ ticket: " + ticket + " }";
    }
}
