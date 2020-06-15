package com.example.integratedfirestoretolocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    Object ob = new Object();
    String latitude;
    String longitude;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("A/B");

    String UserID =  Math.random()+"===>";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callPermissions();

        Toast.makeText(MainActivity.this, "firebase connection success ", Toast.LENGTH_LONG).show();

    }


    public void requestLocationUpdates() {

        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(2000);
        locationRequest.setInterval(4000);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //     Log.e(TAG, "latitude = " + locationResult.getLastLocation().getLatitude() + "      longitude = " + locationResult.getLastLocation().getLongitude());


                latitude = "$" + (locationResult.getLastLocation().getLatitude());
                longitude = "" + (locationResult.getLastLocation().getLongitude()+"$");

                Pusher();


            }
        }, getMainLooper());

    }
    public void Pusher()
    {
        // Create a Map to store the data we want to set

        Map<String, Object> dataToSave = new HashMap<>();

        dataToSave.put( "&"+UserID, latitude + "****" + longitude);
        Task<Void> future = db.collection("to be location").document("to be a greater exact location").update(dataToSave);
        fetchUpdates();
    }



    public void callPermissions() {

        Permissions.check(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, "Location permission required", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Info"), new PermissionHandler() {
            @Override
            public void onGranted() {
                requestLocationUpdates();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }
        });

    }



    public void fetchUpdates() {

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> DataPulled = documentSnapshot.getData();
                Collection<Object> isoCodes = DataPulled.values();


                Log.e(TAG,"CONVERT NEEDED"+isoCodes);

                Sorter(isoCodes);
                Log.e("TAG", "here is the data" + DataPulled);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              //  Log.d("TAG", "FAIL");
            }
        });






    }
    //TODO extract parsing DATA from Log Cat
//TODO Basically, start with the formatting of the comma
    //TODO After that, you start inserting the seperate UserID+location
    //TODO after that, parse the locations

      public void Sorter(Collection<Object> Values)
      {
       String  basicDataPulled =Values.toString();

      }
}






















