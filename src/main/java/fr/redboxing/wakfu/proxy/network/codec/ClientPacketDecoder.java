package fr.redboxing.wakfu.proxy.network.codec;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.ClientHandler;
import fr.redboxing.wakfu.proxy.network.packets.Opcodes;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.in.*;
import fr.redboxing.wakfu.proxy.network.packets.out.PacketCharacterList;

import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class ClientPacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final HashMap<Integer, Class<? extends Packet>> INCOMING_PACKET_MAP = new HashMap<>();

    public ClientPacketDecoder() {
        initPacketList();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClientSession sess = ctx.channel().attr(ClientHandler.CLIENTSESS_ATTR).get();
        if (sess != null && msg.readableBytes() > 0) {
            Packet packet;
            int size = msg.readShort();
            int type = msg.readByte();
            int opcode = msg.readShort();

            msg.resetReaderIndex();

            ByteBuf buf = msg.duplicate();
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);

            if(INCOMING_PACKET_MAP.containsKey(opcode)) {
                packet = INCOMING_PACKET_MAP.get(opcode).getDeclaredConstructor(ByteBuf.class, Packet.PacketType.class, ClientSession.class, boolean.class).newInstance(msg.retain(), Packet.PacketType.CLIENT_MSG, sess, true);
                WakfuProxy.getInstance().getLogger().info("[AUTH CLIENT] Incoming packet: { name: " + packet.getClass().getSimpleName() + ", size: " + size + ", type: " + type + ", opcode: " + opcode + " }");
            }else {
                packet = new Packet(msg.retain(), Packet.PacketType.CLIENT_MSG, sess,false);
                WakfuProxy.getInstance().getLogger().info("[AUTH CLIENT] Unknown packet incoming: { size: " + size + ", type: " + type + ", opcode: " + opcode + " }");
            }

            sess.sendToServer(packet);
        }
    }

    private static void initPacketList() {
        INCOMING_PACKET_MAP.put(Opcodes.VERSION, PacketVersion.class);
        INCOMING_PACKET_MAP.put(Opcodes.AUTHENTICATION, PacketAuthentication.class);
        INCOMING_PACKET_MAP.put(Opcodes.REQUEST_RSA, PacketRequestRSA.class);
        INCOMING_PACKET_MAP.put(Opcodes.PROXIES_REQUEST, PacketProxiesRequest.class);
        INCOMING_PACKET_MAP.put(Opcodes.AUTHENTICATION_TOKEN_REQUEST, PacketAuthenticationTokenRequest.class);
        INCOMING_PACKET_MAP.put(Opcodes.AUTHENTICATION_TOKEN_REDEEM, PacketAuthenticationTokenRedeem.class);
        INCOMING_PACKET_MAP.put(Opcodes.LANGUAGE, PacketLanguage.class);
        INCOMING_PACKET_MAP.put(Opcodes.CHARACTERS_LIST, PacketCharacterList.class);
    }
}