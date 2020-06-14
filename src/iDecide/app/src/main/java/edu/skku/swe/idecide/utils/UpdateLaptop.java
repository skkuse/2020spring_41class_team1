package edu.skku.swe.idecide.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.skku.swe.idecide.entities.Laptop;

public class UpdateLaptop {
    private static String TAG = "update_firestore";
    private static FirebaseFirestore mDb;

    public static void saveLaptopsToFiretore(String filename, Context context) throws IOException {
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                ArrayList<String> laptop_infos = new ArrayList<String>();
                mDb = FirebaseFirestore.getInstance();

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    Laptop laptop = new Laptop();
                    laptop.setmLaptopName(line);
                    line = bufferedReader.readLine();
                    laptop.setmMinPrice(line);
                    line = bufferedReader.readLine();
                    laptop.setmMaxBatteryLife(line);
                    line = bufferedReader.readLine();
                    laptop.setmDisplaySize(line);
                    line = bufferedReader.readLine();
                    laptop.setmOfficialSite(line);
                    line = bufferedReader.readLine();
                    laptop.setmScore(line);
                    line = bufferedReader.readLine();
                    laptop.setmCpuScore(line);
                    line = bufferedReader.readLine();
                    laptop.setmGpuScore(line);
                    mDb.collection("Laptop").document()
                            .set(laptop)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!: ");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}