package com.example.rss.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

	protected void showToastMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//enabling toolbar to have menu items in fragment
		setHasOptionsMenu(true);
	}
}
