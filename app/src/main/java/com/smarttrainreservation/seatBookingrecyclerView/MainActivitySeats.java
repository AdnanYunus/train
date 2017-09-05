package com.smarttrainreservation.seatBookingrecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarttrainreservation.Models.Invoice;
import com.smarttrainreservation.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;

public class MainActivitySeats extends AppCompatActivity implements OnSeatSelected {

    private static final int COLUMNS = 5;
    RelativeLayout invoice, nic;
    Button btn_confirm, bt_confirm_nic;
    String tName, tClass, passenger, arrived, reached, distance, price, ttime, atime, rtime;
    AirplaneAdapter adapter;
    int[] myseats;
    EditText edt_nic, edt_card, edt_number;
    TextView txt_totalseats, txt_tName, txt_tclass, txt_seatsnumbers, txt_reached, txt_arrived, txt_dep, txt_cdate, txt_nic, txt_number, txt_price;
    String otherString, old = ":";
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    int firSeat=0;

    CircularProgressView progressView;
    private TextView txtSeatSelected, txt_manually;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);



        progressView= (CircularProgressView) findViewById(R.id.progress_view);
        progressView.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        edt_card = (EditText) findViewById(R.id.edt_card);
        edt_nic = (EditText) findViewById(R.id.edt_nic);
        edt_number = (EditText) findViewById(R.id.edt_number);
        bt_confirm_nic = (Button) findViewById(R.id.btn_confirm_nic);
        // recipet


        btn_confirm = (Button) findViewById(R.id.btn_confim);

        txt_totalseats = (TextView) findViewById(R.id.txt_totalseats);
        txt_tName = (TextView) findViewById(R.id.txt_trainname);
        txt_tclass = (TextView) findViewById(R.id.txt_tclass);
        txt_seatsnumbers = (TextView) findViewById(R.id.txt_seatsnumbers);
        txt_arrived = (TextView) findViewById(R.id.txt_arrived);
        txt_reached = (TextView) findViewById(R.id.txt_reached);
        txt_dep = (TextView) findViewById(R.id.txt_dep);
        txt_cdate = (TextView) findViewById(R.id.txt_cdate);
        txt_nic = (TextView) findViewById(R.id.txt_nic);
        txt_number = (TextView) findViewById(R.id.txt_number);
        txt_price = (TextView) findViewById(R.id.price);


        tName = getIntent().getStringExtra("tName");
        tClass = getIntent().getStringExtra("tClass");
        passenger = getIntent().getStringExtra("passenger");
        arrived = getIntent().getStringExtra("arrived");
        reached = getIntent().getStringExtra("reached");
        distance = getIntent().getStringExtra("distance");
        price = getIntent().getStringExtra("price");
        ttime = getIntent().getStringExtra("ttime");
        atime = getIntent().getStringExtra("atime");
        rtime = getIntent().getStringExtra("rtime");

        txtSeatSelected = (TextView) findViewById(R.id.txt_seat_selected);
        txt_manually = (TextView) findViewById(R.id.txt_manually);

        txt_manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySeats.this, MainActivitySeatsManually.class);
                intent.putExtra("tName", tName);
                intent.putExtra("tClass", tClass);
                intent.putExtra("passenger", passenger);
                intent.putExtra("arrived", arrived);
                intent.putExtra("reached", reached);
                intent.putExtra("distance", distance);
                intent.putExtra("price", price);
                intent.putExtra("ttime", ttime);
                intent.putExtra("atime", atime);
                intent.putExtra("rtime", rtime);

                //   intent.putExtra("",);
                // intent.putExtra("",);


                startActivity(intent);

                finish();
            }
        });
        txtSeatSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nic.setVisibility(View.VISIBLE);

            }
        });


        invoice = (RelativeLayout) findViewById(R.id.invoice);

        nic = (RelativeLayout) findViewById(R.id.nic);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveInvoice();


            }
        });
        bt_confirm_nic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int a = 0; a < myseats.length; a++) {

                    if(a==0) {
                        firSeat = myseats[a];
                        old = old + "A" + firSeat + " ";
                    }
                    else {
                        old = old + "A" + (firSeat+a) + " ";
                    }
                }
//hello 

                if (edt_nic.getText().length() < 1) {
                    edt_nic.setError("Enter your NIC number");
                } else if (edt_card.getText().toString().length() < 1) {
                    edt_card.setError("Enter your card details");
                } else if (edt_number.getText().toString().length() < 1) {
                    edt_number.setError("Enter your number");
                } else {


                    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    Calendar c = Calendar.getInstance();

                    String sDate = c.get(Calendar.YEAR) + "-"
                            + c.get(Calendar.MONTH)
                            + "-" + c.get(Calendar.DAY_OF_MONTH);


                    txt_totalseats.setText(myseats.length + "");
                    txt_tName.setText(tName);
                    txt_seatsnumbers.setText(old);
                    txt_arrived.setText(arrived);
                    txt_reached.setText(reached);
                    txt_dep.setText(mydate);
                    txt_cdate.setText(sDate);
                    txt_nic.setText(edt_nic.getText().toString());
                    txt_number.setText(edt_number.getText().toString());
                    txt_price.setText(price + "X" + myseats.length + "=" + (Integer.parseInt(price) * myseats.length));
                    txt_tclass.setText(tClass);
                    nic.setVisibility(View.GONE);
                    invoice.setVisibility(View.VISIBLE);

                }


            }
        });


        List<EdgeItem> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            EdgeItem edgeItem = new EdgeItem();
            edgeItem.setName("1" + 1);
            items.add(edgeItem);

        }

        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
        recyclerView.setLayoutManager(manager);

        adapter = new AirplaneAdapter(this, items);
        adapter.getPassenger(passenger);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSeatSelected(int count, int se) {

        //  txtSeatSelected.setText("Book "+count+" seats");
    }

    @Override
    public void onSeatSelectedList(int[] a) {
        myseats = a;
        Log.d("SHAN", "SEat selected size" + a.length);
    }

    public void saveInvoice() {

        progressView.setVisibility(View.VISIBLE);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference donorsRef = mDatabase.child("Invoices");
        DatabaseReference usersRef = donorsRef.child(firebaseUser.getUid());

        Invoice invoice = new Invoice();
        invoice.setContact(txt_number.getText().toString());
        invoice.setDep(txt_dep.getText().toString());
        invoice.setFrom(arrived);
        invoice.setIssue(txt_cdate.getText().toString());
        invoice.setNic(txt_nic.getText().toString());
        invoice.setSeatsNumber(txt_seatsnumbers.getText().toString());
        invoice.setTo(reached);
        invoice.setTotalSeats(txt_totalseats.getText().toString());
        invoice.setTrainName(txt_tName.getText().toString());
        invoice.setTrainClass(txt_tclass.getText().toString());
        invoice.setTotalPrice(txt_price.getText().toString());


        String userIdd = usersRef.push().getKey();

        usersRef.child(userIdd).setValue(invoice);


        progressView.setVisibility(View.INVISIBLE);

        Toast.makeText(MainActivitySeats.this, "Thanks for booking !", Toast.LENGTH_SHORT).show();

        finish();
        //startActivity(new Intent(SignupActivity.this, MainActivity.class));


    }
}
