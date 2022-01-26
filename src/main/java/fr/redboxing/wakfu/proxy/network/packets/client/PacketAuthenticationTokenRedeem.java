package fr.redboxing.wakfu.proxy.network.packets.client;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Packet;

public class PacketAuthenticationTokenRedeem extends Packet {
    private String ticket;

    public PacketAuthenticationTokenRedeem(String ticket) {
        super(PacketType.CLIENT_MSG, 1);
        this.ticket = ticket;
    }

    public PacketAuthenticationTokenRedeem() {
        this("");
    }

    @Override
    public void decode(PacketBuffer packet) {
        this.ticket = packet.readBigString();
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeString(this.ticket);
        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "{ ticket: " + ticket + " }";
    }
}
