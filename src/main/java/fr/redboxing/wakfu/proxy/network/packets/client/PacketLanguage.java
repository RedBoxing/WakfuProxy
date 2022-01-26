package fr.redboxing.wakfu.proxy.network.packets.client;

import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.DataUtils;
import io.netty.buffer.ByteBuf;

public class PacketLanguage extends Packet {
    private String lang;

    public PacketLanguage(String lang) {
        super(PacketType.CLIENT_MSG);
    }

    public PacketLanguage() {
        this("fr");
    }

    @Override
    public void decode(PacketBuffer packet) {
        this.lang = packet.readLargeString();
    }

    @Override
    public void encode(PacketBuffer packet) {
        packet.writeLargeString(lang);
        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "{ lang: " + lang + " }";
    }
}
