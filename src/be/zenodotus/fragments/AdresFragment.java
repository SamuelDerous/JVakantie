package be.zenodotus.fragments;

import be.zenodotus.databank.Personal;
import be.zenodotus.databank.PersonalDao;
import be.zenodotus.jvakantie.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdresFragment extends Fragment {

	private EditText txtNaam, txtAdres, txtTelefoon, txtEmail;
	private Button btnBewaren;
	private PersonalDao dao;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_adresgegevens, container, false);
		txtNaam = (EditText) rootView.findViewById(R.id.txtNaam);
		txtAdres = (EditText) rootView.findViewById(R.id.txtAdres);
		txtTelefoon = (EditText) rootView.findViewById(R.id.txtTelefoon);
		txtEmail = (EditText) rootView.findViewById(R.id.txtEmail);
		btnBewaren = (Button) rootView.findViewById(R.id.btnBewaren);
		dao = new PersonalDao(rootView.getContext());
		
		dao.open();
		Personal persoonlijk = dao.getGegevens();
		dao.close();
		txtNaam.setText(persoonlijk.getNaam());
		txtAdres.setText(persoonlijk.getAdres());
		txtTelefoon.setText(persoonlijk.getTelefoon());
		txtEmail.setText(persoonlijk.getTelefoon());
		
		btnBewaren.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Personal persoonlijk = new Personal();
				persoonlijk.setNaam(txtNaam.getText().toString());
				persoonlijk.setAdres(txtAdres.getText().toString());
				persoonlijk.setTelefoon(txtTelefoon.getText().toString());
				persoonlijk.setEmail(txtEmail.getText().toString());
				
				dao.open();
				dao.setNAW(persoonlijk);
				dao.close();
				
				Toast.makeText(getActivity(), "Persoonlijke gegevens bijgewerkt", Toast.LENGTH_LONG).show();
				
				
				
			}
			
		});
		return rootView;
	}
}
