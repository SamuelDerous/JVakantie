/**
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Deze klasse wordt gebruikt om een pdf te genereren; gebruik makend van de
 * bibliotheek itext
 */
package be.zenodotus.creatie;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import be.zenodotus.databank.Feestdag;
import be.zenodotus.databank.FeestdagDao;
//import be.zenodotus.databank.Gegevens;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import be.zenodotus.databank.Werkdag;
import be.zenodotus.databank.WerkdagDao;







//import bewerkingen.Initialisatie;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.itextpdf.text.*;

import java.io.File;
//import gui.dlgsFout.DlgFout;
//import java.awt.Color;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author zenodotus
 */
public class GeneratePDF {

    private static volatile GeneratePDF instance = null;
    private Context context;

    public String vakantieAfdruk(Context context, String name, int jaar) {
    	this.context = context;
        PdfWriter w = null;
        Document d = new Document(PageSize.A4.rotate(), 5, 5, 10, 10);
        d.setPageCount(3);
        String fileName = name;
        String file = name;
        GregorianCalendar datum = new GregorianCalendar();
        datum.set(GregorianCalendar.YEAR, jaar);
        
        String[] maanden = {"Januari", "Februari", "Maart", "April", "Mei",
            "Juni", "Juli", "Augustus", "September", "Oktober", "November",
            "December"};
        int[] dagen = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        VerlofDao dao = new VerlofDao(context);
        FeestdagDao feestdagDao = new FeestdagDao(context);
        WerkdagDao werkdagDao = new WerkdagDao(context);
        File folder = new File(context.getFilesDir(), "pdfs");
        folder.mkdirs();
        if(datum.isLeapYear(jaar)) {
        	dagen[1] = 29;
        }
        File temp = null;
        temp = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Jacqueline" + jaar + ".pdf");
 	    fileName = temp.toString();
        try {
 	    	dao.open();
 	    	ArrayList<Verlof> verloflijst = dao.getAlleVerlovenPerJaar(jaar);
            w = PdfWriter.getInstance(d, new FileOutputStream(temp));
            d.open();
            d.addAuthor("Jacqueline Vandenbroecke");
            d.addCreationDate();
            d.addCreator("Verlofplanner");
            d.addTitle("Vakantie " + jaar + " van Jacqueline Vandenbroecke");
            Font standaard = FontFactory.getFont(FontFactory.HELVETICA, 8);
            Font standaardBold = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
            Paragraph gegeven = new Paragraph("Jacqueline Vandenbroecke Verlof " + jaar + "\n", standaardBold);
            gegeven.setAlignment(Paragraph.ALIGN_CENTER);
            d.add(gegeven);
            
            for (int paginas = 0; paginas < 2; paginas++) {
                int aantal = 0;
                if (paginas == 1) {
                    d.newPage();
                    aantal = 6;
                }
                PdfPTable table = new PdfPTable(6);
                for (int i = aantal; i < (aantal + 6); i++) {
                    PdfPCell cell = new PdfPCell(new Paragraph(maanden[i], standaardBold));
                    cell.setBorder(1);
                    table.addCell(cell);
                }
                int dag = 1;
                int k = aantal;
                for (int i = aantal; i < (aantal + 6); i++) {
                    for (int j = 0; j < 32; j++) {
                        if (k > ((aantal + 6) - 1)) {
                            k = aantal;
                            dag++;
                        }
                        if (dag > dagen[k]) {
                            PdfPCell cell = new PdfPCell(new Paragraph("", standaard));
                            table.addCell(cell);
                            k++;
                        } else {
                            SimpleDateFormat formatterDag = new SimpleDateFormat("dd");
                            SimpleDateFormat formatterWeek = new SimpleDateFormat("EEE");
                            datum.set(jaar, k, dag);
                            PdfPTable dagTabel = new PdfPTable(4);
                            PdfPCell cellDag = new PdfPCell(new Paragraph(formatterDag.format(datum.getTime()), standaard));
                            PdfPCell cellWeek = new PdfPCell(new Paragraph(formatterWeek.format(datum.getTime()), standaard));
                          	ArrayList<Verlof> verlof = new ArrayList<Verlof>();
                            for(int z = 0; z < verloflijst.size(); z++) {
                    			if (((verloflijst.get(z).getDag() + 1) == dag) && (verloflijst.get(z).getMaand() == k)) {
                    				verlof.add(verloflijst.get(z));
                    				
                    			}
                    		}
                            feestdagDao.open();
                            Feestdag feestdag = feestdagDao.getFeestdag(jaar, datum.get(GregorianCalendar.MONTH), datum.get(GregorianCalendar.DATE));
                            feestdagDao.close();
                            werkdagDao.open();
                            java.util.List<Werkdag> weekend = werkdagDao.getWeekend();
                            werkdagDao.close();
                            String Verlof = "";
                            String uur = "";
                            if (verlof.size() > 0) {
                                if (verlof.size() > 1) {
                                    Verlof = verlof.get(0).getVerlofsoort() + "\n" + verlof.get(1).getVerlofsoort();
                                    uur = verlof.get(0).getUrental() + "\n" + verlof.get(1).getUrental();
                                } else {
                                    Verlof = verlof.get(0).getVerlofsoort();
                                    uur = verlof.get(0).getUrental();
                                }

                            }
                            PdfPCell cellVerlof = new PdfPCell(new Paragraph(Verlof, standaard));
                            PdfPCell uren = new PdfPCell(new Paragraph(uur, standaard));
                            if (verlof.size() > 0) {
                                BaseColor kleur = new BaseColor(Color.GRAY);
                                cellVerlof.setBackgroundColor(kleur);
                                uren.setBackgroundColor(kleur);
                                cellDag.setBackgroundColor(kleur);
                                cellWeek.setBackgroundColor(kleur);
                            }
                            for(int z = 0; z < weekend.size(); z++) {
                                if ((formatterWeek.format(datum.getTime())).equals(weekend.get(z).getDag())) {
                                    BaseColor kleur = new BaseColor(Color.LTGRAY);
                                    cellVerlof.setBackgroundColor(kleur);
                                    uren.setBackgroundColor(kleur);
                                    cellDag.setBackgroundColor(kleur);
                                    cellWeek.setBackgroundColor(kleur);
                                }
                            }
                             if(feestdag != null) {
                                BaseColor kleur = new BaseColor(Color.GREEN);
                                uren.setBackgroundColor(kleur);
                                cellVerlof.setBackgroundColor(kleur);
                                uren.setBackgroundColor(kleur);
                                cellDag.setBackgroundColor(kleur);
                                cellWeek.setBackgroundColor(kleur);
                            }
                            dagTabel.addCell(cellDag);
                            dagTabel.addCell(cellWeek);
                            dagTabel.addCell(cellVerlof);
                            dagTabel.addCell(uren);
                            table.addCell(dagTabel);
                            k++;
                        }
                    }

                }
                d.add(table);
                dao.close();
            }
        } catch (Exception ex) {
        	
        	ex.printStackTrace();
            
        } finally {
            d.close();
            w.close();
        }
        return fileName;
    }
    
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

  
    
   
}

