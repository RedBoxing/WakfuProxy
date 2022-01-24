package fr.redboxing.wakfu.proxy.network.packets;

import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

public interface IncomingPacket {
    public void decode(ClientSession session, ByteBuf buffer, int size);

    public ByteBuf decode(ByteBuf buffer, int size, int opcode);
}
