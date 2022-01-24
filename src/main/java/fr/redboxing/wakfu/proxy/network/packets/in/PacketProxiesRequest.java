package fr.redboxing.wakfu.proxy.network.packets.in;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public class PacketProxiesRequest extends Packet { //ClientProxiesRequestMessage
    public PacketProxiesRequest(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        return super.decode();
    }

    @Override
    public String toString() {
        return "{}";
    }
}
