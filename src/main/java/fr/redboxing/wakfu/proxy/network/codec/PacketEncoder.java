package fr.redboxing.wakfu.proxy.network.codec;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Protocol;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    public PacketEncoder() {
        super(Packet.class);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        msg.encode(new PacketBuffer(out, Protocol.getInstance().getPacketId(msg), msg.getPacketType(), msg.getType()));

        byte[] b = new byte[out.readableBytes()];
        out.readBytes(b);
        System.out.println(Utils.toHex(b));

        ctx.channel().flush();

        WakfuProxy.getInstance().getForm().addPacketToTable(msg.getClass(), msg);
    }
}