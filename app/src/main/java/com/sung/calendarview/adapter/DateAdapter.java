package com.sung.calendarview.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sung.calendarview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sung on 2017/7/19.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder>{
    public List<DateObject> dates = new ArrayList<>();

    public DateAdapter(List<DateObject> dates) {
        if (dates != null)
            this.dates = dates;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_calendar_item, parent, false);
        DateViewHolder viewHolder = new DateViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        DateObject date = dates.get(position);
        holder.day.setText(dates.get(position).day+"");
        if (date.day == 1) {
            if (date.currentMonth)
                holder.month.setText((date.month)+"月");
            else
                holder.month.setText((date.month+1)+"月");

            //多重保险
            if (holder.day.getText().toString().trim().equals("1"))
                holder.month.setVisibility(View.VISIBLE);
        }

        if (!date.currentMonth){
            holder.root.setAlpha(0.3f);
        }else {
            holder.day.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void setDates(List dates, boolean reset){
        if (dates == null)
            return;

        if (reset){
            this.dates.clear();
        }
        this.dates.addAll(dates);
        this.notifyDataSetChanged();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout root;
        public TextView day;
        public TextView month;

        public DateViewHolder(View view){
            super(view);
            root = (RelativeLayout) view.findViewById(R.id.calendar_item_root);
            day = (TextView) view.findViewById(R.id.calendar_item_text_day);
            month = (TextView) view.findViewById(R.id.calendar_item_text_month);
        }
    }
}
