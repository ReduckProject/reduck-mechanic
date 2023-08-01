package net.reduck.mechanic;

/**
 * @author Gin
 * @since 2023/8/1 10:47
 */
public class BitsUtils {

    public static int readUnsignedShort(byte[] data , final int offset) {
        return ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
    }

    public static int readUnsignedShort(byte[] data) {
        return readUnsignedShort(data, 0);
    }

    public static int readInt(byte[] data,  final int offset) {
        return ((data[offset] & 0xFF) << 24)
                | ((data[offset + 1] & 0xFF) << 16)
                | ((data[offset + 2] & 0xFF) << 8)
                | (data[offset + 3] & 0xFF);
    }

    public static int readInt(byte[] data) {
        return readInt(data, 0);
    }
}
