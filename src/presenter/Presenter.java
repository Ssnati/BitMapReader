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
        try {
            bitmap = new BitMap("image.bmp");
        } catch (IOException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void run(){
        try {
            //printHeaderInfo(bitmap.readBitmap());
            principalMenu();
        } catch (IOException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void principalMenu() throws IOException {
        int[] headerInfo = bitmap.getHeaderInfo();
        int option;
        switch (view.readInt("Bienvenido, elige la opcion\n1. Modificar el alto de la imagen\n2. Modificar el ancho de la imagen")){
            case 1:
                option = highMenu();
                if (option==1) {
                    bitmap.createNewImage(view.readInt("Alto actual de la imagen en pixeles: " + headerInfo[7] + "\nIngresa el nuevo valor del alto de la imagen"), 1);
                } else if (option==2) {
                    bitmap.createNewImage(view.readInt("Alto actual de la imagen en pixeles: " + headerInfo[7] + "\nIngresa el pixel de inicio de la imagen"), view.readInt("Alto actual de la imagen en pixeles: " + headerInfo[7] + "\nIngresa el pixel de finalizacion de la imagen"), 1);
                }else {
                    view.showMessage("Elegiste una opcion invalida");
                }
                break;
            case 2:
                option = widthMenu();
                if (option==1) {
                    bitmap.createNewImage(view.readInt("Ancho actual de la imagen en pixeles: " + headerInfo[6] + "\nIngresa el nuevo valor del ancho de la imagen"), 2);
                } else if (option==2) {
                    bitmap.createNewImage(view.readInt("Ancho actual de la imagen en pixeles: " + headerInfo[6] + "\nIngresa el pixel de inicio de la imagen"), view.readInt("Ancho actual de la imagen en pixeles: " + headerInfo[6] + "\nIngresa el pixel de finalizacion de la imagen"), 2);
                }else {
                    view.showMessage("Elegiste una opcion invalida");
                }
                break;
        }
    }

    private int widthMenu() {
        return view.readInt("Elige la opcion del nuevo ancho\n1. Para declarar el nuevo valor del ancho\n2. Para colocar el pixel de inicio y el pixel final");
    }

    public int highMenu(){
        return view.readInt("Elige la opcion del nuevo alto\n1. Para declarar el nuevo valor del alto\n2. Para colocar el pixel de inicio y el pixel final");
    }

    public void printHeaderInfo(int[] headerInfo) {
        view.showMessage("Tipo de fichero \"BM\": " + headerInfo[0]);
        view.showMessage("Tama�o del archivo: " + headerInfo[1]);
        view.showMessage("Reservado: " + headerInfo[2]);
        view.showMessage("Reservado: " + headerInfo[3]);
        view.showMessage("Inicio de los datos de la imagen: " + headerInfo[4]);
        view.showMessage("Tama�o de la cabezara del bitmap: " + headerInfo[5]);
        view.showMessage("Anchura (pixeles): " + headerInfo[6]);
        view.showMessage("Altura (pixeles): " + headerInfo[7]);
        view.showMessage("Numero de Planos: " + headerInfo[8]);
        view.showMessage("Tama�o de cada punto: " + headerInfo[9]);
        view.showMessage("Compresion (0 = no comprimido): " + headerInfo[10]);
        view.showMessage("Tama�o de la imagen: " + headerInfo[11]);
        view.showMessage("Resolucion horizontal: " + headerInfo[12]);
        view.showMessage("Resolucion vertical: " + headerInfo[13]);
        view.showMessage("Tama�o de la table de color: " + headerInfo[14]);
        view.showMessage("Contador de colores importantes: " + headerInfo[15]);
    }

    public static void main(String[] args) {
    new Presenter().run();
    }

}
