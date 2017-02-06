package be.zenodotus.data;

import be.zenodotus.databank.Feestdag;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author zenodotus
 */
public class BWBelgischeFeestdagen {
    
    private int jaar;
    private GregorianCalendar pasen;
    private List<Feestdag> feestdagen = new ArrayList<Feestdag>();
    
    public BWBelgischeFeestdagen(int jaar) {
        this.jaar = jaar;
        feestdagen.clear();
        //Feestdag feestdag = new Feestdag();
        //feestdag.setFeestdag("Pasen");
        pasen = berekening();
        //feestdag.setDatum(pasen);
        setPasen();
        setAllerheiligen();
        setPinksteren();
        setArbeid();
        setHemelvaart();
        setKerstmis();
        setLieveVrouw();
        setNationaal();
        setNieuwjaar();
        setWapenstilstand();
    }
    
   public final GregorianCalendar berekening() {
        GregorianCalendar cal = new GregorianCalendar();
        int a = (jaar % 19);
        int b= jaar / 100;
        int c = jaar % 100;
        int d = b / 4;
        int e = b % 4;
        int g = (8 * b + 13) / 25;
        int h = (11 * (b - d - g) - 4) / 30;
        int j = (7 * a + h + 6) / 11;
        int k = (19 * a + (b - d -g) + 15 - j) % 29;
        int i = c / 4;
        int l = c % 4;
        int m = ((32 + 2 * e) + 2 * i - l - k) % 7;
        int n = ((90 + (k + m)) / 25) - 1;
        int p = ((19 + (k + m) + n) % 32) + 2;
        cal.set(jaar, n, p);
        
        return cal;
        
                
    }
   
   public final void setPasen()
   {
       GregorianCalendar cal = new GregorianCalendar();
       cal.set(pasen.get(GregorianCalendar.YEAR),
               pasen.get(GregorianCalendar.MONTH),
               pasen.get(GregorianCalendar.DATE));
       Feestdag feestdag = new Feestdag();
       feestdag.setFeestdag("Pasen");
       feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
       feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
       feestdag.setDag(cal.get(GregorianCalendar.DATE));
       feestdagen.add(feestdag);
       
   }
    
    public final void setPinksteren() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Pinksteren");
        cal.set(pasen.get(GregorianCalendar.YEAR),
               pasen.get(GregorianCalendar.MONTH),
               pasen.get(GregorianCalendar.DATE));
        cal.add(GregorianCalendar.DAY_OF_YEAR, 49);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    
    public final void setHemelvaart() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Onze lieve heer hemelvaart");
        cal.set(pasen.get(GregorianCalendar.YEAR),
               pasen.get(GregorianCalendar.MONTH),
               pasen.get(GregorianCalendar.DATE));
        cal.add(GregorianCalendar.DAY_OF_YEAR, 38);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    
    public final void setNieuwjaar() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Nieuwjaar");
        cal.set(jaar, 0, 1);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
    }
    
    public final void setKerstmis() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Kerstmis");
        cal.set(jaar, 11, 25);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    
    public final void setLieveVrouw() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag =new Feestdag();
        feestdag.setFeestdag("Onze lieve vrouw hemelvaart");
        cal.set(jaar, 7, 15);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    
    public final void setNationaal() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Nationale Feestdag");
        cal.set(jaar, 6, 21);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    
    public final void setAllerheiligen() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Allerheiligen");
        cal.set(jaar, 10, 1);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    public final void setWapenstilstand() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Wapenstilstand");
        
        cal.set(jaar, 10, 11);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
       
    }
    
    public final void setArbeid() {
        GregorianCalendar cal = new GregorianCalendar();
        Feestdag feestdag = new Feestdag();
        feestdag.setFeestdag("Dag van de arbeid");
        cal.set(jaar, 4, 1);
        feestdag.setJaar(cal.get(GregorianCalendar.YEAR));
        feestdag.setMaand(cal.get(GregorianCalendar.MONTH));
        feestdag.setDag(cal.get(GregorianCalendar.DATE));
        feestdagen.add(feestdag);
        
    }
    
    public int getJaar() {
        return jaar;
    }
    
    public List<Feestdag> getFeestdagen() {
        return feestdagen;
    }
    
    public static void main(String[] args) {
        BWBelgischeFeestdagen feesten = new BWBelgischeFeestdagen(2017);
        List<Feestdag> feest = feesten.getFeestdagen();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(feest.get(0).getFeestdag() + " ");
        
    }
  

}
