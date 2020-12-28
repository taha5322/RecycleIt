package com.siddiqui.recycleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class RecylingCentreActivity extends AppCompatActivity {
    private static final String TAG = "RecycleTrashActivity: ";
    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 2;
    ArrayList<String> items = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    Button buttonShow;
    Button scanButton;
    ImageView listingPicture;
    int AUTOCOMPLETE_REQUEST_CODE;
    public static Place place;
    public static String apiKey = "AIzaSyB6vFSREwAYbZgKlj9JgCb8OAQbr0zNxXs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyling_centre);




        //implicit
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri geolocation = Uri.parse("geo:0,0?q=43.588648, -79.683915(4450 Tucana Crt)");
//        intent.setData(geolocation);
//        if(intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);
//        } else{
//            Toast.makeText(this, "Maps not availible", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(mRootLayout, "Sorry nothing found", Snackbar.LENGTH_LONG);
//            snackbar.show();
//        }

        // Initialize Places.
        Places.initialize(getApplicationContext(), apiKey);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Define a Place ID.
        String placeId = "ChIJRV_VcTNHK4gRxXIBu1HJ-ug";

        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields)
                .build();


        // Add a listener to handle the response.
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i(TAG, "Place found: " + place.getName());
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });

//        //FindCurrentPlaceRequestBuilder for additional fields
//        FindCurrentPlaceRequest findCurrentPlaceRequest= FindCurrentPlaceRequest.builder(placeFields).build();
//        placesClient.findCurrentPlace(findCurrentPlaceRequest).addOnSuccessListener((response) -> {
//            Toast.makeText(this,"find current place works", Toast.LENGTH_SHORT);
//            Log.i("TAGGG", "It worksss");
//        }).addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                int statusCode = apiException.getStatusCode();
//                // Handle error with given status code.
//                Log.e(TAG, "Place not found: " + exception.getMessage());
//            }
//        });

        //Fragment manager stuff
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragm);

        //what kind of place your user is going to type in
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(43.601530, -79.647298),
                new LatLng(43.604792, -79.652228)
        ));
        autocompleteFragment.setCountries("CA");

        //specifiy the type of place to return data
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.PHONE_NUMBER,
                Place.Field.ADDRESS, Place.Field.OPENING_HOURS,
                Place.Field.PHOTO_METADATAS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                RecycleTrashActivity.place = place;

                Intent intent = new Intent(RecylingCentreActivity.this, LocationPreview.class);

                //getting the photo
                // Get the photo metadata.
                PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);

                // Get the attribution text.
                String attributions = photoMetadata.getAttributions();

                // Create a FetchPhotoRequest.
                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        .setMaxWidth(500) // Optional.
                        .setMaxHeight(300) // Optional.
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
//                    listingPicture.setImageBitmap(bitmap);
                    intent.putExtra("Bitmap", bitmap);
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        int statusCode = apiException.getStatusCode();
                        // Handle error with given status code.
                        Log.e(TAG, "Place not found: " + exception.getMessage());
                    }
                });

                intent.putExtra("name", place.getName());
                intent.putExtra("id", place.getId());
                intent.putExtra("address", place.getAddress());
                intent.putExtra("phoneNumber", place.getPhoneNumber());
                intent.putStringArrayListExtra("openingHours", (ArrayList<String>) place.getOpeningHours().getWeekdayText());
                startActivity(intent);

            }
            //name, address, timings, photo,


            @Override
            public void onError(@NonNull Status status) {
                Log.i("Error", "an error occured " + status);
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_ACCESS_COARSE_LOCATION){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //granted

            }else{
                //not granted
            }
        }
    }

}
