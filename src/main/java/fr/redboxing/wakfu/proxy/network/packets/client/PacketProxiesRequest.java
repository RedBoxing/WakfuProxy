package fr.redboxing.wakfu.proxy.network.packets.client;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public class PacketProxiesRequest extends Packet {
    public PacketProxiesRequest() {
        super(PacketType.CLIENT_MSG, 8);
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return "{}";
    }
}
