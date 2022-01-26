package fr.redboxing.wakfu.proxy.network.codec;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Protocol;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GameServerPacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (msg.readableBytes() > 0) {
            int size = msg.readShort();
            int opcode = msg.readShort();

            msg.resetReaderIndex();

            WakfuProxy.getInstance().getLogger().info("[SERVER] Received packet with size " + size + ", opcode " + opcode);

            Packet packet = Protocol.getInstance().getPacket(opcode);
            packet.decode(new PacketBuffer(msg, opcode, Packet.PacketType.SERVER_MSG));
            out.add(packet);
        }
    }
}
