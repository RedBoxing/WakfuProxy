package fr.redboxing.wakfu.proxy.network.packets;

import io.netty.buffer.ByteBuf;

public interface OutgoingPacket {
    public OutPacket decode(ByteBuf buffer, int size, int opcode);

    public OutPacket encode();
}
