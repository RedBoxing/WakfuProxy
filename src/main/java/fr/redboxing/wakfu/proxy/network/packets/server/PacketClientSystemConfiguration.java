package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.utils.SystemConfiguration;

public class PacketClientSystemConfiguration extends Packet {
    private SystemConfiguration configuration;

    public PacketClientSystemConfiguration(SystemConfiguration config) {
        super(PacketType.SERVER_MSG);
        this.configuration = config;
    }

    public PacketClientSystemConfiguration() {
        super(PacketType.SERVER_MSG);
        this.configuration = SystemConfiguration.INSTANCE;
    }

    @Override
    public void decode(PacketBuffer packet) {
        byte[] data = packet.readBytes(packet.readInt());
        configuration.unserialize(data);
    }

    @Override
    public void encode(PacketBuffer packet) {
        byte[] data = configuration.serializeForClient();
        packet.writeInt(data.length);
        packet.writeBytes(data);
        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public SystemConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(SystemConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "{ config: " + SystemConfiguration.INSTANCE + " }";
    }
}
