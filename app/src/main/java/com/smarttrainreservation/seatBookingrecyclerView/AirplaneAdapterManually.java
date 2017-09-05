package com.smarttrainreservation.seatBookingrecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttrainreservation.R;

import java.util.List;

public class AirplaneAdapterManually extends SelectableAdapter<RecyclerView.ViewHolder> {

    String passenger = "shan";
    int[] number;

    private OnSeatSelected mOnSeatSelected;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<EdgeItem> mItems;
    int totalSeats=0;
    int current=0;

    public AirplaneAdapterManually(Context context, List<EdgeItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }


    public  void setTotal(int a){
        this.totalSeats=a;
    }
    public  void setTotalCurrent(int a){
        this.current=a;
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


        holder.imgSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current<totalSeats){
                    toggleSelection(position);

                    mOnSeatSelected.onSeatSelected(getSelectedItemCount(),Integer.parseInt(holder.seat_number.getText().toString()));
                }else {
                    Toast.makeText(mContext, "You can only select "+totalSeats+" seats", Toast.LENGTH_SHORT).show();
                }



            }
        });


        holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        holder.seat_number.setText("" + (position + 1));


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
