package fr.redboxing.wakfu.proxy.network.packets;

public class Packet {
    private PacketType packetType;
    private int type;
    private PacketBuffer buffer;

    public Packet(PacketType packetType, int type) {
        this.packetType = packetType;
        this.type = type;
    }

    public Packet(PacketType packetType) {
        this(packetType, 0);
    }

    public void decode(PacketBuffer packet) {
        this.buffer = packet;
    }
    public void encode(PacketBuffer packet) {
        packet = this.buffer;
    }
    public void handle(AbstractPacketHandler handler) {}

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public enum PacketType {
        CLIENT_MSG, SERVER_MSG
    }
}
