package be.zenodotus.adapters;

import be.zenodotus.fragments.AdresFragment;
import be.zenodotus.fragments.WerkdagenFragment;
import be.zenodotus.fragments.WerkgeverFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new WerkdagenFragment();
			
		case 1:
			return new AdresFragment();
			
		case 2:
			return new WerkgeverFragment();
		}
		
		return null;
	}

	@Override
	public int getCount() {
		
		return 3;
	}

}
