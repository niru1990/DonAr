package Negocio;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PdfDocument createDocumentExample(){
        this.pdf = createDocument();
        this.paint = createPaint();
        PdfDocument.Page page= createPage(this.pdf);
        Canvas canvas = page.getCanvas();
        canvas.drawText(this.data.toString(), 40, 50, paint);
        pdf.finishPage(page);
        savePDF(this.pdf, this.name);
        pdf.close();
        return pdf;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PdfDocument createDocument(){
        return new PdfDocument();
    }

    public Paint createPaint(){
        return new Paint();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private PdfDocument.Page createPage(PdfDocument pdf){
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                400, 600, 1)
                .create();

        PdfDocument.Page page = pdf.startPage(pageInfo);
        return  page;
    }

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
