package com.sung.calendarview.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sung.calendarview.R;
import com.sung.calendarview.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sung on 2017/7/19.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> implements View.OnClickListener{
    public List<DateObject> dates = new ArrayList<>();
    private onCalendarDayClick onCalendarDayClickListner;

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
        Log.d(holder.toString());

        holder.position = position;
        DateObject date = dates.get(position);
        holder.day.setText(dates.get(position).day+"");
        holder.root.setOnClickListener(this);
        holder.root.setTag(holder);

        //设置月初
        if (date.day == 1) {
            if (date.currentMonth)
                holder.month.setText((date.month)+"月");
            else
                holder.month.setText((date.month+1)+"月");

            //多重保险
            if (holder.day.getText().toString().trim().equals("1"))
                holder.month.setVisibility(View.VISIBLE);
        }

        //设置当月高亮
        if (!date.currentMonth){
            holder.root.setAlpha(0.3f);
        }else {
            holder.day.setTextColor(Color.BLACK);
        }

        //设置选中状态
        setSellectStatus(position,holder);

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

    @Override
    public void onClick(View view) {
        if (view instanceof RelativeLayout){
            DateViewHolder holder = (DateViewHolder) view.getTag();
            if (!dates.get(holder.position).currentMonth)
                return;

            dates.get(holder.position).sellectStatus = !dates.get(holder.position).sellectStatus;

            this.notifyDataSetChanged();
            onCalendarDayClickListner.onClick(holder.position);
        }
    }

    /**
    * 根据前后的选中状态判断图片的状态
    * @param holder holder操作ui
    * @param position 游标
    * */
    private void setSellectStatus(int position, DateViewHolder holder){
        DateObject date = dates.get(position);
        if (!date.currentMonth)
            return;

        ImageView img = holder.sellect;
        TextView day = holder.day;

        if (date.sellectStatus){
            day.setTextColor(Color.WHITE);
        }else {
            day.setTextColor(Color.BLACK);
        }

        if (position == 0){
            if (date.sellectStatus && dates.get(position + 1).sellectStatus)
                img.setImageResource(R.drawable.calendar_sellector_left);
            else if (date.sellectStatus && !dates.get(position + 1).sellectStatus)
                img.setImageResource(R.drawable.calendar_sellector_single);

            return;
        }

        if (position == 34){
            if (date.sellectStatus && dates.get(position - 1).sellectStatus)
                img.setImageResource(R.drawable.calendar_sellector_right);
            else if (date.sellectStatus && !dates.get(position - 1).sellectStatus)
                img.setImageResource(R.drawable.calendar_sellector_single);

            return;
        }

        DateObject dateLeft = dates.get(position-1);
        DateObject dateRight = dates.get(position+1);

        if (dateLeft.sellectStatus && date.sellectStatus && dateRight.sellectStatus){
            img.setImageResource(R.drawable.calendar_sellector_center);
        }

        if (!dateLeft.sellectStatus && date.sellectStatus && dateRight.sellectStatus){
            img.setImageResource(R.drawable.calendar_sellector_left);
        }

        if (dateLeft.sellectStatus && !date.sellectStatus && dateRight.sellectStatus){
            img.setImageResource(0);
        }

        if (dateLeft.sellectStatus && date.sellectStatus && !dateRight.sellectStatus){
            img.setImageResource(R.drawable.calendar_sellector_right);
        }

        if (!dateLeft.sellectStatus && date.sellectStatus && !dateRight.sellectStatus){
            img.setImageResource(R.drawable.calendar_sellector_single);
        }
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout root;
        public ImageView sellect;
        public TextView day;
        public TextView month;
        public int position;

        public DateViewHolder(View view){
            super(view);
            position = 0;
            root = (RelativeLayout) view.findViewById(R.id.calendar_item_root);
            sellect = (ImageView) view.findViewById(R.id.calendar_item_sellect);
            day = (TextView) view.findViewById(R.id.calendar_item_text_day);
            month = (TextView) view.findViewById(R.id.calendar_item_text_month);
        }
    }

    public void setOnCalendarDayClickListner(onCalendarDayClick onCalendarDayClickListner){
        this.onCalendarDayClickListner = onCalendarDayClickListner;
    }

    public interface onCalendarDayClick{
        void onClick(int position);
    }
}
