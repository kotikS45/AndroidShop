package com.example.shop.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.shop.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(@NonNull Preference preference) {
        if (preference instanceof PreferenceScreen) {
            String key = preference.getKey();
            if ("screen".equals(key)) {
                // Create a new fragment to display the nested preference screen
                NestedSettingsFragment nestedSettingsFragment = new NestedSettingsFragment();
                Bundle args = new Bundle();
                args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, key);
                nestedSettingsFragment.setArguments(args);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, nestedSettingsFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    public static class NestedSettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_screen, rootKey);
        }
    }
}
