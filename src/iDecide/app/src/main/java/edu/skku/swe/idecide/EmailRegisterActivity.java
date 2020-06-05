package edu.skku.swe.idecide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import edu.skku.swe.idecide.entities.User;

import static android.text.TextUtils.isEmpty;


public class EmailRegisterActivity extends AppCompatActivity implements
        View.OnClickListener
{
    private static final String TAG = "RegisterActivity";

    //widgets
    private EditText mEmail, mPassword, mConfirmPassword;
    private TextInputLayout lEmail, lPassword, lCFPassword;
    private ProgressBar mProgressBar;

    //vars
    private FirebaseFirestore mDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_l);
        lEmail = findViewById(R.id.l_input_email);
        lPassword = findViewById(R.id.l_input_password);
        lCFPassword = findViewById(R.id.l_input_confirm_password);

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) { lEmail.setError("이메일을 입력해 주세요"); }
                else lEmail.setError(null);
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) { lPassword.setError("비밀번호를 입력해 주세요"); }
                else if (s.toString().length() < 6) { lPassword.setError("비밀번호 형식이 올바르지 않습니다"); }
                else lPassword.setError(null);
            }
        });

        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(mPassword.getText().toString())) { lCFPassword.setError("비밀번호를 다시 확인해 주세요"); }
                else lCFPassword.setError(null);
            }
        });


        findViewById(R.id.btn_register).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        hideSoftKeyboard();
    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password){

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            //insert some default data
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
                                        Toast.makeText(EmailRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        //View parentLayout = findViewById(android.R.id.content);
                                        //Snackbar.make(parentLayout, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
                            //View parentLayout = findViewById(android.R.id.content);
                            //Snackbar.make(parentLayout, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(EmailRegisterActivity.this, "다른 이메일 주소를 사용해 주세요", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(EmailRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }

                        // ...
                    }
                });
    }


    private void goToGetProfilePage(String email){
        Log.d(TAG, "go to Get Profile Page");

        Intent intent = new Intent(EmailRegisterActivity.this, GetProfileActivity.class);
        intent.putExtra("user_key", email);
        startActivity(intent);
        finish();
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

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:{
                Log.d(TAG, "onClick: attempting to register.");

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                //check for null valued EditText fields
                if(!isEmpty(email) && !isEmpty(password) && !isEmpty(confirmPassword)){

                    //check if passwords match
                    if(password.equals(confirmPassword)){
                        //Initiate registration task

                        boolean availaleEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                        boolean availablePW = true;
                        if (password.length() < 6) availablePW = false;

                        if (availaleEmail && availablePW) registerNewEmail(email, password);
                        else {
                            if (!availaleEmail){ lEmail.setError("이메일 형식이 올바르지 않습니다"); }
                            if (!availablePW){ lPassword.setError("비밀번호 형식이 올바르지 않습니다"); }
                        }



                    }else{ lCFPassword.setError("비밀번호가 일치하지 않습니다"); }

                }
                if (isEmpty(email)) { lEmail.setError("이메일을 입력해 주세요"); }
                if (isEmpty(password)) { lPassword.setError("비밀번호를 입력해 주세요"); lCFPassword.setError("비밀번호를 다시 확인해 주세요");}
                if (!isEmpty(password) && isEmpty(confirmPassword)) { lCFPassword.setError("비밀번호를 다시 확인해 주세요"); }

                break;
            }
        }
    }
}