package ProjectUI;

import java.io.*;
public class DES {
    static final int[] S = {
            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, // S1
            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13,
            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, // S2
            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9,
            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, // S3
            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12,
            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, // S4
            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14,
            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, // S5
            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3,
            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, // S6
            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13,
            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, // S7
            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12,
            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, // S8
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
    };
    static final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    static final int[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };
    static final int[] P = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };
    static final int[] INVP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    static final int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
    static final int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };
    static final int[] SHIFTS = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    public static DesResults main(String[] args) throws Exception {
        DesResults results = new DesResults();
        if (args.length > 0) {
            String fileName = args[0];
            String input = readFileToString(fileName);
            DES dess = new DES();
            // get the key from the Home class

            String key = Home.desKey;
            byte[] theKey = key.getBytes();
            byte[][] subKeys = getSubkeys(theKey);

            //....encryption calling
            long startTime = System.nanoTime(); //start measuring time
            String encryptedText = dess._cryptAll(input, subKeys, "encrypt");

            String asciiBinary = dess.toAsciiBinary(encryptedText);
            String DNAcode = dess.binaryToDNA(asciiBinary);

            long endTime = System.nanoTime();   // End measuring execution time
            long encryptionTime = (endTime - startTime) / 1000000;   // Calculate execution time in milliseconds
            //.....


            //....decryption calling
            long strtTime = System.nanoTime();
            String decodedAscii = dess.encodeDNA(DNAcode);
            String decodedmessage = dess.convertBinaryToASCII(decodedAscii);

            String unencryptedText = dess._cryptAll(decodedmessage, subKeys, "decrypt");

            long enddTime = System.nanoTime();   // End measuring execution time
            long decryptionTime = (enddTime - strtTime) / 1000000;   // Calculate execution time in milliseconds
            //.....

            results.encryptedTextDes = encryptedText;
            results.DNAEncryptedTextDes = DNAcode;
            results.DNADecryptedTextDes = decodedmessage;
            results.decryptedTextDes = unencryptedText;
            results.encryptionTime = encryptionTime;
            results.decryptionTime = decryptionTime;
            Home.DESMethod(results);
            return results;
        } else {
            System.exit(1);
        }
        return results;
    }
    public static byte[] cipher(byte[] theMsg, byte[][] subKeys, String mode) throws Exception {
        if (theMsg.length < 8)
            throw new Exception("Message is less than 64 bits.");

        theMsg = selectBits(theMsg, IP); // Initial Permutation
        int blockSize = IP.length;
        byte[] l = selectBits(theMsg, 0, blockSize / 2);
        byte[] r = selectBits(theMsg, blockSize / 2, blockSize / 2);
        int numOfSubKeys = subKeys.length;
        for (int k = 0; k < numOfSubKeys; k++) {
            byte[] rBackup = r;
            r = selectBits(r, E); // Expansion
            if (mode.equalsIgnoreCase("encrypt"))
                r = doXORBytes(r, subKeys[k]); // XOR with the sub key
            else
                r = doXORBytes(r, subKeys[numOfSubKeys - k - 1]);
            r = substitution6x4(r); // Substitution
            r = selectBits(r, P); // Permutation
            r = doXORBytes(l, r); // XOR with the previous left half
            l = rBackup; // Taking the previous right half
        }
        byte[] lr = concatenateBits(r, blockSize / 2, l, blockSize / 2);
        lr = selectBits(lr, INVP); // Inverse Permutation
        return lr;
    }

    private static byte[] doXORBytes(byte[] a, byte[] b) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ b[i]);
        }
        return out;
    }

    private static byte[] substitution6x4(byte[] in) {
        in = splitBytes(in, 6); // Splitting byte[] into 6-bit blocks
        byte[] out = new byte[in.length / 2];
        int lhByte = 0;
        for (int b = 0; b < in.length; b++) { // Should be sub-blocks
            byte valByte = in[b];
            int r = 2 * (valByte >> 7 & 0x0001) + (valByte >> 2 & 0x0001); // 1 and 6
            int c = valByte >> 3 & 0x000F; // Middle 4 bits
            int hByte = S[64 * b + 16 * r + c]; // 4 bits (half byte) output
            if (b % 2 == 0) lhByte = hByte; // Left half byte
            else out[b / 2] = (byte) (16 * lhByte + hByte);
        }
        return out;
    }

    private static byte[] splitBytes(byte[] in, int len) {
        int numOfBytes = (8 * in.length - 1) / len + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < numOfBytes; i++) {
            for (int j = 0; j < len; j++) {
                int val = getBit(in, len * i + j);
                setBit(out, 8 * i + j, val);
            }
        }
        return out;
    }
    public static byte[][] getSubkeys(byte[] theKey) {
        int activeKeySize = PC1.length;
        int numOfSubKeys = SHIFTS.length;
        byte[] activeKey = selectBits(theKey, PC1);
        int halfKeySize = activeKeySize / 2;
        byte[] c = selectBits(activeKey, 0, halfKeySize);
        byte[] d = selectBits(activeKey, halfKeySize, halfKeySize);
        byte[][] subKeys = new byte[numOfSubKeys][];
        for (int k = 0; k < numOfSubKeys; k++) {
            c = rotateLeft(c, halfKeySize, SHIFTS[k]);
            d = rotateLeft(d, halfKeySize, SHIFTS[k]);
            byte[] cd = concatenateBits(c, halfKeySize, d, halfKeySize);
            subKeys[k] = selectBits(cd, PC2);
        }
        return subKeys;
    }

    private static byte[] rotateLeft(byte[] in, int len, int step) {
        int numOfBytes = (len - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < len; i++) {
            int val = getBit(in, (i + step) % len);
            setBit(out, i, val);
        }
        return out;
    }

    private static byte[] concatenateBits(byte[] a, int aLen, byte[] b,
                                          int bLen) {
        int numOfBytes = (aLen + bLen - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        int j = 0;
        for (int i = 0; i < aLen; i++) {
            int val = getBit(a, i);
            setBit(out, j, val);
            j++;
        }
        for (int i = 0; i < bLen; i++) {
            int val = getBit(b, i);
            setBit(out, j, val);
            j++;
        }
        return out;
    }

    private static byte[] selectBits(byte[] in, int pos, int len) {
        int numOfBytes = (len - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < len; i++) {
            int val = getBit(in, pos + i);
            setBit(out, i, val);
        }
        return out;
    }

    private static byte[] selectBits(byte[] in, int[] map) {
        int numOfBytes = (map.length - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < map.length; i++) {
            int val = getBit(in, map[i] - 1);
            setBit(out, i, val);
        }
        return out;
    }

    private static int getBit(byte[] data, int pos) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        byte valByte = data[posByte];
        int valInt = valByte >> (8 - (posBit + 1)) & 0x0001;
        return valInt;
    }

    private static void setBit(byte[] data, int pos, int val) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        byte oldByte = data[posByte];
        oldByte = (byte) (((0xFF7F >> posBit) & oldByte) & 0x00FF);
        byte newByte = (byte) ((val << (8 - (posBit + 1))) | oldByte);
        data[posByte] = newByte;
    }


    public static String readFileToString(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Append each line to the string builder
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return contentBuilder.toString();
    }

    //DNA part functions
    public static String toAsciiBinary(String message) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            String binaryString = Integer.toBinaryString((int) c);
            // Pad the binary string with leading zeros to make it 8 bits long
            String paddedBinaryString = String.format("%8s", binaryString).replace(' ', '0');
            result.append(paddedBinaryString);
        }
        return result.toString();
    }

    public static String binaryToDNA(String binaryString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 2) {
            String pair = binaryString.substring(i, i + 2);
            switch (pair) {
                case "00":
                    result.append("A");
                    break;
                case "01":
                    result.append("T");
                    break;
                case "10":
                    result.append("C");
                    break;
                case "11":
                    result.append("G");
                    break;
                default:
                    // Ignore invalid pairs
                    break;
            }
        }
        return result.toString();
    }

    public static String encodeDNA(String dnaSequence) {
        // Replace A with 00, T with 01, C with 10, and G with 11
        dnaSequence = dnaSequence.replaceAll("A", "00");
        dnaSequence = dnaSequence.replaceAll("T", "01");
        dnaSequence = dnaSequence.replaceAll("C", "10");
        dnaSequence = dnaSequence.replaceAll("G", "11");

        return dnaSequence;
    }

    public static String convertBinaryToASCII(String binaryString) {
        // Split the binary string into 8-bit chunks
        int length = binaryString.length();
        if (length % 8 != 0) {

            throw new IllegalArgumentException("Binary string length must be a multiple of 8");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i += 8) {
            String binaryChunk = binaryString.substring(i, i + 8);
            // Convert the binary chunk to its decimal equivalent
            int decimalValue = Integer.parseInt(binaryChunk, 2);
            // Convert the decimal value to its ASCII character
            sb.append((char) decimalValue);
        }
        return sb.toString();
    }

    public static String _cryptAll(String data, byte[][] subKey, String mode) throws Exception {
        DES aes = new DES();
        if (mode == "encrypt") {
            while ((data.length() % 8) != 0) data += " ";
        }
        if (data.length() / 8 > ((int) data.length() / 8)) {
            int rest = data.length() - ((int) data.length() / 8) * 8;
            for (int i = 0; i < rest; i++)
                data += " ";
        }
        int nParts = (int) data.length() / 8;
        byte[] res = new byte[data.length()];
        String partStr;
        byte[] partByte;
        for (int p = 0; p < nParts; p++) {
            partStr = data.substring(p * 8, p * 8 + 8);
            partByte = static_stringToByteArray(partStr);
            if (mode == "encrypt") partByte = aes.cipher(partByte, subKey, mode);
            if (mode == "decrypt") partByte = aes.cipher(partByte, subKey, mode);
            for (int b = 0; b < 8; b++)
                res[p * 8 + b] = partByte[b];
        }
        return static_byteArrayToString(res);
    }

    public static String static_byteArrayToString(byte[] data) {
        String res = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int n = (int) data[i] & 0xFF;
            res += (char) n;
        }
        return res;
    }

    public static byte[] static_stringToByteArray(String s) {
        byte[] temp = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            temp[i] = (byte) s.charAt(i);
        }
        return temp;
    }
    public static class DesResults {
        public String encryptedTextDes;
        public String DNAEncryptedTextDes;
        public String DNADecryptedTextDes;
        public String decryptedTextDes;
        public long encryptionTime;
        public long decryptionTime;
    }

}


