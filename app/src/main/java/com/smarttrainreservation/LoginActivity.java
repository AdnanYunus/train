package com.smarttrainreservation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarttrainreservation.Helper.Validations;
import com.smarttrainreservation.Models.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

  /*  @InjectView(R.id.txt_logo)
    TextView txt_logo;*/
    @InjectView(R.id.edt_email)
    EditText edt_email;
    @InjectView(R.id.edt_pass)
    EditText edt_pass;
    @InjectView(R.id.bt_go)
    Button btGo;
    @InjectView(R.id.cv)
    CardView cv;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.progress_view)
    CircularProgressView progressView;
    User donors = null;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressView.setVisibility(View.INVISIBLE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, SignupActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, SignupActivity.class));
                }
                break;
            case R.id.bt_go:








               /* Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);*/


                if (validations()) {
                    loginUser(edt_email.getText().toString(), edt_pass.getText().toString());
                }


                break;
        }
    }

    public boolean validations() {

        if (edt_email.getText().toString().length() < 1) {
            edt_email.setError(" Email can't be empty");
            return false;
        } else if (!(Validations.isEmailValid(edt_email.getText().toString()))) {
            edt_email.setError("Email is not valid");
            return false;
        } else if (edt_pass.getText().toString().length() < 1) {
            edt_pass.setError(" Password can't be empty");
            return false;

        } else if (edt_pass.getText().toString().length() < 6) {
            edt_pass.setError("Password length minimum 8 characters");
            return false;

        } else {


            return true;


        }


    }

    public void loginUser(String email, String password) {

        progressView.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressView.setVisibility(View.INVISIBLE);
                        if (!task.isSuccessful()) {
                            // there was an error

                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                        } else {

                            getDataFromFirebase();
                        }
                    }
                });


    }

    public void getDataFromFirebase() {
        DatabaseReference usersRef = null;
        final FirebaseUser user = auth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = mDatabase.child("Users");
        usersRef = users.child(user.getUid());


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {


                    User donors = null;
                    for (DataSnapshot msgSnapshot : dataSnapshot.getChildren()) {
                        donors = msgSnapshot.getValue(User.class);
                    }




                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", donors.getName());
                    intent.putExtra("email", donors.getEmail());
                    intent.putExtra("phone", donors.getPhone());
                    intent.putExtra("password", donors.getPassword());
                    finish();
                    startActivity(intent);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
