package com.siddiqui.recycleit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.siddiqui.recycleit.databinding.SaleListingBinding;

import java.util.ArrayList;
import java.util.List;

// Created by Taha Siddiqui
// 2020-05-24
public class SalesListingAdapter extends RecyclerView.Adapter<SalesListingAdapter.ListingHolder> {

    private List<Listing> listings;
    private Context context;
    SaleListingBinding binding;
    public SalesListingAdapter(Context context, List<Listing> listings) {
        this.listings = listings;
        this.context = context;
    }

    @NonNull
    @Override
    public SalesListingAdapter.ListingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the binding layoutt and creates the newly created binding for that layout
        binding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.sale_listing, parent, false);

        return new ListingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingHolder holder, int position) {
        Log.e("Error ", "This is the number "+position);
        Listing hour = listings.get(position);
        if(holder == null){
            Log.e("HOLDER IS NULL", "HOLDER IS NULL");
        }
        if(hour==null){
            Log.e("HOUR IS NULL", "HOUR IS NULL");
        }
        if(holder.binding == null){
            Log.e("ITEM BINDING IS EMPTY", "ITEM BINDING EMPTY");

        }

        holder.binding.setList(hour);
    }


    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ListingHolder extends RecyclerView.ViewHolder{

        public SaleListingBinding binding;

        public ListingHolder(@NonNull SaleListingBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}
