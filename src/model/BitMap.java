package model;

import java.io.FileInputStream;
import java.io.IOException;

public class BitMap {

<<<<<<< HEAD
	public int[] readBitmap(String path) throws IOException {
		FileInputStream file = new FileInputStream(path);
		byte[] headerBytes = new byte[56];
		int[] headerInfo = new int[16];
		file.read(headerBytes);
		file.close();
		byte[] bfTypeBytes = Arrays.copyOfRange(headerBytes, 0, 2);
		headerInfo[0] = bytesToShort(reverseArray(bfTypeBytes));
		byte[] bfSizeBytes = Arrays.copyOfRange(headerBytes, 2, 6);
		headerInfo[1] = bytesToInt(reverseArray(bfSizeBytes));
		byte[] bfReserved1Bytes = Arrays.copyOfRange(headerBytes, 6, 8);
		headerInfo[2] = bytesToShort(reverseArray(bfReserved1Bytes));
		byte[] bfReserved2Bytes = Arrays.copyOfRange(headerBytes, 8, 10);
		headerInfo[3] = bytesToShort(reverseArray(bfReserved2Bytes));
		byte[] bfOffBitsBytes = Arrays.copyOfRange(headerBytes, 10, 14);
		headerInfo[4] = bytesToInt(reverseArray(bfOffBitsBytes));
		byte[] biSizeBytes = Arrays.copyOfRange(headerBytes, 14, 18);
		headerInfo[5] = bytesToInt(reverseArray(biSizeBytes));
		byte[] biWidthBytes = Arrays.copyOfRange(headerBytes, 18, 22);
		headerInfo[6] = bytesToInt(reverseArray(biWidthBytes));
		byte[] biHeightBytes = Arrays.copyOfRange(headerBytes, 22, 26);
		headerInfo[7] = bytesToInt(reverseArray(biHeightBytes));
		byte[] biPlanesBytes = Arrays.copyOfRange(headerBytes, 26, 28);
		headerInfo[8] = bytesToShort(reverseArray(biPlanesBytes));
		byte[] biBitCountBytes = Arrays.copyOfRange(headerBytes, 28, 30);
		headerInfo[9] = bytesToShort(reverseArray(biBitCountBytes));
		byte[] biCompressionBytes = Arrays.copyOfRange(headerBytes, 30, 34);
		headerInfo[10] = bytesToInt(reverseArray(biCompressionBytes));
		byte[] biSizeImageBytes = Arrays.copyOfRange(headerBytes, 34, 38);
		headerInfo[11] = bytesToInt(reverseArray(biSizeImageBytes));
		byte[] biXPelsPerMeterBytes = Arrays.copyOfRange(headerBytes, 38, 42);
		headerInfo[12] = bytesToInt(reverseArray(biXPelsPerMeterBytes));
		byte[] biYPelsPerMeterBytes = Arrays.copyOfRange(headerBytes, 42, 46);
		headerInfo[13] = bytesToInt(reverseArray(biYPelsPerMeterBytes));
		byte[] biClrUsedBytes = Arrays.copyOfRange(headerBytes, 46, 50);
		headerInfo[14] = bytesToInt(reverseArray(biClrUsedBytes));
		byte[] biCrlImportantBytes = Arrays.copyOfRange(headerBytes, 50, 54);
		headerInfo[15] = bytesToInt(reverseArray(biCrlImportantBytes));
		return headerInfo;
	}
=======
    public int[] readBitmap() throws IOException {
        FileInputStream file = new FileInputStream("image.bmp");
        byte[] headerBytes = new byte[54];
        int[] headerInfo = new int[16];
        file.read(headerBytes);
        file.close();
        int[] headerInt = byteArrayToIntArray(headerBytes);
        int[] bfTypeBytes = cutArray(headerInt, 0, 1);
        headerInfo[0]= getRealValue(bfTypeBytes);
        int[] bfSizeBytes = cutArray(headerInt, 2, 5);
        headerInfo[1] = getRealValue(bfSizeBytes);
        int[] bfReserved1Bytes = cutArray(headerInt, 6, 7);
        headerInfo[2]= getRealValue((bfReserved1Bytes));
        int[] bfReserved2Bytes = cutArray(headerInt, 8, 9);
        headerInfo[3] = getRealValue((bfReserved2Bytes));
        int[] bfOffBitsBytes = cutArray(headerInt, 10, 13);
        headerInfo[4] = getRealValue((bfOffBitsBytes));
        int[] biSizeBytes = cutArray(headerInt, 14, 17);
        headerInfo[5] = getRealValue((biSizeBytes));
        int[] biWidthBytes = cutArray(headerInt, 18, 21);
        headerInfo[6] = getRealValue((biWidthBytes));
        int[] biHeightBytes = cutArray(headerInt, 22, 25);
        headerInfo[7] = getRealValue((biHeightBytes));
        int[] biPlanesBytes = cutArray(headerInt, 26, 27);
        headerInfo[8] = getRealValue((biPlanesBytes));
        int[] biBitCountBytes = cutArray(headerInt, 28, 29);
        headerInfo[9] = getRealValue((biBitCountBytes));
        int[] biCompressionBytes = cutArray(headerInt, 30, 33);
        headerInfo[10] = getRealValue((biCompressionBytes));
        int[] biSizeImageBytes = cutArray(headerInt, 34, 37);
        headerInfo[11] = getRealValue((biSizeImageBytes));
        int[] biXPelsPerMeterBytes = cutArray(headerInt, 38, 41);
        headerInfo[12] = getRealValue((biXPelsPerMeterBytes));
        int[] biYPelsPerMeterBytes = cutArray(headerInt, 42, 45);
        headerInfo[13] = getRealValue((biYPelsPerMeterBytes));
        int[] biClrUsedBytes = cutArray(headerInt, 46, 49);
        headerInfo[14] = getRealValue((biClrUsedBytes));
        int[] biCrlImportantBytes = cutArray(headerInt, 50, 53);
        headerInfo[15] = getRealValue((biCrlImportantBytes));
        return headerInfo;
    }
>>>>>>> secundaria

    private int[] cutArray(int[] arrayToCut, int from, int to) {
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

}
