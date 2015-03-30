package com.desolationrom.settings.fragments;
import android.preference.*;
import android.view.*;
import com.desolationrom.settings.R;
import android.provider.Settings;
import android.app.*;
import android.os.*;
import android.content.*;
import android.net.Uri;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

public class StatusBarFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        Preference headerImage, clearHeaderImage;

        private static final String KEY_DESO_LOGO_COLOR = "status_bar_deso_logo_color";
        private ColorPickerPreference mDesoLogoColor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		addPreferencesFromResource(R.xml.status_bar_prefs);
		headerImage = findPreference("status_bar_custom_header_image");
		clearHeaderImage = findPreference("status_bar_custom_header_image_clear");

                headerImage.setOnPreferenceClickListener(this);
                clearHeaderImage.setOnPreferenceClickListener(this);

                mDesoLogoColor = (ColorPickerPreference) findPreference(KEY_DESO_LOGO_COLOR);
                mDesoLogoColor.setOnPreferenceChangeListener(this);
                int intColor = Settings.System.getInt(getContext().getContentResolver(), Settings.System.STATUS_BAR_DESO_LOGO_COLOR, 0xffffffff);
                String hexColor = String.format("#%08x", (0xffffffff & intColor));
                mDesoLogoColor.setSummary(hexColor);
                mDesoLogoColor.setNewPreviewColor(intColor);

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

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference == mDesoLogoColor) {
                String hex = ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue)));
                preference.setSummary(hex);
                int intHex = ColorPickerPreference.convertToColorInt(hex);
                Settings.System.putInt(getContext().getContentResolver(),
                        Settings.System.STATUS_BAR_DESO_LOGO_COLOR, intHex);
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
