package Negocio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportesPDF<T> {

    private PdfDocument pdf;
    private Paint paint;
    private String name;
    private T data;

    public ReportesPDF(String name, T data){
        this.name = name;
        this.data = data;
    }

    /**
     * Generación de un PDF de prueba que consta de un texto
     * pasado por parametro en el constructor y con el nombre informado
     * mas fecha.
     * @return PdfDocument
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PdfDocument createDocumentExample(){
        this.pdf = createDocument();
        this.paint = createPaint();
        PdfDocument.Page page= createPage(this.pdf, 400, 600, 1);
        paintOnACanvas(page, 40, 50);
        savePDF(this.pdf, this.name);
        pdf.close();
        return pdf;
    }

    /**
     * Devuelve un PdfDocument vacio
     * @return PdfDocumet
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PdfDocument createDocument(){
        return new PdfDocument();
    }

    /**
     * Devuelve un Paint para trabajar con el PDF
     * @return Paint
     */
    public Paint createPaint(){
        return new Paint();
    }

    /**
     * Permite la configuración la posición del texo
     * @param position
     */
    public void configPositionPaint(@NotNull String position){
        switch (position)
        {
            case "centro":
                this.paint.setTextAlign(Paint.Align.CENTER);
            break;
            case "derecha":
                this.paint.setTextAlign(Paint.Align.RIGHT);
                break;
            case "izquierda":
                this.paint.setTextAlign(Paint.Align.LEFT);
                break;
            default:
        }
    }

    /**
     * Permite la configuración del color del texot
     * @param red
     * @param green
     * @param blue
     */
    public void configColorPaint(int red, int green,int blue){
        this.paint.setColor(Color.rgb(red, green, blue));
    }

    /**
     * Permite configurar el tamaño del texto
     * @param tamaño
     */
    public void configSizeTextPaint(float tamaño){
        this.paint.setTextSize(tamaño);
    }

    /**
     * Recibe string con nombre del tipo de linea
     *  stroke, fill, fas
     * @param tipo
     */
    public void configTipoLineaPaint(@NotNull String tipo){
        switch (tipo.toLowerCase()) {
            case "stroke":
                this.paint.setStyle(Paint.Style.STROKE);
                this.paint.setStrokeWidth(2);
                break;
            case "fill":
                this.paint.setStyle(Paint.Style.FILL);
                break;
            case "fas":
                this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
        }
    }

    /**
     * Crea linea vertical en canvas
     * @param canvas
     * @param posicions
     */
    public void lineaEnCanvas(@NotNull Canvas canvas, @NotNull int[] posicions){
        //xStart, yStart, xStop, yStop, paint
        canvas.drawLine(posicions[0], posicions[1], posicions[2], posicions[3], paint);
    }

    /**
     * Permite colocar una imagen en el canvas que luego se vera en el PDF.
     * @param canvas
     * @param bmp
     * @param left
     * @param top
     */
    public void imagenCanvas(@NotNull Canvas canvas, Bitmap bmp, int left, int top){
        canvas.drawBitmap(bmp, left, top, this.paint);
    }

    /**
     * Crea la pagina para el documento,
     * setea alto, ancho y número de pagina
     * @param pdf
     * @param alto
     * @param ancho
     * @param numero
     * @return Page
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private PdfDocument.Page createPage(@NotNull PdfDocument pdf, int alto, int ancho, int numero){
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                alto, ancho, numero)
                .create();

        PdfDocument.Page page = pdf.startPage(pageInfo);
        return  page;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void paintOnACanvas(@NotNull PdfDocument.Page page, int X, int Y){
        Canvas canvas = page.getCanvas();
        canvas.drawText(this.data.toString(), X, Y, this.paint);
        pdf.finishPage(page);
    }

    /**
     * guarda el archivo.
     * @param pdf
     * @param name
     * @return boolean
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean savePDF(PdfDocument pdf, String name){
        boolean response = false;
        File f = new File(Environment.getExternalStorageDirectory(), "/" + name +".pdf");
        try
        {
            pdf.writeTo(new FileOutputStream(f));
            response = true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            response = false;
        }
        finally {
            return  response;
        }
    }

}
