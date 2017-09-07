package com.smarttrainreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    String selectTrain = "Select Train";
    String selectClass = "Select Class";
    String selectSeats = "Select Seats";
    Button btn_ticket;
    EditText edt_passenger;
    TextView txt_schedule;
    String ghnta, mnt, secd, time = " ", myPoints, timeSchedule;
    DateFormat f1, f2;
    Date d;
    private int yearg = 0, monthg = 0, dayg = 0, hourg = 0, mintg = 0, secg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_schedule = (TextView) findViewById(R.id.schedule);
        txt_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MainActivity.this,

                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Datepickerdialog");


            }
        });
        int num;
        Random re = new Random();
        num = re.nextInt(50);
        Log.d("SHAN","check"+num);

        /*int[] number = new int[1];
        ArrayList<Integer> integers = new ArrayList<>();
        int count = 0;


        Random r = new Random();

        while (count < number.length) {
            num = r.nextInt(50);
            boolean repeat = false;
            do {
                for (int i = 0; i < number.length; i++) {
                    if (num == number[i]) {
                        repeat = true;
                        break;
                    } else if (i == count) {
                        number[count] = num;
                        count++;
                        repeat = true;
                        break;
                    }
                }
            } while (!repeat);

        }

        Arrays.sort(number);*/




        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        btn_ticket = (Button) findViewById(R.id.btn_ticket);
        // edt_passenger = (EditText) findViewById(R.id.edt_passenger);


        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectTrain.equals("Select Train")) {
                    Toast.makeText(MainActivity.this, "Select Your Train", Toast.LENGTH_SHORT).show();
                } else if (selectClass.equals("Select Class")) {
                    Toast.makeText(MainActivity.this, "Select Your Train Class", Toast.LENGTH_SHORT).show();
                } else if (selectSeats.equals("Select Seats")) {

                    Toast.makeText(MainActivity.this, "Select Your Seats", Toast.LENGTH_SHORT).show();

                } else if (txt_schedule.getText().equals("Departure Date")) {

                    Toast.makeText(MainActivity.this, "Select your departure date", Toast.LENGTH_SHORT).show();

                } else {


                    Intent intent = new Intent(MainActivity.this, TicketPricesActivity.class);
                    intent.putExtra("train", selectTrain);
                    intent.putExtra("tclass", selectClass);
                    intent.putExtra("passenger", selectSeats);
                    intent.putExtra("date", txt_schedule.getText());
                    startActivity(intent);
                }


            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Select Train");
        //  categories.add("Udarata Menike");
        categories.add("Podi Menike");
        //categories.add("Tikiri Menike");
        //categories.add("Senkadagala Menike");
        //categories.add("Yal Devi");
        //categories.add("Uttara Dewi");
        //categories.add("Udaya Devi");
        categories.add("Rajarata Rajina");
        categories.add("Ruhunu Kumari");
        categories.add("Samudra Devi");


        List<String> categories2 = new ArrayList<String>();
        categories2.add("Select Class");
        categories2.add("1st Class");
        categories2.add("2nd Class");
        categories2.add("3rd Class");

        List<String> categories3 = new ArrayList<String>();
        categories3.add("Select Seats");
        categories3.add("1");
        categories3.add("2");
        categories3.add("3");
        categories3.add("4");
        categories3.add("5");
        categories3.add("6");
        categories3.add("7");
        categories3.add("8");
        categories3.add("9");
        categories3.add("10");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectTrain = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner2.setAdapter(dataAdapter2);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectClass = spinner2.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner3.setAdapter(dataAdapter3);


        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selectSeats = spinner3.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        yearg = year;
        monthg = monthOfYear + 1;
        dayg = dayOfMonth;


        String s = "12:18:00";

        if (hourg < 10) {
            time = "0" + hourg;
        } else time = "" + hourg;
        if (mintg < 10) {
            time = time + ":" + "0" + mintg;
        } else time = time + ":" + mintg;
        if (secg < 10) {
            time = time + ":" + "0" + secg;
        } else time = time + ":" + secg;


        String ss = hourg + ":" + mintg + "  " + secg;

        f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)

        try {
            d = f1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        f2 = new SimpleDateFormat("h:mm a");
        // f2.format(d).toLowerCase(); // "12:18am"

        timeSchedule = dayg + "-" + monthg + "-" + yearg;

        Calendar calander = Calendar.getInstance();

        if (yearg < calander.get(Calendar.YEAR)) {
            Toast.makeText(this, "Date is not correct", Toast.LENGTH_SHORT).show();
            txt_schedule.setText("Departure Date");
        } else if (monthg < calander.get(Calendar.MONTH) + 1) {
            Toast.makeText(this, "Date is not correct", Toast.LENGTH_SHORT).show();
            txt_schedule.setText("Departure Date");
        } else if (monthg >= calander.get(Calendar.MONTH) + 1 && dayg < calander.get(Calendar.DAY_OF_MONTH)) {
            Toast.makeText(this, "Date is not correct", Toast.LENGTH_SHORT).show();
            txt_schedule.setText("Departure Date");
        } else {
            txt_schedule.setText(timeSchedule);

        }


    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        hourg = hourOfDay;
        mintg = minute;
        secg = second;


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivity.this,

                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(getFragmentManager(), "Datepickerdialog");

    }
}
