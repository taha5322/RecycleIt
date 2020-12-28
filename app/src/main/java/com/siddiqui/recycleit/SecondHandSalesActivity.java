package com.siddiqui.recycleit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.siddiqui.recycleit.databinding.SaleListingBinding;
import com.siddiqui.recycleit.Listing;

import java.util.ArrayList;
import java.util.List;

public class SecondHandSalesActivity extends AppCompatActivity {

    SaleListingBinding binding;
    private SalesListingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand_sales);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_hand_sales);
//        adapter = new HourlyAddapter(list, this);
        adapter = new SalesListingAdapter(this, getListings());


        binding..setAdapter(adapter);
//        binding.hourlyListItems.setHasFixedSize(true);
//        binding.hourlyListItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.hourlyListItems.setLayoutManager(new LinearLayoutManager(this));

    }

    private List<Listing> getListings(){
        List<Listing> listings = new ArrayList<>();
        Listing listing = new Listing("Kids' toys and gardening", "Kids and adults",
                "Your regular old neighborhood garage sale. Bring your kids" +
                        "because we have a lot of toys to give away. Don't miss out!","06/02/20",
                -1);// add images
        listings.add(listing);
        Listing listing2 = new Listing("Garden sale", "adults",
                "Come over at our place next week for a nice garden sale. We're " +
                        "going to be selling lots of plants, and some gardening tools" +
                        " so don't miss out","05/30/20",
                        -1);// add images
        listings.add(listing2);
        Listing listing3 = new Listing("Book sale", "For young adults", "Hey guys, long time book fan here," +
                "I have been reading novels since the 2nd grade and I'm going to go to graduate university" +
                "next year. I have tons of books that I would like other people to read so come check it out!!",
                "05/28/20",
                -1);// add images
        listings.add(listing3);
        return listings;
    }
}
