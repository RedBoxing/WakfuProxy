package fr.redboxing.wakfu.proxy.network.packets;

public class Opcodes {
    public static int VERSION = 37;
    public static int AUTHENTICATION = 503;
    public static int REQUEST_RSA = 514;
    public static int PROXIES_REQUEST = 481;
    public static int AUTHENTICATION_TOKEN_REQUEST = 528;
    public static int AUTHENTICATION_TOKEN_REDEEM = 0;
    public static int LANGUAGE = 0;
    public static int CREATE_CHARACTER = 0;

    public static int VERSION_RESULT = 41;
    public static int RSA_RESULT = 401;
    public static int SET_IP = 367;
    public static int PROXY_RELAY_ERROR = 0;
    public static int DISPATCH_AUTHENTICATION_RESPONSE = 0;
    public static int AUTHENTICATION_RESPONSE = 417;
    public static int PROXIES_RESPONSE = 425;
    public static int AUTHENTICATION_TOKEN_RESPONSE = 445;
    public static int WORLD_SELECTION_RESPONSE = 0;
    public static int SERVER_TIME = 0;
    public static int CHARACTERS_LIST = 0;
    public static int CREATE_CHARACTER_RESPONSE = 0;
}
