package com.desolationrom.settings.fragments;
import android.app.*;
import android.os.*;
import android.view.*;
import com.desolationrom.settings.R;

public class DefaultFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.default_fragment, container, false);
	}
	
}
