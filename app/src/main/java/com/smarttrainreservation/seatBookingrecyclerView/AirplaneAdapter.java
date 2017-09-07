package com.smarttrainreservation.seatBookingrecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarttrainreservation.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AirplaneAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {

    String passenger = "shan";
    int[] number;
    int len = 0;
    int a = 111, b = 111, c = 111, d = 111, e = 111, f = 111, g = 111, h = 111, i = 111, k = 111;
    private OnSeatSelected mOnSeatSelected;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<EdgeItem> mItems;

    public AirplaneAdapter(Context context, List<EdgeItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    public void getPassenger(String va) {
        this.passenger = va;
        randomNumber(Integer.parseInt(passenger));
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
        return new EdgeViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {


        //  int type = mItems.get(position);


        final EdgeItem item = (EdgeItem) mItems.get(position);
        final EdgeViewHolder holder = (EdgeViewHolder) viewHolder;


        if (position == a) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == b) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == c) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == d) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == e) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == f) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == g) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == h) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == i) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }
        if (position == k) {
            holder.imgSeatSelected.setVisibility(View.VISIBLE);
        }

        holder.imgSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleSelection(position);
                mOnSeatSelected.onSeatSelected(getSelectedItemCount(), Integer.parseInt(holder.seat_number.getText().toString()));


            }
        });


        mOnSeatSelected.onSeatSelectedList(number);

      /*  if(len<=number.length) {

            if (position == number[len]) {
                holder.imgSeat.setVisibility(View.INVISIBLE);
                holder.imgSeatSelected.setVisibility(View.VISIBLE);
                holder.seat_book.setVisibility(View.INVISIBLE);
                Log.d("SHAN", " in if parrt" + position) ;

            } else {
                holder.imgSeat.setVisibility(View.VISIBLE);
               // holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
                Log.d("SHAN", " in else" + position);
            }
        }else {
            holder.imgSeat.setVisibility(View.VISIBLE);
        }*/


        // holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        holder.seat_number.setText("" + (position + 1));


    }

    public void randomNumber(int nu) {


        int num;
        Random re = new Random();
        num = re.nextInt(50);

        number = new int[nu];
        int count = 0;
        //int num;

        Random r = new Random();
        while (count < number.length) {
            num = r.nextInt(47);
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
        Arrays.sort(number);

        for (int j = 0; j < number.length; j++) {


            if (j == 0) {
                a = number[j];
            }
            if (j == 1) {
                b = a+1;
            }
            if (j == 2) {
                c = b+1;
            }
            if (j == 3) {
                d = c+1;
            }
            if (j == 4) {
            //if (j == 4) {
                e = d+1;
            }
            if (j == 5) {
                f = e+1;
            }
            if (j == 6) {
                g = f+1;
            }
            if (j == 7) {
                h = g+1;
            }
            if (j == 8) {
                i = h+1;
            }
            if (j == 9) {
                k = i+1;
            }


            Log.d("SHAN", "RANDOM Adapter****" + number[j]);
            //System.out.print(number[j] + " ");
        }
    }

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgSeatSelected;
        ImageView imgSeat, seat_book;
        TextView seat_number;

        public EdgeViewHolder(View itemView) {
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            seat_book = (ImageView) itemView.findViewById(R.id.img_book);
            seat_number = (TextView) itemView.findViewById(R.id.seat_number);

        }

    }

}
