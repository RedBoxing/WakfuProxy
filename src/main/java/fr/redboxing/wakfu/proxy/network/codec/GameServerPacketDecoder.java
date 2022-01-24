package fr.redboxing.wakfu.proxy.network.codec;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Opcodes;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.out.*;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class GameServerPacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final HashMap<Integer, Class<? extends Packet>> INCOMING_PACKET_MAP = new HashMap<>();
    private final ClientSession session;

    public GameServerPacketDecoder(ClientSession session) {
        this.session = session;
        initPacketList();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (session != null && msg.readableBytes() > 0) {
            Packet packet;
            int size = msg.readUnsignedShort();
            int opcode = msg.readUnsignedShort();

            msg.resetReaderIndex();

            if(INCOMING_PACKET_MAP.containsKey(opcode)) {
                packet = INCOMING_PACKET_MAP.get(opcode).getDeclaredConstructor(ByteBuf.class, Packet.PacketType.class, ClientSession.class, boolean.class).newInstance(msg.retain(), Packet.PacketType.SERVER_MSG, session, true);
                WakfuProxy.getInstance().getLogger().info("[SERVER] Incoming packet: { name: " + packet.getClass().getSimpleName() + ", size: " + packet.size + ", opcode: " + packet.opcode + " }");
            }else {
                packet = new Packet(msg.retain(), Packet.PacketType.SERVER_MSG, session, false);
                WakfuProxy.getInstance().getLogger().info("[SERVER] Unknown packet incoming: { size: " + packet.size + ", opcode: " + packet.opcode + " }");
            }

            session.write(packet);
        }
    }

    private static void initPacketList() {
        INCOMING_PACKET_MAP.put(Opcodes.VERSION_RESULT, PacketVersionAck.class);
        INCOMING_PACKET_MAP.put(Opcodes.RSA_RESULT, PacketRSA.class);
        INCOMING_PACKET_MAP.put(Opcodes.SET_IP, PacketSetIP.class);
        INCOMING_PACKET_MAP.put(Opcodes.PROXY_RELAY_ERROR, PacketProxyRelayError.class);
        INCOMING_PACKET_MAP.put(Opcodes.DISPATCH_AUTHENTICATION_RESPONSE, PacketDispatchAuthenticationResult.class);
        INCOMING_PACKET_MAP.put(Opcodes.AUTHENTICATION_RESPONSE, PacketAuthenticationResult.class);
        INCOMING_PACKET_MAP.put(Opcodes.PROXIES_RESPONSE, PacketProxiesResult.class);
        INCOMING_PACKET_MAP.put(Opcodes.AUTHENTICATION_TOKEN_RESPONSE, PacketAuthenticationTokenResult.class);
    }
}
