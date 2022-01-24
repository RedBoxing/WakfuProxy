package fr.redboxing.wakfu.proxy.models;

import com.google.gson.annotations.SerializedName;
import fr.redboxing.wakfu.proxy.models.account.Community;
import fr.redboxing.wakfu.proxy.utils.ByteArray;
import fr.redboxing.wakfu.proxy.utils.Utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Proxy {
    @SerializedName("id")
    private int m_id;
    @SerializedName("name")
    private String m_name;
    @SerializedName("community")
    private Community m_community;
    @SerializedName("address")
    private String m_address;
    @SerializedName("ports")
    private int[] m_ports;
    private byte m_order;

    public Proxy(int m_id, String m_name, Community m_community, String m_address, int[] m_ports, byte m_order) {
        this.m_id = m_id;
        this.m_name = m_name;
        this.m_community = m_community;
        this.m_address = m_address;
        this.m_ports = m_ports;
        this.m_order = m_order;
    }

    public Proxy() {
        this(0, "Default", Community.DEFAULT_COMMUNITY, "127.0.0.1", new int[] { 5556, 443 }, (byte)0);
    }

    public int getId() {
        return this.m_id;
    }

    public String getName() {
        return this.m_name;
    }

    public Community getCommunity() {
        return this.m_community;
    }

    public String getAddress() {
        return this.m_address;
    }

    public int[] getPorts() {
        return this.m_ports.clone();
    }

    public int getOrder() {
        return this.m_order;
    }

    public void setOrder(final byte order) {
        this.m_order = order;
    }

    public byte[] build() {
        final ByteArray bb = new ByteArray();
        bb.putInt(this.m_id);
        final byte[] utfName = Utils.toUTF8(this.m_name);
        bb.putInt(utfName.length);
        bb.put(utfName);
        bb.putInt(this.m_community.getId());
        final byte[] utfAddress = Utils.toUTF8(this.m_address);
        bb.putInt(utfAddress.length);
        bb.put(utfAddress);
        bb.putInt(this.m_ports.length);
        for (int i = 0, length = this.m_ports.length; i < length; ++i) {
            final int port = this.m_ports[i];
            bb.putInt(port);
        }
        bb.put(this.m_order);
        return bb.toArray();
    }

    public static Proxy fromBuild(final ByteBuffer bb) {
        final int id = bb.getInt();
        final byte[] utfName = new byte[bb.getInt()];
        bb.get(utfName);
        final String name = Utils.fromUTF8(utfName);
        final Community community = Community.getFromId(bb.getInt());
        final byte[] utfAddress = new byte[bb.getInt()];
        bb.get(utfAddress);
        final String address = Utils.fromUTF8(utfAddress);
        final int[] ports = new int[bb.getInt()];
        for (int i = 0, length = ports.length; i < length; ++i) {
            ports[i] = bb.getInt();
        }
        final byte order = bb.get();
        final Proxy proxy = new Proxy();
        proxy.m_id = id;
        proxy.m_name = name;
        proxy.m_community = community;
        proxy.m_address = address;
        proxy.m_ports = ports;
        proxy.m_order = order;
        return proxy;
    }

    @Override
    public String toString() {
        return "Proxy{m_id=" + this.m_id + ", m_name='" + this.m_name + '\'' + ", m_community=" + this.m_community + ", m_address='" + this.m_address + '\'' + ", m_ports=" + Arrays.toString(this.m_ports) + ", m_order=" + this.m_order + '}';
    }
}
