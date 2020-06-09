package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import edu.skku.swe.idecide.entities.User;

public class RegisterActivity extends AppCompatActivity {
    Button emailBT, googleBT;
    private ProgressBar mProgressBar;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_UP = 1000;
    private FirebaseFirestore mDb;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        mProgressBar = findViewById(R.id.progressBar_r);
        emailBT = findViewById(R.id.email_register_button);
        googleBT = findViewById(R.id.google_register_button);
        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        // when register with email
        emailBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, EmailRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // when register with google
        googleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(RegisterActivity.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_UP);
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final String email = acct.getEmail();
        showDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Log.v("error", email);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.v("errora", "asdasdasdsd");
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User();
                            user.setEmail(email);

                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
                            mDb.setFirestoreSettings(settings);

                            DocumentReference newUserRef = mDb
                                    .collection(getString(R.string.collection_users))
                                    .document(email);

                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    hideDialog();
                                    if(task.isSuccessful()){
                                        goToGetProfilePage(email);
                                    }else{
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        //View parentLayout = findViewById(android.R.id.content);
                                        //Snackbar.make(parentLayout, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            hideDialog();
                            Toast toast = DynamicToast.makeError(getApplicationContext(), "회원가입에 실패했습니다", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_UP) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                String email = account.getEmail();


                /**
                 * 이미 계정이 있는지 없는지 확인
                 */
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("User").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                hideDialog();
                                Toast toast = DynamicToast.makeError(getApplicationContext(), "이미 회원가입 된 계정입니다", Toast.LENGTH_SHORT);
                                toast.show();
                                finish();
                            }
                            else {
                                firebaseAuthWithGoogle(account);
                            }
                        }
                        else {
                            hideDialog();
                            Toast toast = DynamicToast.makeError(getApplicationContext(), "회원가입에 실패했습니다", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.v("error", e.getMessage());
                hideDialog();
                Toast toast = DynamicToast.makeError(getApplicationContext(), "회원가입에 실패했습니다", Toast.LENGTH_SHORT);
                toast.show();

                // ...
            }
        }
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void goToGetProfilePage(String email){
        Intent intent = new Intent(RegisterActivity.this, GetProfileActivity.class);
        intent.putExtra("user_key", email);
        startActivity(intent);
        finish();
    }
}
