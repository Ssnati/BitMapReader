package presenter;

import java.io.IOException;
import java.util.Arrays;

import model.*;
import view.*;

public class Presenter {
    private BitMap bitmap;
    private View view;

    public Presenter() {
        view = new View();
        int[] headerInfo;
        try {
            bitmap = new BitMap("image.bmp");
            headerInfo = bitmap.readBitmap();
            printHeaderInfo(headerInfo);
        } catch (IOException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void printHeaderInfo(int[] headerInfo) {
        byte[] newHeader = bitmap.newHeader(768/2, 1);
        view.showMessage("Tipo de fichero \"BM\": " + headerInfo[0]);
        view.showMessage("Tamaño del archivo: " + headerInfo[1]);
        view.showMessage("Valores de la cabecera original:           " + Arrays.toString(bitmap.getHeaderBytes()));
        view.showMessage("Valores de la cabecera con el nuevo valor: " + Arrays.toString(newHeader));
        view.showMessage("Reservado: " + headerInfo[2]);
        view.showMessage("Reservado: " + headerInfo[3]);
        view.showMessage("Inicio de los datos de la imagen: " + headerInfo[4]);
        view.showMessage("Tamaño de la cabezara del bitmap: " + headerInfo[5]);
        view.showMessage("Anchura (pixeles): " + headerInfo[6]);
        view.showMessage("Altura (pixeles): " + headerInfo[7]);
        view.showMessage("Numero de Planos: " + headerInfo[8]);
        view.showMessage("Tamaño de cada punto: " + headerInfo[9]);
        view.showMessage("Compresion (0 = no comprimido): " + headerInfo[10]);
        view.showMessage("Tamaño de la imagen: " + headerInfo[11]);
        view.showMessage("Resolucion horizontal: " + headerInfo[12]);
        view.showMessage("Resolucion vertical: " + headerInfo[13]);
        view.showMessage("Tamaño de la table de color: " + headerInfo[14]);
        view.showMessage("Contador de colores importantes: " + headerInfo[15]);
    }

    public static void main(String[] args) {
    new Presenter();
    }

}
