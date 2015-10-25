package be.zenodotus.fragments;


import java.util.ArrayList;
import java.util.List;

import be.zenodotus.databank.Werkdag;
import be.zenodotus.databank.WerkdagDao;
import be.zenodotus.jvakantie.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WerkdagenFragment extends Fragment {
	
	List<TextView> txtDagen;
	List<CheckBox> chkWerkdag;
	Button btnBewaren;
	WerkdagDao dao;
	List<Werkdag> listWerkdagen;
	
	GridLayout grid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_werkdagen, container, false);
		grid = (GridLayout) rootView.findViewById(R.id.gridWerkdagen);
		txtDagen = new ArrayList<TextView>();
		chkWerkdag = new ArrayList<CheckBox>();
		btnBewaren = new Button(getActivity());
		dao = new WerkdagDao(getActivity());
		dao.open();
		listWerkdagen = dao.getAlleDagen();
		dao.close();
		maakViews();
		vulGrid();
		return rootView;
	}
	
	private void maakViews() {
		
		for(int i = 0; i < listWerkdagen.size(); i++) {
			Werkdag werkdag = listWerkdagen.get(i);
			txtDagen.add(new TextView(getActivity()));
			chkWerkdag.add(new CheckBox(getActivity()));
			txtDagen.get(i).setText(werkdag.getDag());
			if(werkdag.isWerkdag()) {
				chkWerkdag.get(i).setChecked(true);
			}
		}
		
	}
	
	private void vulGrid() {
		GridLayout.LayoutParams gridLayoutParams3 = new GridLayout.LayoutParams();
		for(int i = 0; i < txtDagen.size(); i++) {
			GridLayout.LayoutParams gridLayoutParams1 = new GridLayout.LayoutParams();
			GridLayout.LayoutParams gridLayoutParams2 = new GridLayout.LayoutParams();
			gridLayoutParams1.rowSpec = GridLayout.spec(i);
			gridLayoutParams1.columnSpec = GridLayout.spec(0);
			txtDagen.get(i).setLayoutParams(gridLayoutParams1);
			grid.addView(txtDagen.get(i));
			gridLayoutParams2.rowSpec = GridLayout.spec(i);
			gridLayoutParams2.columnSpec = GridLayout.spec(1);
			chkWerkdag.get(i).setLayoutParams(gridLayoutParams2);
			grid.addView(chkWerkdag.get(i));
		}
		btnBewaren.setText("Bewaren");
		btnBewaren.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for(int i = 0; i < listWerkdagen.size(); i++) {
					Werkdag werkdag = new Werkdag();
					werkdag.setDag(txtDagen.get(i).getText().toString());
					werkdag.setWerkdag(chkWerkdag.get(i).isChecked());
					dao.open();
					dao.updateWerkdag(werkdag);
					dao.close();
				}
				Toast.makeText(getActivity(), "Werkdagen bijgewerkt", Toast.LENGTH_LONG).show();
				
			}
			
		});
		gridLayoutParams3.rowSpec = GridLayout.spec(txtDagen.size() + 1);
		gridLayoutParams3.columnSpec = GridLayout.spec(0);
		grid.addView(btnBewaren);
	}
}
