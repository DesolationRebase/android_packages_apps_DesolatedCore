/*
 * Copyright (C) 2013 The CyanogenMod project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.desolationrom.settings.widgets;

import android.content.Context;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.AttributeSet;

public class SecureSettingSwitchPreference extends SwitchPreference implements Preference.OnPreferenceClickListener {

    public SecureSettingSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SecureSettingSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecureSettingSwitchPreference(Context context) {
        super(context, null);
    }

    protected void setValue(boolean value) {
        Settings.Secure.putInt(getContext().getContentResolver(), getKey(), value ? 1 : 0);
    }


    protected boolean getValue() {
        return Settings.Secure.getInt(getContext().getContentResolver(), getKey(), 0) == 1;
    }

    @Override
    protected void onAttachedToActivity() {
        super.onAttachedToActivity();
        setChecked(getValue());
        setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        setValue(isChecked());
        return true;
    }

}
