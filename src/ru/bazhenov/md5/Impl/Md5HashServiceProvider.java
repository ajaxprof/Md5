package ru.bazhenov.md5.Impl;

import ru.bazhenov.md5.IHashServiceProvider;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


public final class Md5HashServiceProvider implements IHashServiceProvider {

    private final List<Function<Integer, Integer>> operations;

    private final static int A = 0x67452301;
    private final static int B = 0xEFCDAB89;
    private final static int C = 0x98BADCFE;
    private final static int D = 0x10325476;

    public Md5HashServiceProvider(Supplier<Md5Mode> mode) {
        operations = mode.get().getOperations();
    }

    public String HexDigest(byte[] data) {
        String digest = null;
        try {
            digest = digest(data, Integer::toHexString);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return digest;
    }

    public String BinaryDigest(byte[] data) {
        String digest = null;
        try {
            digest = digest(data, Integer::toBinaryString);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return digest;
    }

    private String digest(byte[] data, java.util.function.Function<Integer, String> transform) throws IOException {

        Integer AA = A;
        Integer BB = B;
        Integer CC = C;
        Integer DD = D;

        try (ReadableByteChannel stream = Channels.newChannel(new ByteArrayInputStream(data))) {

            ByteBuffer buffer = ByteBuffer
                    .allocate(64)
                    .order(ByteOrder.LITTLE_ENDIAN);

            int[] x = new int[16];
            int index = 0;
            boolean endOfStream = false;
            List<Byte> appendix = appendixOf(data);

            while (!endOfStream) {
                Integer a = AA, b = BB, c = CC, d = DD;
                buffer.rewind();
                if (stream.read(buffer) < 64) {
                    int position = buffer.position();
                    appendix.stream()
                            .skip(index)
                            .limit(64 - position)
                            .forEach(buffer::put);
                    index += 64 - position;
                    endOfStream = appendix.size() == index;
                }

                buffer.rewind();
                buffer.asIntBuffer().get(x);

                //region Round#1

                a = b + Integer.rotateLeft(a + operations.get(0).apply(b, c, d) + x[0] + 0xd76aa478, 7);
                d = a + Integer.rotateLeft(d + operations.get(0).apply(a, b, c) + x[1] + 0xe8c7b756, 12);
                c = d + Integer.rotateLeft(c + operations.get(0).apply(d, a, b) + x[2] + 0x242070db, 17);
                b = c + Integer.rotateLeft(b + operations.get(0).apply(c, d, a) + x[3] + 0xc1bdceee, 22);

                a = b + Integer.rotateLeft(a + operations.get(0).apply(b, c, d) + x[4] + 0xf57c0faf, 7);
                d = a + Integer.rotateLeft(d + operations.get(0).apply(a, b, c) + x[5] + 0x4787c62a, 12);
                c = d + Integer.rotateLeft(c + operations.get(0).apply(d, a, b) + x[6] + 0xa8304613, 17);
                b = c + Integer.rotateLeft(b + operations.get(0).apply(c, d, a) + x[7] + 0xfd469501, 22);

                a = b + Integer.rotateLeft(a + operations.get(0).apply(b, c, d) + x[8] + 0x698098d8, 7);
                d = a + Integer.rotateLeft(d + operations.get(0).apply(a, b, c) + x[9] + 0x8b44f7af, 12);
                c = d + Integer.rotateLeft(c + operations.get(0).apply(d, a, b) + x[10] + 0xffff5bb1, 17);
                b = c + Integer.rotateLeft(b + operations.get(0).apply(c, d, a) + x[11] + 0x895cd7be, 22);

                a = b + Integer.rotateLeft(a + operations.get(0).apply(b, c, d) + x[12] + 0x6b901122, 7);
                d = a + Integer.rotateLeft(d + operations.get(0).apply(a, b, c) + x[13] + 0xfd987193, 12);
                c = d + Integer.rotateLeft(c + operations.get(0).apply(d, a, b) + x[14] + 0xa679438e, 17);
                b = c + Integer.rotateLeft(b + operations.get(0).apply(c, d, a) + x[15] + 0x49b40821, 22);

                //endregion

                //region Round#2

                a = b + Integer.rotateLeft(a + operations.get(1).apply(b, c, d) + x[1] + 0xf61e2562, 5);
                d = a + Integer.rotateLeft(d + operations.get(1).apply(a, b, c) + x[6] + 0xc040b340, 9);
                c = d + Integer.rotateLeft(c + operations.get(1).apply(d, a, b) + x[11] + 0x265e5a51, 14);
                b = c + Integer.rotateLeft(b + operations.get(1).apply(c, d, a) + x[0] + 0xe9b6c7aa, 20);

                a = b + Integer.rotateLeft(a + operations.get(1).apply(b, c, d) + x[5] + 0xd62f105d, 5);
                d = a + Integer.rotateLeft(d + operations.get(1).apply(a, b, c) + x[10] + 0x02441453, 9);
                c = d + Integer.rotateLeft(c + operations.get(1).apply(d, a, b) + x[15] + 0xd8a1e681, 14);
                b = c + Integer.rotateLeft(b + operations.get(1).apply(c, d, a) + x[4] + 0xe7d3fbc8, 20);

                a = b + Integer.rotateLeft(a + operations.get(1).apply(b, c, d) + x[9] + 0x21e1cde6, 5);
                d = a + Integer.rotateLeft(d + operations.get(1).apply(a, b, c) + x[14] + 0xc33707d6, 9);
                c = d + Integer.rotateLeft(c + operations.get(1).apply(d, a, b) + x[3] + 0xf4d50d87, 14);
                b = c + Integer.rotateLeft(b + operations.get(1).apply(c, d, a) + x[8] + 0x455a14ed, 20);

                a = b + Integer.rotateLeft(a + operations.get(1).apply(b, c, d) + x[13] + 0xa9e3e905, 5);
                d = a + Integer.rotateLeft(d + operations.get(1).apply(a, b, c) + x[2] + 0xfcefa3f8, 9);
                c = d + Integer.rotateLeft(c + operations.get(1).apply(d, a, b) + x[7] + 0x676f02d9, 14);
                b = c + Integer.rotateLeft(b + operations.get(1).apply(c, d, a) + x[12] + 0x8d2a4c8a, 20);

                //endregion

                //region Round#3

                a = b + Integer.rotateLeft(a + operations.get(2).apply(b, c, d) + x[5] + 0xfffa3942, 4);
                d = a + Integer.rotateLeft(d + operations.get(2).apply(a, b, c) + x[8] + 0x8771f681, 11);
                c = d + Integer.rotateLeft(c + operations.get(2).apply(d, a, b) + x[11] + 0x6d9d6122, 16);
                b = c + Integer.rotateLeft(b + operations.get(2).apply(c, d, a) + x[14] + 0xfde5380c, 23);

                a = b + Integer.rotateLeft(a + operations.get(2).apply(b, c, d) + x[1] + 0xa4beea44, 4);
                d = a + Integer.rotateLeft(d + operations.get(2).apply(a, b, c) + x[4] + 0x4bdecfa9, 11);
                c = d + Integer.rotateLeft(c + operations.get(2).apply(d, a, b) + x[7] + 0xf6bb4b60, 16);
                b = c + Integer.rotateLeft(b + operations.get(2).apply(c, d, a) + x[10] + 0xbebfbc70, 23);

                a = b + Integer.rotateLeft(a + operations.get(2).apply(b, c, d) + x[13] + 0x289b7ec6, 4);
                d = a + Integer.rotateLeft(d + operations.get(2).apply(a, b, c) + x[0] + 0xeaa127fa, 11);
                c = d + Integer.rotateLeft(c + operations.get(2).apply(d, a, b) + x[3] + 0xd4ef3085, 16);
                b = c + Integer.rotateLeft(b + operations.get(2).apply(c, d, a) + x[6] + 0x4881d05, 23);

                a = b + Integer.rotateLeft(a + operations.get(2).apply(b, c, d) + x[9] + 0xd9d4d039, 4);
                d = a + Integer.rotateLeft(d + operations.get(2).apply(a, b, c) + x[12] + 0xe6db99e5, 11);
                c = d + Integer.rotateLeft(c + operations.get(2).apply(d, a, b) + x[15] + 0x1fa27cf8, 16);
                b = c + Integer.rotateLeft(b + operations.get(2).apply(c, d, a) + x[2] + 0xc4ac5665, 23);

                //endregion

                //region Round#4

                a = b + Integer.rotateLeft(a + operations.get(3).apply(b, c, d) + x[0] + 0xf4292244, 6);
                d = a + Integer.rotateLeft(d + operations.get(3).apply(a, b, c) + x[7] + 0x432aff97, 10);
                c = d + Integer.rotateLeft(c + operations.get(3).apply(d, a, b) + x[14] + 0xab9423a7, 15);
                b = c + Integer.rotateLeft(b + operations.get(3).apply(c, d, a) + x[5] + 0xfc93a039, 21);

                a = b + Integer.rotateLeft(a + operations.get(3).apply(b, c, d) + x[12] + 0x655b59c3, 6);
                d = a + Integer.rotateLeft(d + operations.get(3).apply(a, b, c) + x[3] + 0x8f0ccc92, 10);
                c = d + Integer.rotateLeft(c + operations.get(3).apply(d, a, b) + x[10] + 0xffeff47d, 15);
                b = c + Integer.rotateLeft(b + operations.get(3).apply(c, d, a) + x[1] + 0x85845dd1, 21);

                a = b + Integer.rotateLeft(a + operations.get(3).apply(b, c, d) + x[8] + 0x6fa87e4f, 6);
                d = a + Integer.rotateLeft(d + operations.get(3).apply(a, b, c) + x[15] + 0xfe2ce6e0, 10);
                c = d + Integer.rotateLeft(c + operations.get(3).apply(d, a, b) + x[6] + 0xa3014314, 15);
                b = c + Integer.rotateLeft(b + operations.get(3).apply(c, d, a) + x[13] + 0x4e0811a1, 21);

                a = b + Integer.rotateLeft(a + operations.get(3).apply(b, c, d) + x[4] + 0xf7537e82, 6);
                d = a + Integer.rotateLeft(d + operations.get(3).apply(a, b, c) + x[11] + 0xbd3af235, 10);
                c = d + Integer.rotateLeft(c + operations.get(3).apply(d, a, b) + x[2] + 0x2ad7d2bb, 15);
                b = c + Integer.rotateLeft(b + operations.get(3).apply(c, d, a) + x[9] + 0xeb86d391, 21);

                //endregion

                AA += a;
                BB += b;
                CC += c;
                DD += d;
            }
        }

        AA = Integer.reverseBytes(AA);
        BB = Integer.reverseBytes(BB);
        CC = Integer.reverseBytes(CC);
        DD = Integer.reverseBytes(DD);

        //return String.format("%s%s%s%s", transform.apply(AA), transform.apply(BB), transform.apply(CC), transform.apply(DD));
        byte[] AAbytes = ByteBuffer.allocate(4).putInt(AA).array();
        byte[] BBbytes = ByteBuffer.allocate(4).putInt(BB).array();
        byte[] CCbytes = ByteBuffer.allocate(4).putInt(CC).array();
        byte[] DDbytes = ByteBuffer.allocate(4).putInt(DD).array();
        String res = "";
        for (int i = 0; i < 4; i++) {
            res += String.format("%8s", Integer.toBinaryString(AAbytes[i] & 0xFF)).replace(' ', '0');
        }
        for (int i = 0; i < 4; i++) {
            res += String.format("%8s", Integer.toBinaryString(BBbytes[i] & 0xFF)).replace(' ', '0');
        }
        for (int i = 0; i < 4; i++) {
            res += String.format("%8s", Integer.toBinaryString(CCbytes[i] & 0xFF)).replace(' ', '0');
        }
        for (int i = 0; i < 4; i++) {
            res += String.format("%8s", Integer.toBinaryString(DDbytes[i] & 0xFF)).replace(' ', '0');
        }
        return res;
    }

    private static List<Byte> appendixOf(byte[] data) {
        long dataBitsCount = data.length << 3;
        List<Byte> appendix = new LinkedList<>();
        appendix.add((byte) 0x80);
        long additions = Math.abs(448 - ((dataBitsCount + 16) % 512)) / 8;
        LongStream.range(0, additions + 1).forEach(i -> appendix.add((byte) 0x00));
        appendBytes(appendix, dataBitsCount);
        return appendix;
    }

    private static void appendBytes(List<Byte> destination, Long source) {
        ByteBuffer buffer = ByteBuffer
                .allocate(Long.BYTES)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putLong(source);
        buffer.rewind();
        IntStream.range(0, Long.BYTES)
                .forEach(i -> destination.add(buffer.get()));
    }

}
