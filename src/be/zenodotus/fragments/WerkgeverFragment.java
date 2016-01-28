package be.zenodotus.fragments;

import be.zenodotus.databank.Personal;
import be.zenodotus.databank.PersonalDao;
import be.zenodotus.jvakantie.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WerkgeverFragment extends Fragment {
	
	private EditText txtNaam, txtAdres, txtTelefoon, txtEmail;
	private Button btnBewaren;
	private PersonalDao dao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_werkgever, container, false);
		txtNaam = (EditText) rootView.findViewById(R.id.txtWerkgever);
		txtAdres = (EditText) rootView.findViewById(R.id.txtWGAdres);
		txtTelefoon = (EditText) rootView.findViewById(R.id.txtWGTelefoon);
		txtEmail = (EditText) rootView.findViewById(R.id.txtWGEmail);
		btnBewaren = (Button) rootView.findViewById(R.id.btnWGBewaren);
		dao = new PersonalDao(rootView.getContext());
		
		dao.open();
		Personal persoonlijk = dao.getGegevens();
		dao.close();
		txtNaam.setText(persoonlijk.getWerkgever());
		txtAdres.setText(persoonlijk.getWgAdres());
		txtTelefoon.setText(persoonlijk.getWgTelefoon());
		txtEmail.setText(persoonlijk.getWgEmail());
		
		btnBewaren.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Personal persoonlijk = new Personal();
				persoonlijk.setWerkgever(txtNaam.getText().toString());
				persoonlijk.setWgAdres(txtAdres.getText().toString());
				persoonlijk.setWgTelefoon(txtTelefoon.getText().toString());
				persoonlijk.setWgEmail(txtEmail.getText().toString());
				
				dao.open();
				dao.setWerkgever(persoonlijk);
				dao.close();
				
				Toast.makeText(getActivity(), "Werkgeversgegevens bijgewerkt", Toast.LENGTH_LONG).show();
				
				
				
			}
			
		});
		
		return rootView;
	}
}
