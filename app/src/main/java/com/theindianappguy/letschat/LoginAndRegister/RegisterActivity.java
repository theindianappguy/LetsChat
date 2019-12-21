package com.theindianappguy.letschat.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theindianappguy.letschat.CustomClasses.UserClass;
import com.theindianappguy.letschat.MainActivity;
import com.theindianappguy.letschat.R;
import com.theindianappguy.letschat.helpingClasses.SessionManagement;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    /*UI Componenets*/
    private FirebaseAuth mAuth;
    EditText userNameEt, emailEt, passwordEt, userPhoneEt;
    private RelativeLayout registerUserBtn;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.collection("Users").document("My First Note");


    /*Variables*/
    SessionManagement cookie;
    boolean gottext = false;
    String nameSt, phoneNumberSt;
    String status, profileUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // defining the layout views
        defineViews();
        otherDefinings();

        //setting on click listener on the layout views
        setUpOnClicklistner();

    }

    private void setUpOnClicklistner() {
        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String emailString = emailEt.getText().toString();
                    String passwordString = passwordEt.getText().toString();
                    nameSt = userNameEt.getText().toString();
                    phoneNumberSt = userPhoneEt.getText().toString();
                    gottext = true;
                    createUser(emailString, passwordString);
                } else {
                    Toast.makeText(RegisterActivity.this, "Make sure all feild are filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void otherDefinings() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        cookie = new SessionManagement(this);
    }

    private void defineViews() {
        registerUserBtn = findViewById(R.id.registerUserBtn);

        userNameEt = findViewById(R.id.userNameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        userPhoneEt = findViewById(R.id.userPhoneEt);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private boolean validate() {

        boolean valid = true;

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        if (userNameEt.equals("")) {
            valid = false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEt.setError("enter a valid email address");
            valid = false;
        } else {
            emailEt.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEt.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEt.setError(null);
        }

        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            status = "Enjoying LetsChat";
            profileUrl = "nothing";
            uploadUserDataToCloud();
            cookie.CreateLoginSession(currentUser.getUid(), phoneNumberSt , currentUser.getEmail(),status,profileUrl);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void uploadUserDataToCloud() {

        String name = userNameEt.getText().toString();
        //verified with OTP
        String phonenumber = userPhoneEt.getText().toString();
        String emailSt = emailEt.getText().toString();

        UserClass userClass = new UserClass();
        userClass.setName(name);
        userClass.setPhonenumber(phonenumber);
        userClass.setEmail(emailSt);
        userClass.setStatus(status);
        userClass.setProfileurl(profileUrl);

        db.collection("UserInfo").document(mAuth.getUid())
                .set(userClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RegisterActivity.this, "Saved Succesfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });

    }


}
