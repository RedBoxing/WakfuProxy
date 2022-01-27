package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.crypto.RSACertificateManager;
import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;


public class PacketRSAResult extends Packet {
    private long verification;
    private byte[] key;

    public PacketRSAResult(long verification, byte[] key) {
        super(PacketType.SERVER_MSG);
        this.verification = verification;
        this.key = key;
    }

    public PacketRSAResult() {
        this(0, null);
    }

    @Override
    public void decode(PacketBuffer packet) {
        this.verification = packet.readLong();
        this.key =  packet.readBytes(packet.readableBytes());
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeLong(this.verification);
        packet.writeBytes(this.key);
        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public long getVerification() {
        return verification;
    }

    public void setVerification(long verification) {
        this.verification = verification;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "{ key: " + Utils.toHex(this.key) + " }";
    }
}
