package com.example.donar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public static final int REQUEST_CODE = 777;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }






    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId())
        {




    }

    }
}
