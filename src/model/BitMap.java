package model;

import java.io.*;
import java.util.Arrays;

public class BitMap {
    private byte[] headerBytes;
    private int[] headerInfo;
    private byte[] bodyBytes;
    private FileOutputStream fileOut;
    private FileInputStream file;

    public BitMap(String source) throws IOException {
        file = new FileInputStream(source);
        fileOut = new FileOutputStream("Copia1" + source);
        headerBytes = new byte[54];
        headerInfo = new int[16];
        file.read(headerBytes);
        int[] tempInfo = readBitmap();
        bodyBytes = new byte[tempInfo[6] * tempInfo[7] * 3];
        file.read(bodyBytes);
        file.close();
    }

    public int[] getHeaderInfo() {
        return headerInfo;
    }


    public int[] readBitmap() {
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

    public int[] decimalToByte(int newValue) {
        int[] newBytes = new int[4];
        for (int i = 0; i < newBytes.length; i++) {
            newBytes[i] = newValue % 256;
            newValue /= 256;
        }
        return newBytes;
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
                int[] widthSize = decimalToByte(newValue);//Ancho de la imagen 18 - 21
                pictureSize = decimalToByte(pictureLength);
                fileSize = decimalToByte((pictureLength + 54));

                //Metodo para dar nuevo tamaño de ancho
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

    public void newBody(int newValue, int attributeToModify) throws IOException {
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
                int valueToCut = headerInfo[6] - newValue;
                for (int i = 0, j = 0; j < newBodySize.length; i++, j++) {
                    if (j != 0 && (j % (newValue * 3) == 0)) {
                        i = i + ((valueToCut) * 3);
                    }
                    newBodySize[j] = bodyBytes[i];
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attributeToModify);
        }
        fileOut.write(newBodySize);
    }

    public void newBody(int initialPos, int finalPos, int attributeToModify) throws IOException {
        int newValue = finalPos - initialPos;
        byte[] newBodySize;
        switch (attributeToModify) {
            case 1:
                newBodySize = new byte[newValue * headerInfo[6] * 3];
                for (int i = 0, j = initialPos * headerInfo[6] * 3; i < newBodySize.length; i++, j++) {
                    newBodySize[i] = bodyBytes[j];
                }
                fileOut.write(newBodySize);
                break;
            case 2:
                byte[] auxBody = new byte[newValue*3];
                byte[] auxHeader = new byte[54];
                FileInputStream fileInAux = new FileInputStream("image.bmp");
                fileInAux.read(auxHeader);
                for (int i = 0; i < headerInfo[7]; i++) {
                    fileInAux.skip(initialPos * 3);
                    fileInAux.read(auxBody);
                    fileInAux.skip((headerInfo[6] - finalPos) * 3);
                    fileOut.write(auxBody);
                }
                fileInAux.close();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + attributeToModify);
        }
    }


    public void createNewImage(int newValue, int attributeToModify) throws IOException {
        byte[] newHeader = newHeader(newValue, attributeToModify);
        fileOut.write(newHeader);
        newBody(newValue, attributeToModify);
        fileOut.close();
    }

    public void createNewImage(int initialPos, int finalPos, int attributeToModify) throws IOException {
        byte[] newHeader = newHeader(finalPos - initialPos, attributeToModify);
        fileOut.write(newHeader);
        newBody(initialPos, finalPos, attributeToModify);
        fileOut.close();
    }

    public static void main(String[] args) throws IOException {
        BitMap bitmap = new BitMap("image.bmp");
        int initialPos = 102;
        int finalPos = 700;
        bitmap.createNewImage(initialPos, finalPos, 2);
        System.out.println("Imagen original");
        System.out.println(Arrays.toString(bitmap.headerBytes));
        System.out.print(bitmap.headerInfo[1] + " ");
        System.out.print(bitmap.headerInfo[6] + " ");
        System.out.print(bitmap.headerInfo[7] + " ");
        System.out.println(bitmap.headerInfo[11] + " ");
        System.out.println("Imagen modificada");
        byte[] newHeader = bitmap.newHeader(finalPos-initialPos, 2);
        System.out.println(Arrays.toString(newHeader));

        System.out.print(bitmap.getRealValue(bitmap.cutArray(bitmap.byteArrayToIntArray(newHeader), 2, 5)) + " ");
        System.out.print(bitmap.getRealValue(bitmap.cutArray(bitmap.byteArrayToIntArray(newHeader), 18, 21)) + " ");
        System.out.print(bitmap.getRealValue(bitmap.cutArray(bitmap.byteArrayToIntArray(newHeader), 22, 25)) + " ");
        System.out.print(bitmap.getRealValue(bitmap.cutArray(bitmap.byteArrayToIntArray(newHeader), 34, 37)) + " ");
    }
}
