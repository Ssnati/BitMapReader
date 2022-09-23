package model;

import java.io.*;
import java.util.Arrays;

public class BitMap {
    private byte[] headerBytes;
    private int[] headerInfo;
    private byte[] bodyBytes;
    private FileOutputStream fileOut;
    private Pixel[] pixelsValue;

    public BitMap(String source) throws IOException {
        FileInputStream file = new FileInputStream(source);
        fileOut = new FileOutputStream("Copia" + source);
        headerBytes = new byte[54];
        headerInfo = new int[16];
        file.read(headerBytes);
        int[] tempInfo = readBitmap();
        bodyBytes = new byte[tempInfo[6] * tempInfo[7] * 3];
        file.read(bodyBytes);
        pixelsValue = new Pixel[bodyBytes.length / 3];
        pixelsValue = readPixelsImage(bodyBytes);
        file.close();
    }

    public int[] getHeaderInfo() {
        return headerInfo;
    }

    private Pixel[] readPixelsImage(byte[] bodyBytes) {
        Pixel[] rgbPixels = new Pixel[bodyBytes.length / 3];
        for (int i = 0; i < rgbPixels.length; i++) {
            int blue = unsignedToBytes(bodyBytes[i * 3]);
            int green = unsignedToBytes(bodyBytes[(i * 3) + 1]);
            int red = unsignedToBytes(bodyBytes[(i * 3) + 2]);
            pixelsValue[i] = new Pixel(red, green, blue);
        }
        return rgbPixels;
    }

    public int[] readBitmap() throws IOException {
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
        for (int i = 0; i < newLength.length; i++, from++) {
            newLength[i] = arrayToCut[from];
        }
        return newLength;
    }

    public int getRealValue(int[] array) {
        double pow = 0;
        for (int i = 0; i < array.length; i++) {
            pow += array[i] * Math.pow(256, i);
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
        int default1 = a;
        if (a < 0)
            default1 = a + 256;
        return default1;
    }

    public byte[] newHeader(int newValue, int attributeToModify) {
        byte[] newHeaderBytes = Arrays.copyOf(headerBytes, headerBytes.length);
        int[] pictureSize;
        int pictureLength;
        int[] fileSize;
        switch (attributeToModify) {
            case 1:
                pictureLength = newValue * headerInfo[6] * 3;
                int[] highSize = decimalToByte(newValue);//Altura de la imagen 22 - 25
                pictureSize = decimalToByte(pictureLength);
                fileSize = decimalToByte(pictureLength + 54);

                //Metodo para dar nuevo tamaño de altura
                for (int i = 22, pos = 0; i < 25; i++, pos++) {
                    newHeaderBytes[i] = (byte) highSize[pos];
                }
                break;
            case 2:
                pictureLength = newValue * headerInfo[7] * 3;

                int[] widthSize = decimalToByte(newValue);//Altura de la imagen 18 - 21
                pictureSize = decimalToByte(pictureLength);
                fileSize = decimalToByte(pictureLength + 54);

                //Metodo para dar nuevo tamaño de altura
                for (int i = 18, pos = 0; i < 21; i++, pos++) {
                    newHeaderBytes[i] = (byte) widthSize[pos];
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attributeToModify);
        }

        //Metodo para dar nuevo tamaño de archivo
        for (int i = 2, pos = 0; i < 5; i++, pos++) {
            newHeaderBytes[i] = (byte) fileSize[pos];
        }

        //Metodo para dar nuevo tamaño de imagen
        for (int i = 34, pos = 0; i < 37; i++, pos++) {
            newHeaderBytes[i] = (byte) pictureSize[pos];
        }

        return newHeaderBytes;
    }

    public int[] decimalToByte(int newValue) {
        int[] newValues = new int[4];
        for (int i = 0; i < newValues.length; i++) {
            newValues[i] = newValue % 256;
            newValue /= 256;
        }
        return newValues;
    }

    public byte[] newBody(int newValue, int attributeToModify) {
        byte[] newBodySize;
        switch (attributeToModify) {
            case 1:
                newBodySize = new byte[newValue * headerInfo[6] * 3];
                for (int i = 0; i < newBodySize.length; i++) {
                    newBodySize[i] = bodyBytes[i];
                }
                break;
            case 2:
                newBodySize = new byte[newValue * headerInfo[7] * 3];
                int rest = headerInfo[6] - newValue;
                for (int i = 0, j = 0; j < newBodySize.length; i++, j++) {
                    if (j != 0 && (j % (newValue * 3) == 0)) {
                        i = i + (rest) * 3;
                    }
                    newBodySize[j] = bodyBytes[i];
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + attributeToModify);
        }
        return newBodySize;
    }

    public void createNewImage(int newValue, int attributeToModify) throws IOException {
        byte[] newHeader = newHeader(newValue, attributeToModify);
        byte[] newBody = newBody(newValue, attributeToModify);

        byte[] newFileBytes = new byte[newHeader.length + newBody.length];
        for (int totalSize = 0, i = 0; totalSize < newFileBytes.length; totalSize++, i++) {
            if (totalSize < newHeader.length) {
                newFileBytes[totalSize] = newHeader[i];
            } else {
                newFileBytes[totalSize] = newBody[i - newHeader.length];
            }
        }
        fileOut.write(newFileBytes);
        fileOut.close();
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }
}
