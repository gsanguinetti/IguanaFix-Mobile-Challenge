package com.gastonsanguinetti.contacts.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.gastonsanguinetti.contacts.R;
import com.gastonsanguinetti.contacts.model.Contact;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

	private List<Contact> contacts;
	
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
		notifyDataSetChanged();
	}
	
	@Override
	public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.contact_list_item, parent, false);
		return new ContactViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(ContactViewHolder holder, int position) {
		holder.contactNameTextView.setText(contacts.get(position).getFullName());
		
		//Config thumb
		DrawableRequestBuilder<String> thumbnailRequest = Glide
				.with(holder.contactImageView.getContext())
				.load(contacts.get(position).getThumb());
		
		//Load image
		Glide.with(holder.contactImageView.getContext())
				.load(contacts.get(position).getPhoto())
				.bitmapTransform(new CropCircleTransformation(holder.contactImageView.getContext()))
				.thumbnail(thumbnailRequest)
				.into(holder.contactImageView);
	}
	
	@Override
	public int getItemCount() {
		return contacts != null ? contacts.size() : 0;
	}
	
	
	public static class ContactViewHolder extends RecyclerView.ViewHolder {
		
		public TextView contactNameTextView;
		public ImageView contactImageView;
		
		
		public ContactViewHolder(View itemView) {
			super(itemView);
			contactNameTextView = (TextView) itemView.findViewById(R.id.name);
			contactImageView = (ImageView) itemView.findViewById(R.id.image);
		}
	}
}
