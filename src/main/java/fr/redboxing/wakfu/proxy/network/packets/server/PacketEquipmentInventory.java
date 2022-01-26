package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;

public class PacketEquipmentInventory extends Packet {
    public PacketEquipmentInventory() {
        super(PacketType.SERVER_MSG);
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
