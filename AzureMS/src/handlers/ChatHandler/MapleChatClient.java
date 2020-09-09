package handlers.ChatHandler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.common.IoSession;

import connections.Crypto.MapleCrypto;

public class MapleChatClient {

    private transient MapleCrypto send, receive;
    private int SenderCID = 0;
    private int SenderAID = 0;
    private long ReadTime = 0;
    private byte LowDateTime = 0;
    private String RawMsg = null;
    private final IoSession session;
    public static String MCCKEY = "MCC";
    private final transient Lock mutex = new ReentrantLock(true);

    public MapleChatClient(IoSession session, MapleCrypto send, MapleCrypto receive) {
        this.session = session;
        this.send = send;
        this.receive = receive;
    }

    public final Lock getLock() {
        return mutex;
    }

    public void sendPacket(final byte[] data) {
        session.write(data);
    }

    public String getIp() {
        return session.getRemoteAddress().toString().split(":")[0];
    }

    public final MapleCrypto getReceiveCrypto() {
        return receive;
    }

    public final MapleCrypto getSendCrypto() {
        return send;
    }

    public void setSenderCID(int id) {
        this.SenderCID = id;
    }

    public void setSenderAID(int id) {
        this.SenderAID = id;
    }

    public int getSenderCID() {
        return SenderCID;
    }

    public int getSenderAID() {
        return SenderAID;
    }

    public void setReadTime(long i) {
        this.ReadTime = i;
    }

    public long getReadTime() {
        return this.ReadTime;
    }

    public void setLowDateTime(byte i) {
        this.LowDateTime = i;
    }

    public byte getLowDateTime() {
        return this.LowDateTime;
    }

    public void setRawMsg(String t) {
        this.RawMsg = t;
    }

    public String getRawMsg() {
        return this.RawMsg;
    }
}
