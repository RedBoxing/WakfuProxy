package fr.redboxing.wakfu.proxy.network.packets.in;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.DataUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketVersion extends Packet {
    int major;
    int minor;
    int patch;
    String buildVersion;

    public PacketVersion(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        major = packet.readUnsignedByte();
        minor = packet.readUnsignedShort();
        patch = packet.readUnsignedByte();
        buildVersion = DataUtils.readString(packet);

        WakfuProxy.getInstance().getLogger().info("Received version packet: {build={}.{}.{} ({})}", new Object[]{major, minor, patch, buildVersion});

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ major: " + major + ", minor: " + minor + ", patch: " + patch + ", build: " + buildVersion + " }";
    }
}
