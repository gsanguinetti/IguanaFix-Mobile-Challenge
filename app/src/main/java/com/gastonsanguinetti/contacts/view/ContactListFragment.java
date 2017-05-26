package com.gastonsanguinetti.contacts.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gastonsanguinetti.contacts.R;

public class ContactListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(getActivity() instanceof AppCompatActivity) {
			Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
			((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		}
	}
	
}
