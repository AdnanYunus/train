package com.smarttrainreservation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarttrainreservation.Models.Train;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EnterTrainInformation extends AppCompatActivity {


    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.arrivedat)
    EditText arrivedat;
    @InjectView(R.id.reachedat)
    EditText reachedat;
    @InjectView(R.id.time)
    EditText time;
    @InjectView(R.id.distance)
    EditText distance;
    @InjectView(R.id.firstclass)
    EditText firstclass;
    @InjectView(R.id.fp)
    EditText fp;
    @InjectView(R.id.secondclass)
    EditText secondclass;
    @InjectView(R.id.sp)
    EditText sp;
    @InjectView(R.id.thirdclass)
    EditText thirdclass;
    @InjectView(R.id.aTime)
    EditText aTime;
    @InjectView(R.id.rTime)
    EditText rTime;
    @InjectView(R.id.tp)
    EditText tp;
    @InjectView(R.id.btn_save)
    Button btn_save;


    FirebaseAuth auth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_train_information);
        ButterKnife.inject(this);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveTrins();
                break;

        }
    }


    public void saveTrins() {


        DatabaseReference donorsRef = mDatabase.child("Trains Information");
        //  DatabaseReference usersRef = donorsRef.child(name.getText().toString());

        Train train = new Train();
        train.setName(name.getText().toString());
        train.setArrivedAt(arrivedat.getText().toString());
        train.setReachedAt(reachedat.getText().toString());
        train.setDistance(distance.getText().toString());
        train.setTime(time.getText().toString());
        train.setFirstClass(firstclass.getText().toString());
        train.setSecondClass(secondclass.getText().toString());
        train.setThirdClass(thirdclass.getText().toString());
        train.setFp(fp.getText().toString());
        train.setSp(sp.getText().toString());
        train.setTp(tp.getText().toString());
        train.setArrivedTime(aTime.getText().toString());
        train.setReachedTime(rTime.getText().toString());
        //   String userIdd = usersRef.push().getKey();

        donorsRef.child(name.getText().toString()).setValue(train);


        // progressView.setVisibility(View.INVISIBLE);

        Toast.makeText(EnterTrainInformation.this, "Great train saved!!", Toast.LENGTH_SHORT).show();

        //finish();
        //startActivity(new Intent(SignupActivity.this, MainActivity.class));


    }

}
