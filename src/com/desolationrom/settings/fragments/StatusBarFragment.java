package com.desolationrom.settings.fragments;
import android.preference.*;
import android.view.*;
import com.desolationrom.settings.R;
import android.os.*;

public class StatusBarFragment extends PreferenceFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		addPreferencesFromResource(R.xml.status_bar_prefs);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
