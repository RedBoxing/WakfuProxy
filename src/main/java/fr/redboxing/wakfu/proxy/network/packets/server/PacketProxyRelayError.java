package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public class PacketProxyRelayError extends Packet {
    private byte reason;
    private byte serverGroup;

    public PacketProxyRelayError(byte reason, byte serverGroup) {
        super(PacketType.SERVER_MSG);
        this.reason = reason;
        this.serverGroup = serverGroup;
    }

    public PacketProxyRelayError() {
        this((byte) 0, (byte) 0);
    }

    @Override
    public void decode(PacketBuffer packet) {
        this.reason = packet.readByte();
        this.serverGroup = packet.readByte();
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeByte(this.reason);
        packet.writeByte(this.serverGroup);
        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public byte getReason() {
        return reason;
    }

    public void setReason(byte reason) {
        this.reason = reason;
    }

    public byte getServerGroup() {
        return serverGroup;
    }

    public void setServerGroup(byte serverGroup) {
        this.serverGroup = serverGroup;
    }

    @Override
    public String toString() {
        return "{ reason: " + reason + ", serverGroup: " + serverGroup + " }";
    }
}
