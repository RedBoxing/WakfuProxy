package fr.redboxing.wakfu.proxy.network.packets.in;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public class PacketAuthenticationTokenRequest extends Packet {
    int server_id;
    long account_id;

    public PacketAuthenticationTokenRequest(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        server_id = packet.readInt();
        account_id = packet.readLong();

        WakfuProxy.getInstance().getLogger().info(getClass().getSimpleName() + " : " + toString());

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ server_id: " + server_id + ", account_id: " + account_id + " }";
    }
}
