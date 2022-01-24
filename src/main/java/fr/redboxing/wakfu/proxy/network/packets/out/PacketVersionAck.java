package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.crypto.RSACertificateManager;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.DataUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketVersionAck extends Packet {
    boolean accepted;
    int major;
    int minor;
    int patch;
    String buildVersion;

    public PacketVersionAck(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        accepted = packet.readUnsignedByte() == 1;
        major = packet.readUnsignedByte();
        minor = packet.readUnsignedShort();
        patch = packet.readUnsignedByte();
        buildVersion = DataUtils.readString(packet);

        WakfuProxy.getInstance().getLogger().info("Received version result packet: { build={}.{}.{} ({}), accepted={} }", new Object[] {major, minor, patch, buildVersion, accepted});

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ accepted: " + accepted + ", major: " + major + ", minor: " + minor + ", patch: " + patch + ", build: " + buildVersion + " }";
    }
}
