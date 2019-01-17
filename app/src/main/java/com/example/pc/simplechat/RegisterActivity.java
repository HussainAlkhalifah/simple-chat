package com.example.pc.simplechat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private FirebaseDatabase mFireDatabase;
    private DatabaseReference mFireReference;
    private Button mButtonReg;
    private FirebaseAuth mAuth;
    //private User mUser;
    private FirebaseUser mCurrentUser;
    //ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mButtonReg = (Button) findViewById(R.id.buttonRegister);
        mFireDatabase = FirebaseDatabase.getInstance();
        mFireReference = mFireDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();



        mButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailView.getText().toString().trim();
                final String password = mPasswordView.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()) {
                   Toast t =  Toast.makeText(RegisterActivity.this,"empty email or password",Toast.LENGTH_SHORT);
                   t.show();
                } else {
                    //show progreaa bar
                    //Toast t =  Toast.makeText(RegisterActivity.this,"Successful",Toast.LENGTH_SHORT);
                    createNewUser(email,password);
                    //t.show();
                }
            }
        });

    }
    public void updateUI() {

    }
    public void createNewUser(final String email, final String pass) {
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
/*                    users = new ArrayList<>();
                    users.add(new User(email,pass));*/
                    mCurrentUser = mAuth.getCurrentUser();
                    User user = new User(email,pass);
                    mFireReference.child("users").push().setValue(user);


                    startActivity(new Intent(RegisterActivity.this,MessageActivity.class));

                }else {
                    Toast.makeText(RegisterActivity.this,"Auth failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}