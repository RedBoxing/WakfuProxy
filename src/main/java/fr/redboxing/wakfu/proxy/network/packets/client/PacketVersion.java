package fr.redboxing.wakfu.proxy.network.packets.client;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Packet;

public class PacketVersion extends Packet {
    private short major;
    private short minor;
    private short patch;
    private String buildVersion;

    public PacketVersion(short major, short minor, short patch, String buildVersion) {
        super(PacketType.CLIENT_MSG);
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.buildVersion = buildVersion;
    }

    public PacketVersion() {
        this((short)0, (short)0, (short)0, "");
    }

    @Override
    public void decode(PacketBuffer packet) {
        this.major = packet.readUnsignedByte();
        this.minor = packet.readShort();
        this.patch = packet.readUnsignedByte();
        this.buildVersion = packet.readString();
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeShort(major);
        packet.writeShort(minor);
        packet.writeShort(patch);
        packet.writeString(buildVersion);

        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public short getMajor() {
        return major;
    }

    public void setMajor(short major) {
        this.major = major;
    }

    public short getMinor() {
        return minor;
    }

    public void setMinor(short minor) {
        this.minor = minor;
    }

    public short getPatch() {
        return patch;
    }

    public void setPatch(short patch) {
        this.patch = patch;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    @Override
    public String toString() {
        return "{ major: " + major + ", minor: " + minor + ", patch: " + patch + ", build: " + buildVersion + " }";
    }
}
