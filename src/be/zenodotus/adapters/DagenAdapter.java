package be.zenodotus.adapters;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import be.zenodotus.data.KalenderItems;
import be.zenodotus.data.Rekenen;
import be.zenodotus.data.Totalen;
import be.zenodotus.databank.Feestdag;
import be.zenodotus.databank.FeestdagDao;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import be.zenodotus.databank.Verlofsoort;
import be.zenodotus.databank.VerlofsoortDao;
import be.zenodotus.databank.Werkdag;
import be.zenodotus.databank.WerkdagDao;
import be.zenodotus.jvakantie.AddActivity;
import be.zenodotus.jvakantie.MainActivity;
import be.zenodotus.jvakantie.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DagenAdapter<T> extends ArrayAdapter<KalenderItems> {
    private Context context;
    private int[] dagen;
    private FeestdagDao dao;
    private List<Feestdag> feestdagen;
    private ArrayList<KalenderItems> items;
    private int jaar;
    private GregorianCalendar kal;
    private int maand;
    private GregorianCalendar nu;
    private int resource;
    private String[] weekdagen;
    private List<Werkdag> weekend;

    /* renamed from: be.zenodotus.adapters.DagenAdapter.1 */
    
    class C00821 implements OnClickListener {
        private final /* synthetic */ int val$position;

        C00821(int i) {
            this.val$position = i;
        }

        public void onClick(View v) {
            int i;
            VerlofDao verlofDao = new VerlofDao(DagenAdapter.this.context);
            verlofDao.open();
            for (i = 0; i < DagenAdapter.this.items.get(this.val$position).getVerlof().size(); i++) {
                verlofDao.verwijderenVerlof((Verlof) DagenAdapter.this.items.get(this.val$position).getVerlof().get(i));
            }
            verlofDao.close();
            int grootte = DagenAdapter.this.items.get(this.val$position).getVerlof().size();
            for (i = 0; i < grootte; i++) {
                DagenAdapter.this.items.get(this.val$position).getVerlof().remove(0);
            }
            DagenAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: be.zenodotus.adapters.DagenAdapter.2 */
    class C00832 implements OnClickListener {
        private final /* synthetic */ int val$position;
        int positie = this.val$position;
        ArrayList<KalenderItems> lijsten;
        DagenAdapter<KalenderItems> adapter;
        C00832(int i, ArrayList<KalenderItems> lijsten) {
            this.val$position = i;
            this.lijsten = lijsten;
            
            
        }

        public void onClick(View v) {
            boolean weekenddag = false;
            WerkdagDao werkdagDao = new WerkdagDao(DagenAdapter.this.context);
            werkdagDao.open();
            List<Werkdag> weekend = werkdagDao.getWeekend();
            for (int i = 0; i < weekend.size(); i++) {
                if (((Werkdag) weekend.get(i)).getDag().equalsIgnoreCase(DagenAdapter.this.items.get(this.val$position).getWeekdag())) {
                    weekenddag = true;
                }
            }
            if (!weekenddag) {
            	Intent addIntent = new Intent(DagenAdapter.this.context, AddActivity.class);
                addIntent.putExtra("JAAR", DagenAdapter.this.jaar);
                addIntent.putExtra("MAAND", DagenAdapter.this.maand);
                addIntent.putExtra("DAG", this.val$position);
                DagenAdapter.this.context.startActivity(addIntent);	
            }
        }
    }
    
    
    

    public DagenAdapter(Context context, int resource, ArrayList<KalenderItems> items, int maand, int jaar) {
        super(context, resource, items);
        this.dagen = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        this.weekdagen = new String[]{"ZO", "ZO", "MA", "DI", "WO", "DO", "VR", "ZA"};
        this.context = context;
        this.kal = new GregorianCalendar();
        this.items = items;
        this.resource = resource;
        this.maand = maand;
        this.jaar = jaar;
    }

    
    
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtDag;
        TextView txtWeekdag;
        TextView txtVerlof;
        ImageButton btnVerlof;
        int i;
        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            txtDag = (TextView) convertView.findViewById(R.id.txtKalenderdag);
            txtWeekdag = (TextView) convertView.findViewById(R.id.txtWeekdag);
            txtVerlof = (TextView) convertView.findViewById(R.id.txtVerlof);
            btnVerlof = (ImageButton) convertView.findViewById(R.id.btnVerlof);
        } else {
            txtDag = (TextView) convertView.findViewById(R.id.txtKalenderdag);
            txtWeekdag = (TextView) convertView.findViewById(R.id.txtWeekdag);
            txtVerlof = (TextView) convertView.findViewById(R.id.txtVerlof);
            btnVerlof = (ImageButton) convertView.findViewById(R.id.btnVerlof);
        }
        WerkdagDao werkdagDao = new WerkdagDao(this.context);
        werkdagDao.open();
        List<Werkdag> weekend = werkdagDao.getWeekend();
        boolean isWeekend = false;
        txtVerlof.setText("");
        for (i = 0; i < weekend.size(); i++) {
            if (this.items.get(position).getWeekdag().equalsIgnoreCase(((Werkdag) weekend.get(i)).getDag())) {
                isWeekend = true;
            }
        }
        if (isWeekend) {
            convertView.setBackgroundColor(-3355444);
        } else if (this.items.get(position).isFeestdag()) {
            convertView.setBackgroundColor(-16711936);
            txtVerlof.append("Feestdag \n");
        } else {
            convertView.setBackgroundColor(-1);
        }
        txtDag.setText(String.valueOf(items.get(position).getDag())); 
        txtWeekdag.setText(this.items.get(position).getWeekdag());
        for (i = 0; i < this.items.get(position).getVerlof().size(); i++) {
            txtVerlof.append(new StringBuilder(String.valueOf(((Verlof) this.items.get(position).getVerlof().get(i)).getVerlofsoort())).append(" ").append(((Verlof) this.items.get(position).getVerlof().get(i)).getUrental()).append("\n").toString());
        }
        if (this.items.get(position).getVerlof().size() == 0) {
            btnVerlof.setVisibility(100);
        } else {
            btnVerlof.setVisibility(0);
        }
        btnVerlof.setOnClickListener(new C00821(position));
        convertView.setOnClickListener(new C00832(position, items));
        if (this.kal.get(1) == this.jaar && this.kal.get(2) == this.maand && position == this.kal.get(5) - 1) {
            convertView.setBackgroundColor(-256);
        }
        return convertView;
    }
}