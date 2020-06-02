package com.agm.ipmanager.machines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.agm.ipmanager.R;

import java.util.ArrayList;

public class MachinesAdapter extends RecyclerView.Adapter<MachinesAdapter.Holder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Machine> data;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MachinesAdapter(Context context, ArrayList<Machine> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.fragment_machine_row, parent, false);
        return new Holder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Machine machine = data.get(position);
        holder.machineRowName.setText(machine.name);
        String ip = machine.ipv4.isEmpty() ? machine.ipv6 : machine.ipv4;
        holder.machineRowIP.setText(ip);

        if (machine.type.equals("remote")) {
            holder.machineRowIcon.setImageResource(R.drawable.remote);
        } else if (machine.type.equals("local")) {
            holder.machineRowIcon.setImageResource(R.drawable.local);
        } else {
            holder.machineRowIcon.setImageResource(R.drawable.info);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        ConstraintLayout machineRowLayout;
        TextView machineRowName;
        TextView machineRowIP;
        ImageView machineRowIcon;


        public Holder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            machineRowLayout = itemView.findViewById(R.id.machineRowLayout);
            machineRowName = itemView.findViewById(R.id.machineRowName);
            machineRowIP = itemView.findViewById(R.id.machineRowIP);
            machineRowIcon = itemView.findViewById(R.id.machineRowIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
