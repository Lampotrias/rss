package com.example.rss.presentation.settings;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.rss.R;

public class settingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
