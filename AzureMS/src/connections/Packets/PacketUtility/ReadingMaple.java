package connections.Packets.PacketUtility;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import constants.ServerConstants;
import java.io.UnsupportedEncodingException;

public class ReadingMaple {

    private ByteStream bs;

    public ReadingMaple(ByteStream bs) {
        this.bs = bs;
    }

    public byte readByte() {
        return (byte) bs.readByte();
    }

    public String toString(boolean a) {
        return bs.toString(true);
    }

    public int readInt() {
        int byte1 = bs.readByte();
        int byte2 = bs.readByte();
        int byte3 = bs.readByte();
        int byte4 = bs.readByte();
        return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
    }

    public long readInt2() {
        int byte1 = bs.readByte();
        int byte2 = bs.readByte();
        int byte3 = bs.readByte();
        int byte4 = bs.readByte();
        return (long) ((byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1);
    }

    public short readShort() {
        int byte1 = bs.readByte();
        int byte2 = bs.readByte();
        return (short) ((byte2 << 8) + byte1);
    }

    public char readChar() {
        return (char) readShort();
    }

    public long readLong() {
        long byte1 = bs.readByte();
        long byte2 = bs.readByte();
        long byte3 = bs.readByte();
        long byte4 = bs.readByte();
        long byte5 = bs.readByte();
        long byte6 = bs.readByte();
        long byte7 = bs.readByte();
        long byte8 = bs.readByte();
        return (byte8 << 56) + (byte7 << 48) + (byte6 << 40) + (byte5 << 32) + (byte4 << 24) + (byte3 << 16)
                + (byte2 << 8) + byte1;
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public String readAsciiString(int n) {
        byte ret[] = new byte[n];
        for (int x = 0; x < n; x++) {
            ret[x] = readByte();
        }
        return new String(ret, Charset.forName("MS949"));
    }

    public String readNullTerminatedAsciiString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte b = 1;
        while (b != 0) {
            b = readByte();
            baos.write(b);
        }
        byte[] buf = baos.toByteArray();
        return new String(buf, Charset.forName("MS949"));
    }

    public long getBytesRead() {
        return bs.getBytesRead();
    }

    public String readMapleAsciiString() {
        return readAsciiString(readShort());
    }

    public String newreadMapleAsciiString() {
        return readAsciiString(readByte());
    }

    public final String readMapleAsciiString2() {
        return readAsciiString2(readShort());
    }

    public final String readAsciiString2(final int n) {
        try {
            final byte ret[] = new byte[n];
            for (int x = 0; x < n; x++) {
                ret[x] = readByte();
            }
            return new String(ret, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(ReadingMaple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Point readPos() {
        int x = readShort();
        int y = readShort();
        return new Point(x, y);
    }

    public Point readIntPos() {
        int x = readInt();
        int y = readInt();
        return new Point(x, y);
    }

    public byte[] read(int num) {
        byte[] ret = new byte[num];
        for (int x = 0; x < num; x++) {
            ret[x] = readByte();
        }
        return ret;
    }

    public void skip(int num) {
        for (int x = 0; x < num; x++) {
            readByte();
        }
    }

    public long available() {
        return bs.available();
    }

    @Override
    public String toString() {
        return bs.toString();
    }

    public void seek(long offset) {
        try {
            bs.seek(offset);
        } catch (Exception e) {
            System.err.println("[Error] Skip failed.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public long getPosition() {
        try {
            return bs.getPosition();
        } catch (Exception e) {
            System.err.println("[Error] Failed to get the current position of the packet.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
            return -1;
        }
    }
}
