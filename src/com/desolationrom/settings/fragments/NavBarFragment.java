package com.desolationrom.settings.fragments;
import android.app.*;
import android.os.*;
import android.view.*;
import com.desolationrom.settings.R;
import android.preference.*;

public class NavBarFragment extends PreferenceFragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		addPreferencesFromResource(R.xml.navbar_prefs);
		return super.onCreateView(inflater, container, savedInstanceState);
		//return inflater.inflate(R.layout.navbar_fragment, container, false);
	}
	
}
