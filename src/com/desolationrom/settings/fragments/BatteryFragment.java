package com.desolationrom.settings.fragments;
import android.preference.*;
import android.os.*;
import android.view.*;
import com.desolationrom.settings.R;

public class BatteryFragment extends PreferenceFragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		addPreferencesFromResource(R.xml.battery_prefs);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
