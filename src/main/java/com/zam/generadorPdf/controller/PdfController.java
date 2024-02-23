/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zam.generadorPdf.controller;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.zam.generadorPdf.model.Producto;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class PdfController {
    
    public String CrearPdf(List<Producto> listaProductos) {
        PdfWriter writer = null;
        try {
            //Ubicacion del archivo.
            File file = new File("src/resources/PDFs/000002103.pdf");

            //Se instancia un objeto de tipo Pdfwriter que permitira escribir en el documento
            writer = new PdfWriter(file);

            //Creamos un objeto de tipo PdfDocument que representara el documento
            PdfDocument docPdf = new PdfDocument(writer);
               
            //Del objeto docPdf agregamos un evento de tipo START_PAGE que permitira que en todas las paginas se cree un encabezado
            docPdf.addEventHandler(PdfDocumentEvent.START_PAGE, new IEventHandler() {
                @Override
                public void handleEvent(Event event) {
                    try {
                        //Se crea un PdfDocumentEvent para el manejo de eventos en un documento pdf
                        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                        PdfDocument docPdf = docEvent.getDocument();
                        PdfPage page = docEvent.getPage();
                        
                        //Creamos un canvas que servira para poder crear el encabezado
                        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), docPdf);
                        
                        /*La constante PdfEncodings.IDENTITY_H se utiliza para especificar la codificación de la fuente y el valor booleano 
                        true se utiliza para especificar que la fuente se incrustará en el documento.*/
                        PdfFont font =  PdfFontFactory.createFont("src/resources/Fonts/Helvetica-Bold-Font.ttf", PdfEncodings.IDENTITY_H, true);
                        
                        //Creamos la primera parte del encabezado
                        canvas.beginText().setFontAndSize(font, 14)
                                .moveText(50, 806)
                                .showText("TIENDA ZAM")
                                .endText();
                        //Creamos la segunda parte del encabezado
                        canvas.beginText().setFontAndSize(font, 14)
                                .moveText(365, 806)
                                .showText("COMPROBANTE DE PAGO")
                                .endText();
                        //Creamos la linea
                        canvas.beginText()
                                .moveTo(50, 800)
                                .lineTo(page.getPageSize().getWidth() - 50, 800)
                                .setLineWidth(2)
                                .stroke()
                                .endText();
                    } catch (IOException ex) {
                        System.out.println("Error: "+ex.getMessage());
                    }
                }

            });

            //Document se encargara de escribir en el documento PDF
            try ( Document documento = new Document(docPdf);) {
                Image imagen = null;
                try {
                    imagen = new Image(ImageDataFactory.create("src/resources/Imgs/logo_negro.png"));
                    imagen.scaleAbsolute(60, 60);
                    imagen.setHorizontalAlignment(HorizontalAlignment.CENTER);
                } catch (MalformedURLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
                
                Paragraph p1 = new Paragraph();
                p1.add(new Text("Ruc: 10957829456\n"));
                p1.add(new Text("Nombre: ZamStore\n"));
                p1.add(new Text("Telefono: 903712983\n"));
                p1.add(new Text("Direccion: Miraflores\n"));
                p1.add(new Text("Razon Social: Izquierda\n"));
                
                        
                Paragraph p2 = new Paragraph("Codigo de venta: 000002103"
                        + "Fecha: "+"23 de febrero del 2024");
                
                
                Table table = new Table(3);
                table.addCell(new Cell().add(imagen).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setWidth(180));
                table.addCell(new Cell().add(p1).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Cell().add(p2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                table.useAllAvailableWidth();
                table.setAutoLayout();
                
                Table tablaCliente = new Table(4);
                Color color = ColorConstants.BLUE;
                Color fondo = ColorConstants.GREEN;
                tablaCliente.addCell(new Cell().add(new Paragraph("Dni/Ruc").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Nombre").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Telefono").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Direccion").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.setHorizontalAlignment(HorizontalAlignment.CENTER);
                tablaCliente.useAllAvailableWidth();
                tablaCliente.setAutoLayout();
                
                Table datosCliente = new Table(4);
                tablaCliente.addCell(new Cell().add(new Paragraph("75897252")).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Jose Zambrano")).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("963507712")).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Av. La estancia")).setBorder(Border.NO_BORDER));
                datosCliente.setHorizontalAlignment(HorizontalAlignment.CENTER);
                datosCliente.useAllAvailableWidth();
                datosCliente.setAutoLayout();
                
                Table productos = new Table(4);
                Color colorFondo = ColorConstants.LIGHT_GRAY;
                productos.addCell(new Cell().add(new Paragraph("Descripcion").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setWidth(250).setBackgroundColor(colorFondo));
                productos.addCell(new Cell().add(new Paragraph("Cantidad").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setBackgroundColor(colorFondo));
                productos.addCell(new Cell().add(new Paragraph("Precio U").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setBackgroundColor(colorFondo));
                productos.addCell(new Cell().add(new Paragraph("Precio T").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setBackgroundColor(colorFondo));
                productos.setHorizontalAlignment(HorizontalAlignment.CENTER);
                productos.useAllAvailableWidth();
                productos.setAutoLayout();
                
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(table);
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Datos del cliente: ").setBold());
                documento.add(new Paragraph(" "));
                documento.add(tablaCliente);
                documento.add(datosCliente);
                documento.add(new Paragraph(" "));
                documento.add(productos);
                
                double total = 0.0;
                for (int i=0; i<listaProductos.size(); i++) {
                    double precio_cantidad = listaProductos.get(i).getPrecio()*listaProductos.get(i).getCantidad();
                    total = precio_cantidad + total;                    
                    Table pro = new Table(4);
                    pro.addCell(new Cell().add(new Paragraph(listaProductos.get(i).getNombre())).setBorder(Border.NO_BORDER).setWidth(250));
                    pro.addCell(new Cell().add(new Paragraph(String.valueOf(listaProductos.get(i).getCantidad()+" u"))).setWidth(88).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/"+String.valueOf(listaProductos.get(i).getPrecio()))).setWidth(85).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/"+String.format("%.2f",(listaProductos.get(i).getPrecio()*listaProductos.get(i).getCantidad())))).setBorder(Border.NO_BORDER));
                    pro.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    pro.useAllAvailableWidth();
                    pro.setAutoLayout();
                    documento.add(pro);
                }
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Total a pagar: S/"+String.format("%.2f", total)).setTextAlignment(TextAlignment.RIGHT));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Cancelacion y firma").setTextAlignment(TextAlignment.CENTER));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("--------------------").setTextAlignment(TextAlignment.CENTER));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Gracias por su compra").setTextAlignment(TextAlignment.CENTER));
                documento.close();
                docPdf.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        String nombre = "000002103.pdf";
        this.abrirPdf(nombre);
        return nombre;
    }
    
    public void abrirPdf(String nombre) {
        File pdf = new File("src/resources/PDFs/"+nombre);
        if (pdf.exists()) {
            try {
                Desktop.getDesktop().open(pdf);
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        } else {
            System.out.println("El archivo no existe");
        }
    }
    
}
