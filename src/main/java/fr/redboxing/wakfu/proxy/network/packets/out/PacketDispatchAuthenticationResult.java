package fr.redboxing.wakfu.proxy.network.packets.out;

import com.google.common.base.Optional;
import fr.redboxing.wakfu.proxy.WakfuProxy;
import fr.redboxing.wakfu.proxy.models.account.*;
import fr.redboxing.wakfu.proxy.models.account.admin.*;
import fr.redboxing.wakfu.proxy.network.packets.Packet;
import fr.redboxing.wakfu.proxy.session.ClientSession;
import fr.redboxing.wakfu.proxy.utils.AdminUtils;
import fr.redboxing.wakfu.proxy.utils.DataUtils;
import io.netty.buffer.ByteBuf;

public class PacketDispatchAuthenticationResult extends Packet {
    PacketAuthenticationResult.LoginResponseCode code;
    AccountInformation accountInformation;

    public PacketDispatchAuthenticationResult(ByteBuf packet, PacketType packetType, ClientSession session, boolean know) {
        super(packet, packetType, session, know);
    }

    @Override
    public ByteBuf decode() {

        //LOGIN RESPONSE
        code = PacketAuthenticationResult.LoginResponseCode.getByID(packet.readByte());
        boolean success = packet.readBoolean();

        if(success) {
            try {
                accountInformation = AccountInformation.unSerialize(packet);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        WakfuProxy.getInstance().getLogger().info(getClass().getSimpleName() + " : " + toString());

        return super.decode();
    }

    @Override
    public String toString() {
        return "{ code: " + code + ", accountInformations: " + accountInformation.toString() + " }";
    }

    private static class AccountInformation
    {
        private final long m_account_id;
        private final Community m_community;
        private final Optional<Admin> m_adminInformation;
        private final String m_accountNickName;

        AccountInformation(final long account_id, final Community community, final Optional<Admin> adminInformation, String accountNickName) {
            super();
            this.m_account_id = account_id;
            this.m_community = community;
            this.m_adminInformation = adminInformation;
            this.m_accountNickName = accountNickName;
        }

        public long get_account_id() {
            return this.m_account_id;
        }

        public Community getCommunity() {
            return this.m_community;
        }

        public Optional<Admin> getAdminInformation() {
            return this.m_adminInformation;
        }

        public String get_accountNickName() {
            return this.m_accountNickName;
        }

        public static AccountInformation unSerialize(final ByteBuf buf) {
            final long id = buf.readLong();
            final Community community = Community.getFromId(buf.readInt());
            final String nickname = DataUtils.readBigString(buf);
            Optional<Admin> admin;
            if (buf.readBoolean()) {
                final byte[] sAdmin = new byte[buf.readInt()];
                buf.readBytes(sAdmin);
                admin = Optional.of(AdminUtils.unSerialize(sAdmin));
            }
            else {
                admin = Optional.absent();
            }
            return new AccountInformation(id, community, admin, nickname);
        }

        @Override
        public String toString() {
            return "AccountInformation{m_account_id=" + this.m_account_id + ", m_community=" + this.m_community + ", m_adminInformation=" + this.m_adminInformation + ", m_accountNickName=" + this.m_accountNickName + '}';
        }
    }
}
