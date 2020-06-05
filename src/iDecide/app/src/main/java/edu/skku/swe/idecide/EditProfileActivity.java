package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.skku.swe.idecide.entities.User;
import edu.skku.swe.idecide.fragment.FragmentProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private NumberPicker.OnValueChangeListener valueChangeListener;
    private static final int REQUEST_CODE = 0;
    private File newImageFile;

    Button editImgButton;
    CircleImageView imageView;
    TextInputEditText nicknameEditText, genderEditText, ageEditText;
    TextInputLayout l_nickname, l_gender, l_age;
    ProgressBar mProgressBar;

    // initialize
    Bitmap getImage = null;
    String getNickname = null;
    int getGender = -1, getAge = -1;
    private String user_key;

    // temp
    final CharSequence[] genderList = {"남자", "여자", "기타"};
    final CharSequence[] changeProfile = {"새 프로필 사진", "프로필 사진 삭제"};
    int getIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        tb.setTitle("프로필 수정");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.img_edit_profile);
        editImgButton = findViewById(R.id.editButton_edit_profile);
        nicknameEditText = findViewById(R.id.nickname_edit_profile);
        genderEditText = findViewById(R.id.gender_edit_profile);
        ageEditText = findViewById(R.id.age_edit_profile);
        genderEditText.setInputType(0);
        ageEditText.setInputType(0);
        l_nickname = findViewById(R.id.l_nickname_edit_profile);
        l_gender = findViewById(R.id.l_gender_edit_profile);
        l_age = findViewById(R.id.l_age_edit_profile);
        l_nickname.setCounterEnabled(true);
        l_nickname.setCounterMaxLength(20);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_e);

        // SET
        /**
         * get profile image from firestore
         */
        showDialog();

        Intent fromProfile = getIntent();
        user_key = fromProfile.getStringExtra("user_key");
        //getImage = (Bitmap)fromProfile.getParcelableExtra("img");
        getNickname = fromProfile.getStringExtra("nickname");
        getAge = fromProfile.getIntExtra("age", -1);
        getGender = fromProfile.getIntExtra("gender", -1);


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("User").child(user_key).child("profile.jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                getImage = bitmap;
                imageView.setImageBitmap(getImage);
                hideDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                getImage = null; hideDialog();
                imageView.setImageResource(R.drawable.default5);
            }});
        nicknameEditText.setText(getNickname);
        if (getGender > 0) genderEditText.setText(genderList[getGender]);
        if (getAge > 0) ageEditText.setText(Integer.toString(getAge));




        // GET
        // image
        editImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("프로필 사진 변경");
                builder.setItems(changeProfile, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                tedPermission(); // 권한확인부분 수정 필요
                                Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(gallary, REQUEST_CODE);
                                break;

                            case 1:
                                showDialog();
                                StorageReference ref = FirebaseStorage.getInstance().getReference().child("User").child(user_key).child("profile.jpg");
                                ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        getImage = null;
                                        imageView.setImageResource(R.drawable.default5);
                                        hideDialog();
                                        Toast.makeText(EditProfileActivity.this, "프로필 사진이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideDialog();
                                        Toast.makeText(EditProfileActivity.this, "프로필 사진이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                        }

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // nickname
        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0 || s == null) {
                    l_nickname.setError("닉네임을 입력해 주세요");
                } else if (s.toString().length() > 20){
                    l_nickname.setError("닉네임을 20자 이내로 입력해 주세요");
                }
                else {
                    l_nickname.setError(null);
                }
            }
        });
        // gender
        genderEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);


                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("성별을 선택해주세요")
                        .setSingleChoiceItems(genderList, getGender, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int index){ getIndex = index; }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getGender = getIndex;
                        genderEditText.setText(genderList[getGender].toString());
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // age
        ageEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);


                final NumberPicker numberPicker = new NumberPicker(EditProfileActivity.this);
                numberPicker.setMinValue(5);
                numberPicker.setMaxValue(120);
                if (getAge < 0) numberPicker.setValue(20);
                else numberPicker.setValue(getAge);
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);

                builder.setTitle("나이를 선택해주세요");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAge = numberPicker.getValue();
                        ageEditText.setText(Integer.toString(getAge));
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                builder.setView(numberPicker);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    // TOOL BAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.toolbar_confirm:{
                getNickname = nicknameEditText.getText().toString();
                showDialog();
                if (getNickname.length() == 0 || getGender < 0 || getAge < 0) {
                    hideDialog();
                    Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    // change image to bytearray
                    try {
                        // update fragment profile
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);

                        // update firestore without profile image
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> postValue = null;
                        User user = new User(getNickname, getGender, getAge);
                        postValue = user.toMap();
                        db.collection("User").document(user_key).update(postValue);


                        // update profile image
                        if (getImage != null) {
                            StorageReference ref = FirebaseStorage.getInstance().getReference().child("User").child(user_key).child("profile.jpg");
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            getImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();
                            UploadTask uploadTask = ref.putBytes(data);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    hideDialog();
                                    finish();
                                    Toast.makeText(EditProfileActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return true;
                        }
                        else
                        {
                            hideDialog();
                            finish();
                            Toast.makeText(EditProfileActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                    } catch (Exception e) {
                        hideDialog();
                        //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "서비스 이용이 원활하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.confirm_toolbar, menu);
        return true;
    }


    // GET IMAGE FROM USER STORAGE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = data.getData();
                Cursor cursor = null;

                try {
                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();
                    newImageFile = new File(cursor.getString(column_index));

                    getImage = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                    getImage = resizeBitmap(getImage, 300, 300);
                    imageView.setImageBitmap(getImage);


                } catch (Exception e) { }
                finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void tedPermission() {

        /*
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() { } // 권한 요청 성공

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) { } // 권한 요청 실패
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setDeniedMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
                .check();

         */
    }


    // GET AGE
    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


    // RESIZE & CROP IMAGE
    public Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap.getWidth() != width || bitmap.getHeight() != height){
            float ratio = 1.0f;

            if (width > height) {
                ratio = (float)width / (float)bitmap.getWidth();
            } else {
                ratio = (float)height / (float)bitmap.getHeight();
            }

            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (int)(((float)bitmap.getWidth()) * ratio), // Width
                    (int)(((float)bitmap.getHeight()) * ratio), // Height
                    true);
        }

        return bitmap;
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
}