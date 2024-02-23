/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zam.generadorPdf;

import com.zam.generadorPdf.controller.PdfController;
import com.zam.generadorPdf.model.Producto;
import java.util.Arrays;
import java.util.List;

public class GeneradorPdf {

    public static void main(String[] args) {
        
        List<Producto> listaProductos = Arrays.asList(
                new Producto("Producto1", 10.99, 5),
                new Producto("Producto2", 25.50, 3),
                new Producto("Producto3", 8.75, 8),
                new Producto("Producto4", 15.30, 2),
                new Producto("Producto5", 30.00, 6)
        );
        
        PdfController pdfController = new PdfController();
        pdfController.CrearPdf(listaProductos);
    }
}
