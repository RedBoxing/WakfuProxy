package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.models.Proxy;
import fr.redboxing.wakfu.proxy.models.Server;
import fr.redboxing.wakfu.proxy.models.WorldInfo;
import fr.redboxing.wakfu.proxy.models.account.Community;
import fr.redboxing.wakfu.proxy.network.packets.OutPacket;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.SystemConfiguration;
import fr.redboxing.wakfu.proxy.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class PacketProxiesResult extends Packet {
    HashMap<Integer, Proxy> proxies = new HashMap<>();
    HashMap<Integer, WorldInfo> worldInfos = new HashMap<>();

    public PacketProxiesResult(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        try {
            final byte[] bytes = new byte[packet.readableBytes()];
            packet.getBytes(packet.readerIndex(), bytes);
            ByteBuffer bb = ByteBuffer.wrap(bytes);


            int proxySize = bb.getInt();
            for (int i = 0; i < proxySize; ++i) {
                final Proxy proxy = Proxy.fromBuild(bb);
                this.proxies.put(proxy.getId(), proxy);

             //   WakfuProxy.getInstance().getLogger().info(proxy.toString());
            }

            int infoSize = bb.getInt();
            for (int j = 0; j < infoSize; ++j) {
                final WorldInfo info = WorldInfo.fromBuild(bb);
                this.worldInfos.put(info.getServerId(), info);

              //  WakfuProxy.getInstance().getLogger().info(info.toString());
            }

            WakfuProxy.getInstance().getLogger().info(getClass().getSimpleName() + " : " + toString());

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server(6, "Dathura", Community.FR, "127.0.0.1", 8081, 4));

        OutPacket out = new OutPacket(opcode);

        out.writeInt(servers.size());
        for(Server server : servers) {
            out.writeBytes(server.serializeProxy());
        }

        out.writeInt(servers.size());
        for(Server server : servers) {
            out.writeBytes(server.serializeWorld());
        }

        out.finish();
        return out.getData();
    }

    @Override
    public String toString() {
        return "{ proxies: " + proxies.toString() + ", worlds: " + worldInfos.toString() + " }";
    }
}
