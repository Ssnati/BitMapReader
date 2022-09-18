package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Bitmap {

	public int[] readBitmap(String path) throws IOException {
		FileInputStream file = new FileInputStream(path);
		byte[] headerBytes = new byte[56];
		file.read(headerBytes);
		file.close();
		byte[] bfTypeBytes = Arrays.copyOfRange(headerBytes, 0, 2);
		short bfType = bytesToShort(reverseArray(bfTypeBytes));
		byte[] bfSizeBytes = Arrays.copyOfRange(headerBytes, 2, 6);
		int bfSize = bytesToInt(reverseArray(bfSizeBytes));
		byte[] bfReserved1Bytes = Arrays.copyOfRange(headerBytes, 6, 8);
		short bfReserved1 = bytesToShort(reverseArray(bfReserved1Bytes));
		byte[] bfReserved2Bytes = Arrays.copyOfRange(headerBytes, 8, 10);
		short bfReserved2 = bytesToShort(reverseArray(bfReserved2Bytes));
		byte[] bfOffBitsBytes = Arrays.copyOfRange(headerBytes, 10, 14);
		int bfOffBits = bytesToInt(reverseArray(bfOffBitsBytes));
		byte[] biSizeBytes = Arrays.copyOfRange(headerBytes, 14, 18);
		int biSize = bytesToInt(reverseArray(biSizeBytes));
		byte[] biWidthBytes = Arrays.copyOfRange(headerBytes, 18, 22);
		int biWidth = bytesToInt(reverseArray(biWidthBytes));
		byte[] biHeightBytes = Arrays.copyOfRange(headerBytes, 22, 26);
		int biHeight = bytesToInt(reverseArray(biHeightBytes));
		byte[] biPlanesBytes = Arrays.copyOfRange(headerBytes, 26, 28);
		short biPlanes = bytesToShort(reverseArray(biPlanesBytes));
		byte[] biBitCountBytes = Arrays.copyOfRange(headerBytes, 28, 30);
		short biBitCount = bytesToShort(reverseArray(biBitCountBytes));
		byte[] biCompressionBytes = Arrays.copyOfRange(headerBytes, 30, 34);
		int biCompression = bytesToInt(reverseArray(biCompressionBytes));
		byte[] biSizeImageBytes = Arrays.copyOfRange(headerBytes, 34, 38);
		int biSizeImage = bytesToInt(reverseArray(biSizeImageBytes));
		byte[] biXPelsPerMeterBytes = Arrays.copyOfRange(headerBytes, 38, 42);
		int biXPelsPerMeter = bytesToInt(reverseArray(biXPelsPerMeterBytes));
		byte[] biYPelsPerMeterBytes = Arrays.copyOfRange(headerBytes, 42, 46);
		int biYPelsPerMeter = bytesToInt(reverseArray(biYPelsPerMeterBytes));
		byte[] biClrUsedBytes = Arrays.copyOfRange(headerBytes, 46, 50);
		int biClrUsed = bytesToInt(reverseArray(biClrUsedBytes));
		byte[] biCrlImportantBytes = Arrays.copyOfRange(headerBytes, 50, 54);
		int biCrlImportant = bytesToInt(reverseArray(biCrlImportantBytes));
		int[] headerInfo = new int[16];
		headerInfo[0] = bfType;
		headerInfo[1] = bfSize;
		headerInfo[2] = bfReserved1;
		headerInfo[3] = bfReserved2;
		headerInfo[4] = bfOffBits;
		headerInfo[5] = biSize;
		headerInfo[6] = biWidth;
		headerInfo[7] = biHeight;
		headerInfo[8] = biPlanes;
		headerInfo[9] = biBitCount;
		headerInfo[10] = biCompression;
		headerInfo[11] = biSizeImage;
		headerInfo[12] = biXPelsPerMeter;
		headerInfo[13] = biYPelsPerMeter;
		headerInfo[14] = biClrUsed;
		headerInfo[15] = biCrlImportant;
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
