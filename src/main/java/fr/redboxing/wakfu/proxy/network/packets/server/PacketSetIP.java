package fr.redboxing.wakfu.proxy.network.packets.server;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;

import java.io.IOException;
import java.net.InetAddress;

public class PacketSetIP extends Packet {
    private int ip;

    public PacketSetIP(int ip) {
        super(PacketType.SERVER_MSG);
        this.ip = ip;
    }

    public PacketSetIP() {
        this(0);
    }

    @Override
    public void decode(PacketBuffer packet) {
        try {
            this.ip = packet.readInt();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeInt(this.ip);
        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        String ipaddress = "0.0.0.0";
        try {
            ipaddress = InetAddress.getByName(String.valueOf(ip)).getHostAddress();
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        return "{ ip: " + ipaddress + " }";
    }
}
