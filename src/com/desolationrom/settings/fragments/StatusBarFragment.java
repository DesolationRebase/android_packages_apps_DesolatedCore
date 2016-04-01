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

        Preference headerImage, clearHeaderImage, mCarrierLabel;
        EditTextPreference customLabel, qsCustomLabel;
        ListPreference numOfNotifications;

        private static final String KEY_DESO_LOGO_COLOR = "status_bar_deso_logo_color";
        private ColorPickerPreference mDesoLogoColor;
        private ColorPickerPreference mCarrierLabelColor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		addPreferencesFromResource(R.xml.status_bar_prefs);
		headerImage = findPreference("status_bar_custom_header_image");
		clearHeaderImage = findPreference("status_bar_custom_header_image_clear");

                customLabel = (EditTextPreference) findPreference("carrier_label_custom_label");
                qsCustomLabel = (EditTextPreference) findPreference("qs_carrier_label_custom_label");
                numOfNotifications = (ListPreference) findPreference("carrier_label_number_of_notification_icons");
                mCarrierLabelColor = (ColorPickerPreference) findPreference("carrier_label_color");
                mCarrierLabelColor.setOnPreferenceChangeListener(this);

                headerImage.setOnPreferenceClickListener(this);
                clearHeaderImage.setOnPreferenceClickListener(this);

                mDesoLogoColor = (ColorPickerPreference) findPreference(KEY_DESO_LOGO_COLOR);
                mDesoLogoColor.setOnPreferenceChangeListener(this);
                int intColor = Settings.System.getInt(getContext().getContentResolver(), Settings.System.STATUS_BAR_DESO_LOGO_COLOR, 0xffffffff);
                String hexColor = String.format("#%08x", (0xffffffff & intColor));
                mDesoLogoColor.setSummary(hexColor);
                mDesoLogoColor.setNewPreviewColor(intColor);

                numOfNotifications.setValue("" +Settings.System.getInt(getContext().getContentResolver(), Settings.System.STATUS_BAR_CARRIER_LABEL_NUMBER_OF_NOTIFICATION_ICONS, 5));
                numOfNotifications.setSummary("" + Settings.System.getInt(getContext().getContentResolver(), Settings.System.STATUS_BAR_CARRIER_LABEL_NUMBER_OF_NOTIFICATION_ICONS, 5));
                numOfNotifications.setOnPreferenceChangeListener(this);

                int intTwoColor = Settings.System.getInt(getContext().getContentResolver(), Settings.System.STATUS_BAR_CARRIER_LABEL_COLOR, 0xffffffff);
                String hexTwoColor = String.format("#%08x", (0xffffffff & intTwoColor));
                mCarrierLabelColor.setSummary(hexTwoColor);
                mCarrierLabelColor.setNewPreviewColor(intTwoColor);

                customLabel.setOnPreferenceChangeListener(this);
                qsCustomLabel.setOnPreferenceChangeListener(this);
                String customLabelText = Settings.System.getString(getContext().getContentResolver(),
                    Settings.System.STATUS_BAR_CARRIER_LABEL_CUSTOM_LABEL);
                String customLabelDefaultSummary = getResources().getString(
                    com.android.internal.R.string.default_custom_label);
                if (customLabelText == null) {
                    customLabelText = "";
                }
                customLabel.setText(customLabelText);
                customLabel.setSummary(customLabelText.isEmpty() ? customLabelDefaultSummary : customLabelText);

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
            ContentResolver mResolver = getContext().getContentResolver();
            if (preference == mDesoLogoColor) {
                String hex = ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue)));
                preference.setSummary(hex);
                int intHex = ColorPickerPreference.convertToColorInt(hex);
                Settings.System.putInt(mResolver,
                        Settings.System.STATUS_BAR_DESO_LOGO_COLOR, intHex);
            } else if(preference == mCarrierLabelColor) {
                String hex = ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue)));
                preference.setSummary(hex);
                int intHex = ColorPickerPreference.convertToColorInt(hex);
                Settings.System.putInt(mResolver,
                        Settings.System.STATUS_BAR_CARRIER_LABEL_COLOR, intHex);
            } else if (preference == numOfNotifications) {
                int intValue = Integer.valueOf((String) newValue);
                int index = numOfNotifications.findIndexOfValue((String) newValue);
                Settings.System.putInt(mResolver,
                        Settings.System.STATUS_BAR_CARRIER_LABEL_NUMBER_OF_NOTIFICATION_ICONS, intValue);
                preference.setSummary(numOfNotifications.getEntries()[index]);
            } else if(preference == customLabel) {
                String label = (String) newValue;
                Settings.System.putString(mResolver,
                    Settings.System.STATUS_BAR_CARRIER_LABEL_CUSTOM_LABEL, label);

                String customLabelText = Settings.System.getString(mResolver,
                    Settings.System.STATUS_BAR_CARRIER_LABEL_CUSTOM_LABEL);
                String customLabelDefaultSummary = getResources().getString(
                    com.android.internal.R.string.default_custom_label);
                if (customLabelText == null) {
                    customLabelText = "";
                }
                customLabel.setText(customLabelText);
                customLabel.setSummary(customLabelText.isEmpty() ? customLabelDefaultSummary : customLabelText);

            } else if(preference == qsCustomLabel) {
                String label = (String) newValue;
                Settings.System.putString(mResolver,
                    Settings.System.CUSTOM_QS_CARRIER_NAME, label);
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
