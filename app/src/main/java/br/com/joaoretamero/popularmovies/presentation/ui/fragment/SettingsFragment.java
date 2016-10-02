package br.com.joaoretamero.popularmovies.presentation.ui.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import br.com.joaoretamero.popularmovies.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        bindPreferenceChangeListener(findPreference(getString(R.string.order_preference_key)));
    }

    private void bindPreferenceChangeListener(Preference preference) {
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    
                    int newValueEntryIndex = listPreference.findIndexOfValue(newValue.toString());
                    if (newValueEntryIndex >= 0) {
                        CharSequence newValueEntry = listPreference.getEntries()[newValueEntryIndex];
                        listPreference.setSummary(newValueEntry);
                    }
                }
                return true;
            }
        });
    }
}
