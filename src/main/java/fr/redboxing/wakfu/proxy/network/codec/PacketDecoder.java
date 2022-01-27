package fr.redboxing.wakfu.proxy.network.codec;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final Packet.PacketType type;

    public PacketDecoder(Packet.PacketType type) {
        this.type = type;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (msg.readableBytes() > 0) {
            int size = msg.readShort();
            int type = 0;
            if(this.type == Packet.PacketType.CLIENT_MSG) {
                type = msg.readByte();
            }
            int opcode = msg.readShort();
            msg.resetReaderIndex();

            Packet packet = Protocol.getInstance().getPacket(opcode);
            WakfuProxy.getInstance().getLogger().info((this.type == Packet.PacketType.CLIENT_MSG ? "[CLIENT]" : "[SERVER]") +" Received " + packet.getClass().getSimpleName() + " { size: " + size + ", type: " + type + ", opcode: " + opcode + " }");
            packet.decode(new PacketBuffer(msg, opcode, this.type, type));
            out.add(packet);
        }
    }
}
