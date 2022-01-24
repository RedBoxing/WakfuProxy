package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PacketSetIP extends Packet {
    int ip;

    public PacketSetIP(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        try {
            ip = packet.readInt();
            WakfuProxy.getInstance().getLogger().info("Received SetIP Packet: " + InetAddress.getByName(String.valueOf(ip)).getHostAddress());
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return super.decode();
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
