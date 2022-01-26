package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;

public class PacketCharacterList extends Packet {
    public PacketCharacterList() {
        super(PacketType.SERVER_MSG);
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }
}
