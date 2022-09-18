package test;

import java.io.IOException;

import model.Bitmap;

public class Test {

	public void printHeaderInfo(int[] headerInfo) {
		System.out.println("Tipo de fichero \"BM\": " + headerInfo[0]);
		System.out.println("Tamaño del archivo: " + headerInfo[1]);
		System.out.println("Reservado: " + headerInfo[2]);
		System.out.println("Reservado: " + headerInfo[3]);
		System.out.println("Inicio de los datos de la imagen: " + headerInfo[4]);
		System.out.println("Tamaño de la cabezara del bitmap: " + headerInfo[5]);
		System.out.println("Anchura (pixeles): " + headerInfo[6]);
		System.out.println("Altura (pixeles): " + headerInfo[7]);
		System.out.println("Numero de Planos: " + headerInfo[8]);
		System.out.println("Tamaño de cada punto: " + headerInfo[9]);
		System.out.println("Compresion (0 = no comprimido): " + headerInfo[10]);
		System.out.println("Tamaño de la imagen: " + headerInfo[11]);
		System.out.println("Resolucion horizontal: " + headerInfo[12]);
		System.out.println("Resolucion vertical: " + headerInfo[13]);
		System.out.println("Tamaño de la table de color: " + headerInfo[14]);
		System.out.println("Contador de colores importantes: " + headerInfo[15]);
	}

	public static void main(String[] args) {
		Test test = new Test();
		Bitmap bitMap = new Bitmap();
		int[] headerInfo;
		try {
			headerInfo = bitMap.readBitmap("image.bmp");
			test.printHeaderInfo(headerInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
