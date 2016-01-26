package com.desolationrom.settings.fragments;
import android.preference.*;
import android.view.*;
import com.desolationrom.settings.R;
import android.provider.Settings;
import android.app.*;
import android.os.*;
import android.content.*;
import android.net.Uri;

public class StatusBarFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

        Preference headerImage, clearHeaderImage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		addPreferencesFromResource(R.xml.status_bar_prefs);
		headerImage = findPreference("status_bar_custom_header_image");
		clearHeaderImage = findPreference("status_bar_custom_header_image_clear");

                headerImage.setOnPreferenceClickListener(this);
                clearHeaderImage.setOnPreferenceClickListener(this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if(preference.equals(headerImage)) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
            } else if(preference.equals(clearHeaderImage)) {
                Settings.System.putString(getContext().getContentResolver(), headerImage.getKey(), "");
            }
            return true;
        }

        public final static int REQ_CODE_PICK_IMAGE = 1;

	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

            switch(requestCode) {
                case REQ_CODE_PICK_IMAGE:
                    if(resultCode == Activity.RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        Settings.System.putString(getContext().getContentResolver(), headerImage.getKey(), selectedImage.toString());
                    }
            }
        }
}
