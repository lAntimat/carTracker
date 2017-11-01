package com.tracker.lantimat.cartracker.Client;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.tracker.lantimat.cartracker.R;

public class SettingsActivity extends PreferenceActivity{
	
	ListPreference m_updateList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);
    }

    
}
