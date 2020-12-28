package com.siddiqui.recycleit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class RecycleTrashActivity extends AppCompatActivity {
    private static final String TAG = "RecycleTrashActivity: ";
    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 2;
    ArrayList<String> items = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    Button buttonShow;
    Button scanButton;

    ImageView listingPicture;
    int AUTOCOMPLETE_REQUEST_CODE;
    private FusedLocationProviderClient fusedLocationClient;
    public static Place place;
    public static String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_trash);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        initItems();
        spinnerDialog = new SpinnerDialog(RecycleTrashActivity.this, items, "Select Item");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Intent intent = new Intent(RecycleTrashActivity.this, ItemActivity.class);
                intent.putExtra("Item", item);
                startActivity(intent);
            }
        });
        buttonShow = findViewById(R.id.btnShow);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });
        scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "works here");
                fetchLocation();

            }
        });


    }



    //
//    private void getLocationPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this, ACCESS_FINE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // You can use the API that requires the permission.
//            performAction(...);
//        } else if (shouldShowRequestPermissionRationale(...)) {
//            // In an educational UI, explain to the user why your app requires this
//            // permission for a specific feature to behave as expected. In this UI,
//            // include a "cancel" or "no thanks" button that allows the user to
//            // continue using your app without granting the permission.
////            showInContextUI(...)?;
//        } else {
//            // You can directly ask for the permission.
//            requestPermissions(...);
//        }
//    }
    private void fetchLocation() {

        if (ContextCompat.checkSelfPermission(RecycleTrashActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            //show explanation?
            Log.i("TAG", "if 1");

            if (ActivityCompat.shouldShowRequestPermissionRationale(RecycleTrashActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //show explanation to user asynchronously
                //this thread waiting for user's response after
                //user sees explanation

                Log.i("TAG", "if 2");

                new AlertDialog.Builder(this)
                        .setTitle("Required loc permission")
                        .setMessage("You have to give this permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(RecycleTrashActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                //no explanation needed, request
                ActivityCompat.requestPermissions(RecycleTrashActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
                //MY_PERMISSIONS constant is an inbuilt constant
                //Callback method gets result of the request
                Log.i("TAG", "else 1");

            }
        } else {
            //permiss has been granted

            Log.i("TAG", "else 2");

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                //GET STUFF FROM HERE
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                Toast.makeText(RecycleTrashActivity.this, "Latitude is " + latitude, Toast.LENGTH_SHORT).show();
                                Toast.makeText(RecycleTrashActivity.this, "Longitu is " + longitude, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initItems() {
//        items.add("Ball");
//        items.add("bottle");
//        items.add("Jug");
//        items.add("Juicbox");
        for(int i=0; i< (Items.items).length; i++){
            items.add(Items.items[i]);
        }
    }


}
