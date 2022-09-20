package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BitMap {

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

	private byte[] reverseArray(byte[] array) {
		byte[] reverseArray = new byte[array.length];
		for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
			reverseArray[i] = array[j];
		}
		return reverseArray;
	}

	private int bytesToInt(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return byteBuffer.getInt();
	}

	private short bytesToShort(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return byteBuffer.getShort();
	}

}
