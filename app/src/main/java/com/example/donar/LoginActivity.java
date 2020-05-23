package com.example.donar;

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


public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public static final int REQUEST_CODE = 777;

    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    Button button = findViewById(R.id.googleButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provider)
                            .setIsSmartLockEnabled(false)
                            .build(),REQUEST_CODE);
                }

            }
        };

    }


/*
    final Button buttonVolver = findViewById(R.id.buttonVolver);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    });
*/


    @Override
    protected void onResume() {
        super.onResume();
        mfirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseAuth.removeAuthStateListener(mAuthListener);
    }

    public void cerrarSesion(View view) {

        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(LoginActivity.this, "Sesión cerrada.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
