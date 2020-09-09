package connections.Packets.PacketUtility;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import tools.HexTool;

public final class WritingPacket {

    private static final Charset maple = Charset.forName("MS949");
    private ByteArrayOutputStream baos;

    public WritingPacket() {
        this(32);
    }

    public WritingPacket(int size) {
        this.baos = new ByteArrayOutputStream(size);
    }

    public void write0(int i) {
        for (int x = 0; x < i; ++x) {
            baos.write((byte) 0);
        }
    }

    public void write(byte[] b) {
        for (int x = 0; x < b.length; ++x) {
            baos.write(b[x]);
        }
    }

    public void write(byte b) {
        baos.write(b);
    }

    public void write(boolean b) {
        baos.write((byte) (b ? 1 : 0));
    }

    public void write(int b) {
        if (b != -88888) {
            baos.write((byte) b);
        }
    }

    public void writeShort(int i) {
        if (i != -88888) {
            baos.write((byte) (i & 0xFF));
            baos.write((byte) ((i >>> 8) & 0xFF));
        }
    }

    public void writeInt(int i) {
        if (i != -88888) {
            baos.write((byte) (i & 0xFF));
            baos.write((byte) ((i >>> 8) & 0xFF));
            baos.write((byte) ((i >>> 16) & 0xFF));
            baos.write((byte) ((i >>> 24) & 0xFF));
        }
    }

    public void writeInt(long i) {
        baos.write((byte) (i & 0xFF));
        baos.write((byte) ((i >>> 8) & 0xFF));
        baos.write((byte) ((i >>> 16) & 0xFF));
        baos.write((byte) ((i >>> 24) & 0xFF));
    }

    public void writeAsciiString(String s) {
        write(s.getBytes(maple));
    }

    public void writeAsciiString(String s, int max) {
        write(s.getBytes(maple));
        for (int i = s.getBytes(maple).length; i < max; ++i) {
            write(0);
        }
    }

    public void writeMapleAsciiString(String s) {
        writeShort(s.getBytes(maple).length);
        writeAsciiString(s);
    }

    public final void writeMapleAsciiString2(final String s) {
        writeShort((short) s.getBytes(Charset.forName("UTF-8")).length);
        write(s.getBytes(Charset.forName("UTF-8")));
    }

    public void writeNullTerminatedAsciiString(String s) {
        writeAsciiString(s);
        write(0);
    }

    public void writePos(Point s) {
        writeShort(s.x);
        writeShort(s.y);
    }

    public void writeintPos(Point s) {
        writeInt(s.x);
        writeInt(s.y);
    }

    public void writeLong(long l) {
        if (l != -88888) {
            baos.write((byte) (l & 0xFF));
            baos.write((byte) ((l >>> 8) & 0xFF));
            baos.write((byte) ((l >>> 16) & 0xFF));
            baos.write((byte) ((l >>> 24) & 0xFF));
            baos.write((byte) ((l >>> 32) & 0xFF));
            baos.write((byte) ((l >>> 40) & 0xFF));
            baos.write((byte) ((l >>> 48) & 0xFF));
            baos.write((byte) ((l >>> 56) & 0xFF));
        }
    }

    public final void writeReversedLong(long l) {
        this.baos.write((byte) (int) (l >>> 32 & 0xFF));
        this.baos.write((byte) (int) (l >>> 40 & 0xFF));
        this.baos.write((byte) (int) (l >>> 48 & 0xFF));
        this.baos.write((byte) (int) (l >>> 56 & 0xFF));
        this.baos.write((byte) (int) (l & 0xFF));
        this.baos.write((byte) (int) (l >>> 8 & 0xFF));
        this.baos.write((byte) (int) (l >>> 16 & 0xFF));
        this.baos.write((byte) (int) (l >>> 24 & 0xFF));
    }
    
    public final void writeNRect(final Rectangle s) {
        writeInt(s.x);
        writeInt(s.y);
        writeInt(s.width);
        writeInt(s.height);
    }


    public byte[] getPacket() {
        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return HexTool.toString(baos.toByteArray());
    }
}
