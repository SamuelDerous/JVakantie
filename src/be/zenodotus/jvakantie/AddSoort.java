package be.zenodotus.jvakantie;

import be.zenodotus.databank.Verlofsoort;
import be.zenodotus.databank.VerlofsoortDao;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSoort extends Activity {
	
	EditText txtSoort, txtUren;
	Button btnSoort;
	Verlofsoort soort;
	VerlofsoortDao dao;

	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_addsoort);
			btnSoort = (Button)findViewById(R.id.btnToevoegenSoort);
			btnSoort.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					toevoegenSoort();
					Toast.makeText(AddSoort.this, "Nieuwe Soort toegevoegd", Toast.LENGTH_LONG).show();
					
				}
				
			});
			
	}

	private void toevoegenSoort() {
		txtSoort = (EditText) findViewById(R.id.txtSoort);
		txtUren = (EditText) findViewById(R.id.txtUren);
		soort = new Verlofsoort();
		soort.setSoort(txtSoort.getText().toString());
		soort.setUren(txtUren.getText().toString());
		dao = new VerlofsoortDao(this);
		dao.open();
		dao.toevoegenSoort(soort);
		dao.close();
		txtSoort.setText("");
		txtUren.setText("");
		
		
		
	}
	
	

}
