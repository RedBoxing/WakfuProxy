package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.crypto.RSACertificateManager;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class PacketRSA extends Packet {
    public PacketRSA(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        long verificationLong = packet.readLong();
        byte[] key = new byte[packet.readableBytes()];
        packet.readBytes(key);

        session.setLoginRSAKey(key);
        WakfuProxy.getInstance().getLogger().info("Received rsa packet: verification: " + verificationLong + ", key: " + Utils.toHex(session.getLoginRSAKey()));

        ByteBuf out = Unpooled.buffer();
        out.writeShort(0);
        out.writeShort(opcode);

        out.writeLong(verificationLong);
        out.writeBytes(RSACertificateManager.INSTANCE.getPublicKey());

        out.setShort(0, out.writerIndex());

        //return out;
        return super.decode();
    }

    @Override
    public String toString() {
        return "{ key: " + Utils.toHex(session.getLoginRSAKey()) + " }";
    }
}
