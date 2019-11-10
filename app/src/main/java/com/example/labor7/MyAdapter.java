package com.example.labor7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> users;

    public MyAdapter(Context c, ArrayList<User> u)
    {
        context = c;
        users = u;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_user,parent,false));
    }

    // binds the data that we want to display with the viewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        holder.loc.setText(users.get(position).getDate());
        holder.date.setText(users.get(position).getName());
        holder.gendr.setText(users.get(position).getGender());
        holder.hobby.setText((CharSequence) users.get(position).getHobby());
        holder.departm.setText(users.get(position).getDepartment());
        holder.year.setText(users.get(position).getYearOfStudy());
        holder.exp.setText(users.get(position).getExpectations());
        Picasso.get().load(users.get(position).getImageUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, loc, date, gendr, hobby, departm, year, exp;
        ImageView img;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewNev);
            loc = (TextView) itemView.findViewById(R.id.textViewHelyseg);
            date = (TextView) itemView.findViewById(R.id.textViewDatum);
            gendr = (TextView) itemView.findViewById(R.id.textViewNem);
            hobby = (TextView) itemView.findViewById(R.id.textViewHobbi);
            departm = (TextView) itemView.findViewById(R.id.textViewSzak);
            year = (TextView) itemView.findViewById(R.id.textViewHanyadEves);
            exp = (TextView) itemView.findViewById(R.id.textViewMegjegyzese);
            img = (ImageView) itemView.findViewById(R.id.imageViewPic);
        }
    }
}
