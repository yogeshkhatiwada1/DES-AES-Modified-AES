package ProjectUI;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class AESmodified {
    static final byte[] ESBox = {
            99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118,
            -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64,
            -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21,
            4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117,
            9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124,
            83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49,
            -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88,
            81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46,
            -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115,
            96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37,
            -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121,
            -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8,
            -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118,
            112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98,
            -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33,
            -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22 };
    static final byte[] rcon = {
            0,
            1, 2, 4, 8, 16, 32,
            64, -128, 27, 54, 108, -40,
            -85, 77, -102, 47, 94, -68,
            99, -58, -105, 53, 106, -44,
            -77, 125, -6, -17, -59, -111 };
    static int[] shift = new int[4];
    int numRounds, traceLevel = 0;
    String traceInfo = "";
    static final int ROUNDS = 14, BLOCK_SIZE = 16;
    byte[][] Ke;
    byte[][] Kd;

    static String sessionKey;
    static final int
            COL_SIZE = 4,
            NUM_COLS = BLOCK_SIZE / COL_SIZE,
            ROOT = 0x11B;
    static final int[] alog = new int[256];
    static final int[] log =  new int[256];
    static { // for multiplying in the field GF(2^8)
        int i, j;
        alog[0] = 1;
        for (i = 1; i < 256; i++) {
            j = (alog[i-1] << 1) ^ alog[i-1];
            if ((j & 0x100) != 0) j ^= ROOT;
            alog[i] = j;
        }
        for (i = 1; i < 255; i++) log[alog[i]] = i;
    }
    public static class ModifiedAesResults {
        public String encryptedTextMAes;
        public String DNAEncryptedTextMAes;
        public String DNADecryptedTextMAes;
        public String decryptedTextMAes;
        public long encryptionTime;
        public long decryptionTime;
    }

    public static ModifiedAesResults main (String[] args) {
        ModifiedAesResults results= new ModifiedAesResults();
         if(args.length>0) {
            String filePath = args[0];
            String fileName = filePath;
            String lines = readFileToString(fileName);
            AESmodified aes = new AESmodified();
            String key = Home.mAesKey;
            sessionKey = key;
            //shift=generateShiftNumbers(key);
            String data = lines;

            aes.setKey(static_stringToByteArray(key));

            //....encryption calling
            long startTime = System.nanoTime();
            String encryptedText = aes.Encrypt(data);
            String asciiBinary = aes.toAsciiBinary(encryptedText);
            String DNAcode = aes.binaryToDNA(asciiBinary);
            long endTime = System.nanoTime();   // End measuring execution time
            long encryptionTime = (endTime - startTime) / 1000000;   // Calculate execution time in milliseconds
            //.....

            //....decryption calling
            long strtTime = System.nanoTime();
            String decodedAscii = aes.encodeDNA(DNAcode);
            String decodedmessage = aes.convertBinaryToASCII(decodedAscii);
            String unencryptedText = aes.Decrypt(decodedmessage);
            long enddTime = System.nanoTime();   // End measuring execution time
            long decryptionTime = (enddTime - strtTime) / 1000000;   // Calculate execution time in milliseconds
            //.....

            results.encryptedTextMAes=encryptedText;
            results.DNAEncryptedTextMAes=DNAcode;
            results.DNADecryptedTextMAes=decodedmessage;
            results.decryptedTextMAes=unencryptedText;
            results.encryptionTime=encryptionTime;
            results.decryptionTime=decryptionTime;
           Home.MAESMethod(results);
        }
        else{
            System.exit(1);
        }
        return results;
    }

    public static int getRounds (int keySize) {
        switch (keySize) {
            case 16:
                return 10;
            case 24:
                return 12;
            default:
                return 14;
        }
    }

    static final int mul (int a, int b) {
        // Multiply two elements of GF(2^8).
        return (a != 0 && b != 0) ?alog[(log[a & 0xFF] + log[b & 0xFF]) % 255] : 0;
    }

    public byte[] encrypt(byte[] plain) {
        byte [] a = new byte[BLOCK_SIZE];    // state variable
        byte [] ta = new byte[BLOCK_SIZE];    // temp state variable
        byte [] Ker;
        int    i, k, row, col;

        traceInfo = "";
        if (traceLevel > 0) traceInfo = "encryptAES(" + Util.toHEX1(plain) + ")";

        // verification
        if (plain == null)
            throw new IllegalArgumentException("Empty plaintext");
        if (plain.length != BLOCK_SIZE)
            throw new IllegalArgumentException("Incorrect plaintext length");

        // copy plaintext bytes into state
        Ker = Ke[0];
        for (i = 0; i < BLOCK_SIZE; i++)    a[i] = (byte)(plain[i] ^ Ker[i]);
        if (traceLevel > 2)
            traceInfo += "n  R0 (Key = "+ Util.toHEX1(Ker)+")ntAK = "+ Util.toHEX1(a);
        else if (traceLevel > 1)
            traceInfo += "n  R0 (Key = "+ Util.toHEX1(Ker)+")t = "+ Util.toHEX1(a);


        for (int r = 1; r < numRounds; r++) {
            Ker = Ke[r];            // get session keys for this round
            if (traceLevel > 1)    traceInfo += "n  R"+r+" (Key = "+ Util.toHEX1(Ker)+")t";

            // SubBytes(state)
            byte[] generatedSBox=generateSBox();
            for (i = 0; i < BLOCK_SIZE; i++) ta[i] = generatedSBox[a[i] & 0xFF];
            if (traceLevel > 2)    traceInfo += "ntSB = "+ Util.toHEX1(ta);

            // ShiftRows(state)
            shift=generateShiftNumbers();
            for (i = 0; i < BLOCK_SIZE; i++) {
                row = i % COL_SIZE;
                k = (i + (shift[row] * COL_SIZE)) % BLOCK_SIZE;    // get shifted byte index
                a[i] = ta[k];
            }
            if (traceLevel > 2)    traceInfo += "ntSR = "+ Util.toHEX1(a);

            // MixColumns(state)
            for (col = 0; col < NUM_COLS; col++) {
                i = col * COL_SIZE;
                ta[i]   = (byte)(mul(2,a[i]) ^ mul(3,a[i+1]) ^ a[i+2] ^ a[i+3]);
                ta[i+1] = (byte)(a[i] ^ mul(2,a[i+1]) ^ mul(3,a[i+2]) ^ a[i+3]);
                ta[i+2] = (byte)(a[i] ^ a[i+1] ^ mul(2,a[i+2]) ^ mul(3,a[i+3]));
                ta[i+3] = (byte)(mul(3,a[i]) ^ a[i+1] ^ a[i+2] ^ mul(2,a[i+3]));
            }
            if (traceLevel > 2)    traceInfo += "ntMC = "+ Util.toHEX1(ta);

            // AddRoundKey(state)
            for (i = 0; i < BLOCK_SIZE; i++)    a[i] = (byte)(ta[i] ^ Ker[i]);
            if (traceLevel > 2)    traceInfo += "ntAK";
            if (traceLevel > 1)    traceInfo += " = "+ Util.toHEX1(a);
        }

        Ker = Ke[numRounds];            // get session keys
        if (traceLevel > 1)    traceInfo += "n  R"+numRounds+" (Key = "+ Util.toHEX1(Ker)+")t";

        // SubBytes(state)
        byte[] lastSBox=generateSBox();
        for (i = 0; i < BLOCK_SIZE; i++) a[i] = lastSBox[a[i] & 0xFF];
        if (traceLevel > 2)    traceInfo += "ntSB = "+ Util.toHEX1(a);

        // ShiftRows(state)
        shift=generateShiftNumbers();
        for (i = 0; i < BLOCK_SIZE; i++) {
            row = i % COL_SIZE;
            k = (i + (shift[row] * COL_SIZE)) % BLOCK_SIZE;    // get shifted byte index
            ta[i] = a[k];
        }
        if (traceLevel > 2)    traceInfo += "ntSR = "+ Util.toHEX1(a);

        // AddRoundKey(state)
        for (i = 0; i < BLOCK_SIZE; i++)    a[i] = (byte)(ta[i] ^ Ker[i]);
        if (traceLevel > 2)    traceInfo += "ntAK";
        if (traceLevel > 1)    traceInfo += " = "+ Util.toHEX1(a)+"n";
        if (traceLevel > 0)    traceInfo += " = "+ Util.toHEX1(a)+"n";

        byte[] addSBox=generateAddiSBox();
        for (i = 0; i < BLOCK_SIZE; i++) a[i] = addSBox[a[i] & 0xFF];

        return (a);
    }

    public byte[] decrypt(byte[] cipher) {
        byte [] a = new byte[BLOCK_SIZE];    // AES state variable
        byte [] ta = new byte[BLOCK_SIZE];    // AES temp state variable
        byte [] Kdr;
        int    i, k, row, col;

        byte[] addInvSBox=generateAddInvSBox();
        for (i = 0; i < BLOCK_SIZE; i++) cipher[i] = addInvSBox[cipher[i] & 0xFF];

        traceInfo = "";
        if (traceLevel > 0) traceInfo = "decryptAES(" + Util.toHEX1(cipher) + ")";

        // verification
        if (cipher == null)
            throw new IllegalArgumentException("Empty ciphertext");
        if (cipher.length != BLOCK_SIZE)
            throw new IllegalArgumentException("Incorrect ciphertext length");

        Kdr = Kd[0];
        for (i = 0; i < BLOCK_SIZE; i++)    a[i] = (byte)(cipher[i] ^ Kdr[i]);
        if (traceLevel > 2)
            traceInfo += "n  R0 (Key = "+ Util.toHEX1(Kdr)+")nt AK = "+ Util.toHEX1(a);
        else if (traceLevel > 1)
            traceInfo += "n  R0 (Key = "+ Util.toHEX1(Kdr)+")t = "+ Util.toHEX1(a);

        for (int r = 1; r < numRounds; r++) {
            Kdr = Kd[r];            // get session keys for this round
            if (traceLevel > 1)    traceInfo += "n  R"+r+" (Key = "+ Util.toHEX1(Kdr)+")t";

            // InvShiftRows(state)
            shift=generateShiftNumbers();
            for (i = 0; i < BLOCK_SIZE; i++) {
                row = i % COL_SIZE;
                k = (i + BLOCK_SIZE - (shift[row] * COL_SIZE)) % BLOCK_SIZE;
                ta[i] = a[k];
            }
            if (traceLevel > 2)    traceInfo += "ntISR = "+ Util.toHEX1(ta);

            // InvSubBytes(state)
            byte[] generatedSBox=generateDSBox();
            for (i = 0; i < BLOCK_SIZE; i++) a[i] = generatedSBox[ta[i] & 0xFF];
            if (traceLevel > 2)    traceInfo += "ntISB = "+ Util.toHEX1(a);

            // AddRoundKey(state)
            for (i = 0; i < BLOCK_SIZE; i++)    ta[i] = (byte)(a[i] ^ Kdr[i]);
            if (traceLevel > 2)    traceInfo += "nt AK = "+ Util.toHEX1(ta);

            // InvMixColumns(state)
            for (col = 0; col < NUM_COLS; col++) {
                i = col * COL_SIZE;        // start index for this col
                a[i]   = (byte)(mul(0x0e,ta[i]) ^ mul(0x0b,ta[i+1]) ^ mul(0x0d,ta[i+2]) ^ mul(0x09,ta[i+3]));
                a[i+1] = (byte)(mul(0x09,ta[i]) ^ mul(0x0e,ta[i+1]) ^ mul(0x0b,ta[i+2]) ^ mul(0x0d,ta[i+3]));
                a[i+2] = (byte)(mul(0x0d,ta[i]) ^ mul(0x09,ta[i+1]) ^ mul(0x0e,ta[i+2]) ^ mul(0x0b,ta[i+3]));
                a[i+3] = (byte)(mul(0x0b,ta[i]) ^ mul(0x0d,ta[i+1]) ^ mul(0x09,ta[i+2]) ^ mul(0x0e,ta[i+3]));
            }
            if (traceLevel > 2)    traceInfo += "ntIMC";
            if (traceLevel > 1)    traceInfo += " = "+ Util.toHEX1(a);
        }

        Kdr = Kd[numRounds];
        if (traceLevel > 1)    traceInfo += "n  R"+numRounds+" (Key = "+ Util.toHEX1(Kdr)+")t";

        // InvShiftRows(state)
        shift=generateShiftNumbers();
        for (i = 0; i < BLOCK_SIZE; i++) {
            row = i % COL_SIZE;
            // get shifted byte index
            k = (i + BLOCK_SIZE - (shift[row] * COL_SIZE)) % BLOCK_SIZE;
            ta[i] = a[k];
        }
        if (traceLevel > 2)    traceInfo += "ntISR = "+ Util.toHEX1(a);

        // InvSubBytes(state)
        byte[] lastSBox=generateDSBox();
        for (i = 0; i < BLOCK_SIZE; i++) ta[i] = lastSBox[ta[i] & 0xFF];
        if (traceLevel > 2)    traceInfo += "ntISB = "+ Util.toHEX1(a);

        // AddRoundKey(state)
        for (i = 0; i < BLOCK_SIZE; i++)    a[i] = (byte)(ta[i] ^ Kdr[i]);
        if (traceLevel > 2)    traceInfo += "nt AK";
        if (traceLevel > 1)    traceInfo += " = "+ Util.toHEX1(a)+"n";
        if (traceLevel > 0)    traceInfo += " = "+ Util.toHEX1(a)+"n";
        return (a);
    }

    public void setKey(byte[] key) {
        final int BC = BLOCK_SIZE / 4;
        final int Klen = key.length;
        final int Nk = Klen / 4;

        int i, j, r;

        traceInfo = "";
        if (traceLevel > 0) traceInfo = "setKey(" + Util.toHEX1(key) + ")n";

        // set master number of rounds given size of this key
        numRounds = getRounds(Klen);
        final int ROUND_KEY_COUNT = (numRounds + 1) * BC;

        //holds 1 of the 4 bytes [b0 b1 b2 b3] in each word w
        byte[] w0 = new byte[ROUND_KEY_COUNT];
        byte[] w1 = new byte[ROUND_KEY_COUNT];
        byte[] w2 = new byte[ROUND_KEY_COUNT];
        byte[] w3 = new byte[ROUND_KEY_COUNT];

        Ke = new byte[numRounds + 1][BLOCK_SIZE]; // encryption
        Kd = new byte[numRounds + 1][BLOCK_SIZE]; // decryption

        for (i=0, j=0; i < Nk; i++) {
            w0[i] = key[j++]; w1[i] = key[j++]; w2[i] = key[j++]; w3[i] = key[j++];
        }

        // implement key expansion algorithm
        byte t0, t1, t2, t3, old0;
        for (i = Nk; i < ROUND_KEY_COUNT; i++) {
            t0 = w0[i-1]; t1 = w1[i-1]; t2 = w2[i-1]; t3 = w3[i-1];
            if (i % Nk == 0) {
                old0 = t0;
                t0 = (byte)(ESBox[t1 & 0xFF] ^ rcon[i/Nk]);
                t1 = (byte)(ESBox[t2 & 0xFF]);
                t2 = (byte)(ESBox[t3 & 0xFF]);
                t3 = (byte)(ESBox[old0 & 0xFF]);
            }
            else if ((Nk > 6) && (i % Nk == 4)) {
                t0 = ESBox[t0 & 0xFF]; t1 = ESBox[t1 & 0xFF]; t2 = ESBox[t2 & 0xFF]; t3 = ESBox[t3 & 0xFF];
            }
            w0[i] = (byte)(w0[i-Nk] ^ t0);
            w1[i] = (byte)(w1[i-Nk] ^ t1);
            w2[i] = (byte)(w2[i-Nk] ^ t2);
            w3[i] = (byte)(w3[i-Nk] ^ t3);
        }

        // copy values into en/decrypt session arrays
        for (r = 0, i = 0; r < numRounds + 1; r++) {
            for (j = 0; j < BC; j++) {
                Ke[r][4*j] = w0[i];
                Ke[r][4*j+1] = w1[i];
                Ke[r][4*j+2] = w2[i];
                Ke[r][4*j+3] = w3[i];
                Kd[numRounds - r][4*j] = w0[i];
                Kd[numRounds - r][4*j+1] = w1[i];
                Kd[numRounds - r][4*j+2] = w2[i];
                Kd[numRounds - r][4*j+3] = w3[i];
                i++;
            }
        }
    }

    public static String static_byteArrayToString(byte[] data) {
        String res = "";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<data.length; i++) {
            int n = (int) data[i];
            if(n<0) n += 256;
            sb.append((char) n);
        }
        res = sb.toString();
        return res;
    }

    public static byte[] static_stringToByteArray(String s){
        byte[] temp = new byte[s.length()];
        for(int i=0;i<s.length();i++){
            temp[i] = (byte) s.charAt(i);
        }
        return temp;
    }

    public String _cryptAll(String data, int mode)  {
        AESmodified aes = this;
        if(data.length()/16 > ((int) data.length()/16)) {
            int rest = data.length()-((int) data.length()/16)*16;
            for(int i=0; i<rest; i++)
                data += " ";
        }
        int nParts = (int) data.length()/16;
        byte[] res = new byte[data.length()];
        String partStr = "";
        byte[] partByte = new byte[16];
        for(int p=0; p<nParts; p++) {
            partStr = data.substring(p*16, p*16+16);
            partByte = static_stringToByteArray(partStr);
            if(mode==1) partByte = aes.encrypt(partByte);
            if(mode==2) partByte = aes.decrypt(partByte);
            for(int b=0; b<16; b++)
                res[p*16+b] = partByte[b];
        }
        return static_byteArrayToString(res);
    }

    public String Encrypt(String data) {
        while((data.length() % 32) != 0) data += " ";
        return _cryptAll(data, 1);
    }

    public String Decrypt(String data) {
        return _cryptAll(data, 2).trim();
    }

    final static class Util {
        public static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        public static String toHEX (byte[] ba) {
            int length = ba.length;
            char[] buf = new char[length * 3];
            for (int i = 0, j = 0, k; i < length; ) {
                k = ba[i++];
                buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
                buf[j++] = HEX_DIGITS[ k        & 0x0F];
                buf[j++] = ' ';
            }
            return new String(buf);
        }

        public static String toHEX1 (byte[] ba) {
            int length = ba.length;
            char[] buf = new char[length * 2];
            for (int i = 0, j = 0, k; i < length; ) {
                k = ba[i++];
                buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
                buf[j++] = HEX_DIGITS[ k        & 0x0F];
            }
            return new String(buf);
        }
    }

    public String getKey() {
        String key;
        do {
            System.out.println("\nEnter key of size 16 bytes: ");
            key = new Scanner(System.in).nextLine();

            // Verification
            if (key.length() != 16 && key.length() != 24 & key.length() != 32)
                System.out.println("AES just uses 128/192/256-bit (16/24/32 byte) keys!");
        } while (key.length() != 16 && key.length() != 24 && key.length() != 32);

        return key;
    }

    //file input part here
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
    public static String browseFile()
    {
        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();

        // Show the dialog and get the user's selection
        int result = fileChooser.showOpenDialog(new JFrame());

        String filePath = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            // The user selected a file, so get its path
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Selected file: " + filePath);
        } else {
            System.out.println("No file selected.");
        }
        return filePath;
    }

    //DNA part
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

    //generate s-box
    public static byte[] generateSBox() {
        byte[] Keyy=sessionKey.getBytes();
        int shift = Keyy[0] ^ Keyy[Keyy.length - 1];
       //int shift=-3;
        if(shift<0){
            shift=-(shift);
        }

        byte[] newSBox = new byte[256];
        for (long i = 0; i < ESBox.length; i++) {
            long newIndex = (i + shift) % ESBox.length;
            newSBox[(int) newIndex] = ESBox[(int) i];
        }
        return newSBox;
    }
    public static byte[] generateDSBox() {
       byte[] Keyy=sessionKey.getBytes();
        int shift = Keyy[0] ^ Keyy[Keyy.length - 1];
        //int shift=-3;
        if(shift<0){
            shift=-(shift);
        }

        byte[] newSBox = new byte[256];
        for (long i = 0; i < ESBox.length; i++) {
            long newIndex = (i + shift) % ESBox.length;
            newSBox[(int) newIndex] = ESBox[(int) i];
        }
        byte[] inverseSBox = new byte[256];

        for (int i = 0; i < 256; i++) {
            inverseSBox[newSBox[i] & 0xFF] = (byte) i;
        }

        return inverseSBox;
    }
    public static byte[] generateAddiSBox() {
        String originalKey=sessionKey;
        StringBuilder sb = new StringBuilder(originalKey);
        sb.reverse();
        String reversedKey = String.valueOf(sb.reverse());

        byte[] Keyy=reversedKey.getBytes();
        int shift = Keyy[Keyy.length - 4];
        if(shift<0){
            shift=-(shift);
        }

        byte[] newSBox = new byte[256];
        for (long i = 0; i < ESBox.length; i++) {
            long newIndex = (i + shift) % ESBox.length;
            newSBox[(int) newIndex] = ESBox[(int) i];
        }
        return newSBox;
    }
    public static byte[] generateAddInvSBox() {
        String originalKey=sessionKey;
        StringBuilder sb = new StringBuilder(originalKey);
        sb.reverse();
        String reversedKey = String.valueOf(sb.reverse());

        byte[] Keyy=reversedKey.getBytes();
        int shift = Keyy[Keyy.length - 4];
        if(shift<0){
            shift=-(shift);
        }

        byte[] newSBox = new byte[256];
        for (long i = 0; i < ESBox.length; i++) {
            long newIndex = (i + shift) % ESBox.length;
            newSBox[(int) newIndex] = ESBox[(int) i];
        }
        byte[] inverseSBox = new byte[256];

        for (int i = 0; i < 256; i++) {
            inverseSBox[newSBox[i] & 0xFF] = (byte) i;
        }

        return inverseSBox;
    }


    public static int[] generateShiftNumbers() {
        String input=sessionKey;
        byte[] byteArray = input.getBytes();
        int[] mod4Numbers = new int[4];
        mod4Numbers[0] = byteArray[1] % 4;
        mod4Numbers[1] = byteArray[5] % 4;
        mod4Numbers[2] = byteArray[8] % 4;
        mod4Numbers[3] = byteArray[14] % 4;

        return mod4Numbers;
    }

}

