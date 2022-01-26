package fr.redboxing.wakfu.proxy.network.packets.client;

import fr.redboxing.wakfu.proxy.crypto.RSACertificateManager;
import fr.redboxing.wakfu.proxy.network.packets.AbstractPacketHandler;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.network.packets.PacketBuffer;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.DataUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

public class PacketAuthentication extends Packet {
    private String username;
    private String password;
    private byte[] publicKey;
    private long rsaVerification;

    public PacketAuthentication(String username, String password, byte[] publicKey, long rsaVerification) {
        super(PacketType.CLIENT_MSG, 8);
        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
        this.rsaVerification = rsaVerification;
    }

    public PacketAuthentication() {
        this("", "", null, 0);
    }

    @Override
    public void decode(PacketBuffer packet) {
        try {
            byte[] b = packet.readBytes(packet.readInt());
            byte[] decoded = RSACertificateManager.INSTANCE.decode(b);
            ByteBuf decbuffer = DataUtils.bufferFromBytes(decoded);

            this.rsaVerification = decbuffer.readLong();
            this.username = DataUtils.readString(decbuffer);
            this.password = DataUtils.readString(decbuffer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void encode(PacketBuffer packet) {
        try {
            ByteBuf encbuffer = Unpooled.buffer();

            encbuffer.writeLong(this.rsaVerification);
            DataUtils.writeString(encbuffer, username);
            DataUtils.writeString(encbuffer, password);

            byte[] bytes = encbuffer.array();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(this.publicKey)));

            byte[] encrypted = cipher.doFinal(bytes);

            packet.writeByte(encrypted.length);
            packet.writeBytes(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        packet.finish();
    }

    @Override
    public void handle(AbstractPacketHandler handler) {
        handler.handle(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public long getRsaVerification() {
        return rsaVerification;
    }

    public void setRsaVerification(long rsaVerification) {
        this.rsaVerification = rsaVerification;
    }

    @Override
    public String toString() {
        return "{ username: " + username + ", password: " + password + " }";
    }
}
