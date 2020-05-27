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

                    laptop.setLaptopModel(line);

                    line = bufferedReader.readLine();
                    laptop.setMinPrice(line);

                    line = bufferedReader.readLine();
                    laptop.setMaxPrice(line);

                    line = bufferedReader.readLine();
                    laptop.setMinBatteryLife(line);

                    line = bufferedReader.readLine();
                    laptop.setMaxBatteryLife(line);

                    line = bufferedReader.readLine();
                    laptop.setCPUModel(line);

                    line = bufferedReader.readLine();
                    laptop.setCPUSpeed(line);

                    line = bufferedReader.readLine();
                    laptop.setCPUCores(line);

                    line = bufferedReader.readLine();
                    laptop.setCPUClass(line);

                    line = bufferedReader.readLine();
                    laptop.setGPUModel(line);

                    line = bufferedReader.readLine();
                    laptop.setGPUSpeed(line);

                    line = bufferedReader.readLine();
                    laptop.setGPUMemSpeed(line);

                    line = bufferedReader.readLine();
                    laptop.setGPUMemSize(line);

                    line = bufferedReader.readLine();
                    laptop.setGPUClass(line);

                    line = bufferedReader.readLine();
                    laptop.setDisplaySize(line);

                    line = bufferedReader.readLine();
                    laptop.setDisplayHres(line);

                    line = bufferedReader.readLine();
                    laptop.setDisplayVres(line);

                    line = bufferedReader.readLine();
                    laptop.setDisplayTouch(line);

                    line = bufferedReader.readLine();
                    laptop.setStorageSize(line);

                    line = bufferedReader.readLine();
                    laptop.setStorageType(line);

                    line = bufferedReader.readLine();
                    laptop.setMemSize(line);

                    line = bufferedReader.readLine();
                    laptop.setOSModel(line);

                    mDb.collection("Laptop").document(laptop.getLaptopModel())
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