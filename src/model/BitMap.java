package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitMap {
    private byte[] headerBytes;

    public int[] readBitmap() throws IOException {
        FileInputStream file = new FileInputStream("image.bmp");
        headerBytes = new byte[54];
        int[] headerInfo = new int[16];
        file.read(headerBytes);
        file.close();
        int[] headerInt = byteArrayToIntArray(headerBytes);
        int[] bfTypeBytes = cutArray(headerInt, 0, 1);
        headerInfo[0] = getRealValue(bfTypeBytes);
        int[] bfSizeBytes = cutArray(headerInt, 2, 5);
        headerInfo[1] = getRealValue(bfSizeBytes);
        int[] bfReserved1Bytes = cutArray(headerInt, 6, 7);
        headerInfo[2] = getRealValue(bfReserved1Bytes);
        int[] bfReserved2Bytes = cutArray(headerInt, 8, 9);
        headerInfo[3] = getRealValue(bfReserved2Bytes);
        int[] bfOffBitsBytes = cutArray(headerInt, 10, 13);
        headerInfo[4] = getRealValue(bfOffBitsBytes);
        int[] biSizeBytes = cutArray(headerInt, 14, 17);
        headerInfo[5] = getRealValue(biSizeBytes);
        int[] biWidthBytes = cutArray(headerInt, 18, 21);
        headerInfo[6] = getRealValue(biWidthBytes);
        int[] biHeightBytes = cutArray(headerInt, 22, 25);
        headerInfo[7] = getRealValue(biHeightBytes);
        int[] biPlanesBytes = cutArray(headerInt, 26, 27);
        headerInfo[8] = getRealValue(biPlanesBytes);
        int[] biBitCountBytes = cutArray(headerInt, 28, 29);
        headerInfo[9] = getRealValue(biBitCountBytes);
        int[] biCompressionBytes = cutArray(headerInt, 30, 33);
        headerInfo[10] = getRealValue(biCompressionBytes);
        int[] biSizeImageBytes = cutArray(headerInt, 34, 37);
        headerInfo[11] = getRealValue(biSizeImageBytes);
        int[] biXPelsPerMeterBytes = cutArray(headerInt, 38, 41);
        headerInfo[12] = getRealValue(biXPelsPerMeterBytes);
        int[] biYPelsPerMeterBytes = cutArray(headerInt, 42, 45);
        headerInfo[13] = getRealValue(biYPelsPerMeterBytes);
        int[] biClrUsedBytes = cutArray(headerInt, 46, 49);
        headerInfo[14] = getRealValue(biClrUsedBytes);
        int[] biCrlImportantBytes = cutArray(headerInt, 50, 53);
        headerInfo[15] = getRealValue(biCrlImportantBytes);
        return headerInfo;
    }

    public int[] cutArray(int[] arrayToCut, int from, int to) {
        int[] newLength = new int[(to - from) + 1];
        for (int i = 0; i < newLength.length; i++) {
            newLength[i] = arrayToCut[from++];
        }
        return newLength;
    }

    public int getRealValue(int[] array) {
        double pow = 0;
        for (int i = 0; i < array.length; i++) {
            pow = pow + array[i] * Math.pow(256, i);
        }
        return (int) pow;
    }

    public int[] byteArrayToIntArray(byte[] byteArray) {
        int[] unsigned = new int[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] < 0) {
                unsigned[i] = unsignedToBytes(byteArray[i]);
            } else {
                unsigned[i] = byteArray[i];
            }
        }
        return unsigned;
    }

    public int unsignedToBytes(byte a) {
        return a + 256;
    }

    private List<String> toBinaryValues(String binaryValue) {
        List<String> bytes = new ArrayList<>();
        boolean end = false;
        for (int i = 0; !end; i++) {
            int a = i * 8;
            int b = ((i + 1) * 8);
            if (b < binaryValue.length()) {
                bytes.add(binaryValue.substring(a, b));
                bytes.set(i, reverseString(bytes.get(i)));
            } else {
                bytes.add(binaryValue.substring(a));
                bytes.set(i, reverseString(bytes.get(i)));
                end = true;
            }
        }
        return bytes;
    }

    private String reverseString(String chain) {
        StringBuilder auxString = new StringBuilder(chain);
        chain = auxString.reverse().toString();
        return chain;
    }

    public byte[] newHeaderValue(int newValue) {
        byte[] newHeader = Arrays.copyOf(headerBytes, headerBytes.length);
        String binaryValue = Integer.toBinaryString(newValue);
        binaryValue = reverseString(binaryValue);
        List<String> binaryList = toBinaryValues(binaryValue);
        long[] bytes = new long[4];
        for (int i = bytes.length; i > 0; i--) {
            if (i>binaryList.size()) {
                bytes[i-1] = 0;
            }else {
                bytes[i-1] = Long.parseLong(binaryList.get(i-1), 2);
            }
        }
        int pos = 0;
        for (int i = 2; i < 5; i++) {
            newHeader[i] = (byte) bytes[pos];
            pos++;
        }
        return newHeader;
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }
}
