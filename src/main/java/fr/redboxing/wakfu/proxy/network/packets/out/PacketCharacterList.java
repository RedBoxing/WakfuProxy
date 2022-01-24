package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public class PacketCharacterList extends Packet {
    public PacketCharacterList(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        WakfuProxy.getInstance().getLogger().info(Arrays.toString(packet.array()));
        WakfuProxy.getInstance().getLogger().info(packet.readByte());

        return super.decode();
    }
}
