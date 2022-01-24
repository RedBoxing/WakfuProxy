package fr.redboxing.wakfu.proxy.network.packets;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public class Packet {
    public ByteBuf packet;
    public PacketType packetType;
    public ClientSession session;

    public int size;
    public int type;
    public int opcode;
    public boolean know;

    public Packet(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        this.packet = packet;
        this.packetType = packetType;
        this.session = session;

        this.size = packet.readUnsignedShort();

        if(packetType == PacketType.CLIENT_MSG) {
            this.type = packet.readUnsignedByte();
        }else {
            this.type = -1;
        }

        this.opcode = packet.readUnsignedShort();
        this.know = know;
    }

    public ByteBuf decode() {
        packet.resetReaderIndex();
        return packet;
    }

    @Override
    public String toString() {
        final byte[] bytes = new byte[packet.readableBytes()];
        packet.getBytes(packet.readerIndex(), bytes);
        return "{ hex: " + Utils.toHex(bytes) + " }";
    }

    public enum PacketType {
        CLIENT_MSG, SERVER_MSG
    }
}
