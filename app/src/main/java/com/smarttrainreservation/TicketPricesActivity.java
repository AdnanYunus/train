package com.smarttrainreservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarttrainreservation.Models.Train;
import com.smarttrainreservation.Pojo.Seats;
import com.smarttrainreservation.seatBookingrecyclerView.MainActivitySeats;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TicketPricesActivity extends AppCompatActivity {

    TextView arrivedAt, reachedAt, aTime, rTime, distance, tTime, price;
    Button bt_go;
    Train train;
    Seats seats;
    String tClass = "firstClass", tName, passenger, forSeatClass;

    @InjectView(R.id.progress_view)
    CircularProgressView progressView;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_prices);


        ButterKnife.inject(this);


        progressView.setVisibility(View.INVISIBLE);


        tClass = getIntent().getStringExtra("tclass");

        if (getIntent().getStringExtra("tclass").equals("1st Class")) {
            tClass = "firstClass";
            forSeatClass = "First Class";
        } else if (getIntent().getStringExtra("tclass").equals("2nd Class")) {
            tClass = "secondClass";
            forSeatClass = "Second Class";
        } else if (getIntent().getStringExtra("tclass").equals("3rd Class")) {
            tClass = "thirdClass";
            forSeatClass = "Third Class";
        }


        tName = getIntent().getStringExtra("train");
        passenger = getIntent().getStringExtra("passenger");


        arrivedAt = (TextView) findViewById(R.id.arrivedAt);
        reachedAt = (TextView) findViewById(R.id.reachedAt);
        distance = (TextView) findViewById(R.id.distance);
        tTime = (TextView) findViewById(R.id.ttime);
        aTime = (TextView) findViewById(R.id.atime);
        rTime = (TextView) findViewById(R.id.rtime);
        price = (TextView) findViewById(R.id.price);

        bt_go = (Button) findViewById(R.id.btn_book);


        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TicketPricesActivity.this, MainActivitySeats.class);
                intent.putExtra("tName", tName);
                intent.putExtra("tClass", tClass);
                intent.putExtra("passenger", passenger);
                intent.putExtra("arrived", train.getArrivedAt());
                intent.putExtra("reached", train.getReachedAt());
                intent.putExtra("distance", distance.getText());
                intent.putExtra("price", price.getText());
                intent.putExtra("ttime", tTime.getText());
                intent.putExtra("atime", aTime.getText());
                intent.putExtra("rtime", rTime.getText());
                //   intent.putExtra("",);
                // intent.putExtra("",);


                MainActivitySeats.getSeatInformation(seats);
                startActivity(intent);


                finish();

            }
        });
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getUsers();
            }
        }, 3000);

        getSeatInformation(tName, forSeatClass);


    }


    public void getUsers() {
        progressView.setVisibility(View.VISIBLE);

        final DatabaseReference users = mDatabase.child("Trains Information");
        DatabaseReference alldonors = users.child(tName);
        //final DatabaseReference receiver = users.child("GTjrWgpKjoeXUt4JdBJTYP1JkVT2");


        alldonors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                progressView.setVisibility(View.INVISIBLE);
                train = dataSnapshot.getValue(Train.class);

                Log.d("SHAN", "SENDER outennnr" + train);
                Log.d("SHAN", "SENDER outennnr" + train.getName());


                arrivedAt.setText("Arrived At " + train.getArrivedAt());
                aTime.setText(train.getArrivedTime());
                reachedAt.setText("Reach " + train.getReachedAt());
                rTime.setText(train.getReachedTime());
                distance.setText(train.getDistance());
                tTime.setText(train.getTime());

                if (tClass.equals("firstClass"))
                    price.setText(train.getFp());
                else if (tClass.equals("secondClass"))
                    price.setText(train.getSp());
                else if (tClass.equals("thirdClass"))
                    price.setText(train.getTp());


                // Toast.makeText(getContext(), "Ad", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SHAN ", databaseError.getMessage());
                progressView.setVisibility(View.INVISIBLE);
            }
        });


        //  Toast.makeText(getContext(), "in frag metn "+isAdmin, Toast.LENGTH_SHORT).show();
    }

    public void getSeatInformation(String tName, String tClass) {
        progressView.setVisibility(View.VISIBLE);

        final DatabaseReference users = mDatabase.child("Train Seats");
        DatabaseReference TRAIN = users.child(tName);
        DatabaseReference CLASS = TRAIN.child(tClass);

        //final DatabaseReference receiver = users.child("GTjrWgpKjoeXUt4JdBJTYP1JkVT2");

        Query query = CLASS.orderByValue();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                seats = dataSnapshot.getValue(Seats.class);

                Log.d("SHAN", "SENDER outennnr" + seats);
                Log.d("SHAN", "SENDER outennnr" + seats.getEight());


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SHAN ", databaseError.getMessage());
                //  progressView.setVisibility(View.INVISIBLE);
            }
        });


        //  Toast.makeText(getContext(), "in frag metn "+isAdmin, Toast.LENGTH_SHORT).show();
    }
}
