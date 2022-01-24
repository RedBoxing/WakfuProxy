package fr.redboxing.wakfu.proxy.network.packets.out;

import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.models.account.AccountInformations;
import fr.redboxing.wakfu.proxy.models.account.LocalAccountInformations;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public class PacketAuthenticationResult extends Packet {
    LoginResponseCode code;
    int banDuration;
    LocalAccountInformations accountInformations;

    public PacketAuthenticationResult(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {
        code = LoginResponseCode.getByID(packet.readByte());

        if(code == LoginResponseCode.ACCOUNT_BANNED) {
            banDuration = packet.readInt();
        }else if(code == LoginResponseCode.CORRECT_LOGIN) {
            int accountInfosSize = packet.readableBytes();//packet.readByte();
            byte[] accountInfosBytes = new byte[accountInfosSize];
            packet.readBytes(accountInfosBytes);

            accountInformations = new LocalAccountInformations();
            try {
                accountInformations.fromBuild(accountInfosBytes);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        WakfuProxy.getInstance().getLogger().info(getClass().getSimpleName() + " : " + toString());

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ code: " + code + (banDuration != 0 ? ", banDuration: " + banDuration : "") + (accountInformations != null ? ", accountInfos: " + accountInformations.toString() : "") + " }";
    }

    public enum LoginResponseCode {
        CORRECT_LOGIN(0),
        INVALID_LOGIN(2),
        ALREADY_CONNECTED(3),
        SAVE_IN_PROGRESS(4),
        ACCOUNT_BANNED(5),
        ACCOUNT_LOCKED(9),
        LOGIN_SERVER_DOWN(10),
        TOO_MANY_CONNECTIONS(11),
        INVALID_PARTNER(12),
        INVALID_EMAIL(20),
        ACCOUNT_UNDER_MODERATION(21),
        CLOSED_BETA(127);

        private int code;

        private LoginResponseCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static LoginResponseCode getByID(int id) {
            for(LoginResponseCode code : values()) {
                if(code.code == id) return code;
            }

            return null;
        }
    }
}
