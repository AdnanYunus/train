package com.smarttrainreservation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarttrainreservation.Helper.Validations;
import com.smarttrainreservation.Models.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {


    @InjectView(R.id.edt_name)
    EditText edt_name;
    @InjectView(R.id.edt_number)
    EditText edt_number;
    @InjectView(R.id.edt_email)
    EditText edt_email;
    @InjectView(R.id.edt_pass)
    EditText edt_pass;
   /* @InjectView(R.id.txt_logo)
    TextView txt_logo;*/
    @InjectView(R.id.btn_next)
    Button btn_next;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.cv_add)
    CardView cvAdd;
    @InjectView(R.id.progress_view)
    CircularProgressView progressView;


    FirebaseAuth auth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressView.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        }
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        }
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                SignupActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    if(validations()){
                        userSignup(edt_email.getText().toString(),edt_pass.getText().toString());
                    }

                  // startActivity(new Intent(this, MainActivity.class), options.toBundle());
                } else {


                    if(validations()){
                        userSignup(edt_email.getText().toString(),edt_pass.getText().toString());
                    }
                   // startActivity(new Intent(this, MainActivity.class));
                }


        }
    }

    public boolean validations() {

        if (edt_name.getText().toString().length() < 1) {
            edt_name.setError("Name can't be empty");
            return false;

        } else if (edt_email.getText().toString().length() < 1) {
            edt_email.setError(" Email can't be empty");
            return false;
        } else if (!(Validations.isEmailValid(edt_email.getText().toString()))) {
            edt_email.setError("Email is not valid");
            return false;
        } else if (edt_number.getText().toString().length() < 1) {
            edt_number.setError("Number can't be empty");
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

    public void userSignup(String email, String password) {



        progressView.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            progressView.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } else {

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            DatabaseReference donorsRef = mDatabase.child("Users");
                            DatabaseReference usersRef = donorsRef.child(firebaseUser.getUid());

                            User user = new User();
                            user.setName(edt_name.getText().toString());
                            user.setEmail(edt_email.getText().toString());
                            user.setPhone(edt_number.getText().toString());
                            user.setPassword(edt_pass.getText().toString());
                            String userIdd = usersRef.push().getKey();
                            usersRef.child(userIdd).setValue(user);


                            progressView.setVisibility(View.INVISIBLE);

                           // Toast.makeText(SignupActivity.this, "Great!!", Toast.LENGTH_SHORT).show();

                            finish();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        }
                    }
                });

    }
}
