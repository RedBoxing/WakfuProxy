package fr.redboxing.wakfu.proxy.network.packets.in;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.crypto.RSACertificateManager;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
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

    public PacketAuthentication(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
       /* byte[] b = new byte[packet.readInt()];
        packet.readBytes(b);

        byte[] decoded = RSACertificateManager.INSTANCE.decode(b);
        ByteBuf decbuffer = DataUtils.bufferFromBytes(decoded);

        long rsaVerification = decbuffer.readLong();
        username = DataUtils.readString(decbuffer);
        password = DataUtils.readString(decbuffer);

        WakfuProxy.getInstance().getLogger().info("Received login packet: { username: {}, password: {} }", new Object[] {username, password});
        //AbsolumentPasUneFonctionPourSaveDansUneDB();

        ByteBuf out = Unpooled.buffer();
        byte[] key = session.getLoginRSAKey();

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key)));

            ByteBuf encbuffer = Unpooled.buffer(9 + username.length() + 1 + password.length());

            encbuffer.writeLong(rsaVerification);
            DataUtils.writeString(encbuffer, username);
            DataUtils.writeString(encbuffer, password);

            byte[] bytes = encbuffer.array();
            byte[] encrypted = cipher.doFinal(bytes);

            out.writeShort(0);
            out.writeByte(type);
            out.writeShort(opcode);

            out.writeByte(encrypted.length);
            out.writeBytes(encrypted);

            out.setShort(0, out.writerIndex());

            //return out;
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ username: " + username + ", password: " + password + " }";
    }
}
