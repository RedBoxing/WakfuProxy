package fr.redboxing.wakfu.proxy.network.packets.in;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.DataUtils;
import io.netty.buffer.ByteBuf;

public class PacketLanguage extends Packet {
    private String lang;

    public PacketLanguage(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        lang = DataUtils.readLargeString(packet);

        WakfuProxy.getInstance().getLogger().info(getClass().getSimpleName() + " : " + toString());

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ lang: " + lang + " }";
    }
}
