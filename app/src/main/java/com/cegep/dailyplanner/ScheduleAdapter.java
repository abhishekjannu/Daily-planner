package com.cegep.dailyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    List<SchedulePojo> schedules;

    public ScheduleAdapter() {
        schedules = new ArrayList<>();
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        SchedulePojo schedule = schedules.get(position);

        holder.description.setText(schedule.getDescription());
        holder.date.setText(schedule.getDate());
        holder.time.setText(schedule.getTime());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public void setSchedules(List<SchedulePojo> schedules) {
        if (schedules == null)
            schedules = new ArrayList<>();

        if (this.schedules == null)
            this.schedules = new ArrayList<>();

        this.schedules.clear();
        this.schedules.addAll(schedules);

        notifyDataSetChanged();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView date, time, description;
        public ScheduleViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.schedule_date);
            time = itemView.findViewById(R.id.schedule_time);
            description = itemView.findViewById(R.id.schedule_description);
        }
    }
}

