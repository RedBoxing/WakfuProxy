package fr.redboxing.wakfu.proxy.network.codec;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    public PacketEncoder() {
        super(Packet.class);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.decode());
        ctx.channel().flush();

        WakfuProxy.getInstance().getForm().addPacketToTable(msg.getClass(), msg);
    }
}