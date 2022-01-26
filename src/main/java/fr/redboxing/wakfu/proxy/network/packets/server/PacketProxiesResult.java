package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.models.Proxy;
import fr.redboxing.wakfu.proxy.models.WorldInfo;
import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class PacketProxiesResult extends Packet {
    HashMap<Integer, Proxy> proxies;
    HashMap<Integer, WorldInfo> worldInfos;

    public PacketProxiesResult(HashMap<Integer, Proxy> proxies, HashMap<Integer, WorldInfo> worldInfos) {
        super(PacketType.SERVER_MSG);
        this.proxies = proxies;
        this.worldInfos = worldInfos;
    }

    public PacketProxiesResult() {
        this(new HashMap<>(), new HashMap<>());
    }

    @Override
    public void decode(PacketBuffer packet) {
        try {
            ByteBuffer bb = packet.toByteBuffer();

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
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeInt(this.proxies.size());
        for(Proxy proxy : this.proxies.values()) {
            packet.writeBytes(proxy.build());
        }

        packet.writeInt(this.worldInfos.size());
        for(WorldInfo worldInfo : this.worldInfos.values()) {
            packet.writeBytes(worldInfo.build());
        }

        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public HashMap<Integer, Proxy> getProxies() {
        return proxies;
    }

    public void setProxies(HashMap<Integer, Proxy> proxies) {
        this.proxies = proxies;
    }

    public HashMap<Integer, WorldInfo> getWorldInfos() {
        return worldInfos;
    }

    public void setWorldInfos(HashMap<Integer, WorldInfo> worldInfos) {
        this.worldInfos = worldInfos;
    }

    @Override
    public String toString() {
        return "{ proxies: " + proxies.toString() + ", worlds: " + worldInfos.toString() + " }";
    }
}
