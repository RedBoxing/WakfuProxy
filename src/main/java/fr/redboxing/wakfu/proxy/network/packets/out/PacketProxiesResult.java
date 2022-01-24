package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.ProxyServer;
import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.models.Proxy;
import fr.redboxing.wakfu.proxy.models.ProxyServers;
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
import java.util.Arrays;
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
            }

            int infoSize = bb.getInt();
            for (int j = 0; j < infoSize; ++j) {
                final WorldInfo info = WorldInfo.fromBuild(bb);
                this.worldInfos.put(info.getServerId(), info);
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<Proxy> proxies = new ArrayList<>();
        ArrayList<WorldInfo> worlds = new ArrayList<>();

        proxies.add(new Proxy(6, "Dathura", Community.FR, new ProxyServers(new ProxyServer("127.0.0.1", 8081)), (byte) 0));
        worlds.add(new WorldInfo(6, new SystemConfiguration(6, false, false, 0, "", "", "default;steam"), Utils.versionToBytes((byte)1, (short)74, (byte)3)));

        OutPacket out = new OutPacket(opcode);

        out.writeInt(proxies.size());
        for(Proxy server : proxies) {
            out.writeBytes(server.build());
        }

        out.writeInt(worlds.size());
        for(WorldInfo worldInfo : worlds) {
            out.writeBytes(worldInfo.build());
        }

        out.finish();
        return out.getData();
    }

    @Override
    public String toString() {
        return "{ proxies: " + proxies.toString() + ", worlds: " + worldInfos.toString() + " }";
    }
}
