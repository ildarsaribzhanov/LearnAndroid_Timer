package com.example.udemylearntimer;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.timer_preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        int count = preferenceScreen.getPreferenceCount();

        for (int preferenceIndex = 0; preferenceIndex < count; preferenceIndex++) {
            Preference preference = preferenceScreen.getPreference(preferenceIndex);

            if (preference instanceof ListPreference) {
                String val = sharedPreferences.getString(preference.getKey(), "");
                setPrefLabel(preference, val);
            }
        }
    }

    private void setPrefLabel(Preference preference, String val) {
        if (!(preference instanceof ListPreference)) {
            return;
        }

        ListPreference listPreference = (ListPreference) preference;
        int index = listPreference.findIndexOfValue(val);

        if (index < 0) {
            return;
        }

        listPreference.setSummary(listPreference.getEntries()[index]);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);

        if (preference instanceof ListPreference) {
            String val = sharedPreferences.getString(key, "");
            setPrefLabel(preference, val);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
