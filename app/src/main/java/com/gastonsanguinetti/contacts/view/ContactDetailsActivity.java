package com.gastonsanguinetti.contacts.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.gastonsanguinetti.contacts.R;

public class ContactDetailsActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_details);
		
		if(savedInstanceState == null) {
			
			Bundle bundle = new Bundle();
			bundle.putSerializable(ContactDetailsFragment.CONTACT_EXTRA,
					getIntent().getSerializableExtra(ContactDetailsFragment.CONTACT_EXTRA));
			
			Fragment contactDetailsFragment = new ContactDetailsFragment();
			contactDetailsFragment.setArguments(bundle);
			
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, contactDetailsFragment, ContactDetailsFragment.class.getSimpleName())
					.commit();
		}
		
	}
	
}
