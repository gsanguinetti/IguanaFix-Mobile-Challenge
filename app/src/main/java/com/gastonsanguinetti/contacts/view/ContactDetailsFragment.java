package com.gastonsanguinetti.contacts.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gastonsanguinetti.contacts.R;
import com.gastonsanguinetti.contacts.model.Address;
import com.gastonsanguinetti.contacts.model.Contact;
import com.gastonsanguinetti.contacts.model.Phone;
import com.gastonsanguinetti.contacts.viewmodel.ContactDetailsViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContactDetailsFragment extends UseCaseFragment<Contact> {
	
	CollapsingToolbarLayout collapsingToolbarLayout;
	ImageView contactImageView;
	
	View progress;
	View errorLayout;
	LinearLayout contactDetailsContainer;
	
	public ContactDetailsFragment() {
	}
	
	public static final String CONTACT_EXTRA = "ContactExtra";
	
	@NonNull
	@Override
	protected Class getViewModelClass() {
		return ContactDetailsViewModel.class;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_details, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//Obtain the clicked contact and initialize the viewmodel
		Contact preFilledContact = (Contact) getArguments().getSerializable(CONTACT_EXTRA);
		((ContactDetailsViewModel) getViewModel())
				.initialize(preFilledContact);
		
		//Get views
		collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
		contactImageView = (ImageView) view.findViewById(R.id.image);
		errorLayout = view.findViewById(R.id.errorImage);
		progress = view.findViewById(R.id.progress);
		contactDetailsContainer = (LinearLayout) view.findViewById(R.id.content_container);
		
		//Fill the layout with the info that already know
		preFillContactDetails(preFilledContact);
		
		//Workaround for the scrolling fling issue in Collapsing Toolbar.
		//https://stackoverflow.com/questions/31795483/collapsingtoolbarlayout-doesnt-recognize-scroll-fling
		//Todo: find a better solution implementing a custom Scrolling Behavior
		ViewGroup rootContainer = (ViewGroup) view.findViewById(R.id.root_container);
		rootContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Do nothing
			}
		});
		
		//Start observing events
		getViewModel().getData().observe(this, new Observer<Contact>() {
			@Override
			public void onChanged(@Nullable Contact contactDetails) {
				fillContactDetails(contactDetails);
			}
		});
	}
	
	private void preFillContactDetails(Contact preFilledContact) {
		collapsingToolbarLayout.setTitle(preFilledContact.getFullName());
		loadImage(contactImageView, preFilledContact.getPhoto());
	}
	
	private void fillContactDetails(Contact contactDetails) {
		contactDetailsContainer.removeAllViews();
		collapsingToolbarLayout.setTitle(contactDetails.getFullName());
		loadImage(contactImageView, contactDetails.getPhoto());
		addPhonesSection(contactDetails.getPhones());
		addAddressesSection(contactDetails.getAddresses());
		addBirthdaySection(contactDetails.getBirthDate());
	}
	
	private void addPhonesSection(List<Phone> phones) {
		ViewGroup phoneSection = createSection(contactDetailsContainer);
		ImageView phoneIcon = (ImageView) phoneSection.findViewById(R.id.section_image);
		phoneIcon.setImageResource(R.drawable.ic_phone_black_48px);
		
		LinearLayout entries = (LinearLayout) phoneSection.findViewById(R.id.entries_container);
		
		for (Phone phone : phones) {
			if (phone.getNumber() != null) {
				entries.addView(
						fillSection(createEntry(phoneSection), phone.getNumber(), phone.getType()));
			}
		}
		
		if (entries.getChildCount() > 0) {
			contactDetailsContainer.addView(phoneSection);
		}
	}
	
	private void addAddressesSection(List<Address> addresses) {
		boolean hasAddresses = addresses != null && !addresses.isEmpty();
		
		if (hasAddresses) {
			
			ViewGroup addressSection = createSection(contactDetailsContainer);
			ImageView addressIcon = (ImageView) addressSection.findViewById(R.id.section_image);
			addressIcon.setImageResource(R.drawable.ic_location_on_black_48px);
			
			LinearLayout entries = (LinearLayout) addressSection.findViewById(R.id.entries_container);
			
			for (Address address : addresses) {
				if (address.getHome() != null) {
					entries.addView
							(fillSection(createEntry(addressSection), address.getHome(), getString(R.string.home)));
				}
				if (address.getWork() != null) {
					entries.addView
							(fillSection(createEntry(addressSection), address.getWork(), getString(R.string.work)));
				}
			}
			
			contactDetailsContainer.addView(addressSection);
		}
		
	}
	
	private void addBirthdaySection(String birthday) {
		if( birthday != null && !birthday.isEmpty()) {
			ViewGroup birthdaySection = createSection(contactDetailsContainer);
			ImageView birthdayIcon = (ImageView) birthdaySection.findViewById(R.id.section_image);
			birthdayIcon.setImageResource(R.drawable.ic_perm_contact_calendar_black_48px);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			Date convertedDate;
			try {
				convertedDate = dateFormat.parse(birthday);
			} catch (ParseException e) {
				//In a real case it should throw a handled exception
				Log.e(getClass().getSimpleName(), "Bad birthday format");
				return;
			}
			
			DateFormat formattedDate = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
			LinearLayout entries = (LinearLayout) birthdaySection.findViewById(R.id.entries_container);
			entries.addView
					(fillSection(createEntry(birthdaySection), formattedDate.format(convertedDate), getString(R.string.birthday)));
			contactDetailsContainer.addView(birthdaySection);
		}
	}
	
	private ViewGroup createSection(ViewGroup root) {
		return (ViewGroup) LayoutInflater.from(getContext())
				.inflate(R.layout.contact_details_section, root, false);
	}
	
	private ViewGroup fillSection(ViewGroup section, String value, String secondary) {
		ViewGroup entry = createEntry(section);
		TextView valueTextView = (TextView) entry.findViewById(R.id.entry);
		TextView secondaryTextView = (TextView) entry.findViewById(R.id.secondary);
		
		valueTextView.setText(value);
		secondaryTextView.setText(secondary);
		
		return entry;
	}
	
	private ViewGroup createEntry(ViewGroup root) {
		return (ViewGroup) LayoutInflater.from(getContext())
				.inflate(R.layout.contact_details_entry, root, false);
	}
	
	private void loadImage(ImageView imageView, String uri) {
		Glide.with(imageView.getContext())
				.load(uri)
				.into(imageView);
	}
	
	@Override
	protected void onUseCaseLoading() {
		progress.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
		contactDetailsContainer.setVisibility(View.GONE);
	}
	
	@Override
	protected void onUseCaseError() {
		progress.setVisibility(View.GONE);
		errorLayout.setVisibility(View.VISIBLE);
		contactDetailsContainer.setVisibility(View.GONE);
		
		final Snackbar errorSnackbar = Snackbar.make(getView(),
				R.string.error_msg,
				Snackbar.LENGTH_INDEFINITE);
		errorSnackbar.setAction(R.string.retry_button, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getViewModel().getData();
			}
		});
		errorSnackbar.show();
	}
	
	@Override
	protected void onUseCaseFinished() {
		progress.setVisibility(View.GONE);
		errorLayout.setVisibility(View.GONE);
		contactDetailsContainer.setVisibility(View.VISIBLE);
	}
}
