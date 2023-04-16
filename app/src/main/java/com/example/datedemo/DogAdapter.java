package com.example.datedemo;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datedemo.databinding.LayoutDogitemBinding;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {

    List<Dog> DogList;
    OnItemClickListener onItemClickListener;

    public DogAdapter(List<Dog> dogList, OnItemClickListener onItemClickListener) {
        DogList = dogList;
        this.onItemClickListener = onItemClickListener;
    }

    public DogAdapter(List<Dog> dogList) {
        DogList  = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //previous method without view binding - inflate external layout, get view
        // use view to create view holder.

        //with view binding - get the binding object of external layout, and use the binding object to
        //create holder object
        //******//
        //Inflate the binding
        //using binding create holder object
        //set up click listener on the holder itemView
        //****//

        LayoutDogitemBinding binding = LayoutDogitemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        DogViewHolder holder = new DogViewHolder(binding);
        /*
        // recycler view'e tiklaninca ne olacagini belirtiyor !
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onItemClickListener.onItemClick(holder.getAdapterPosition());

            }
        });
        */

        return holder;


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {

        holder.binding.txtViewId.setText(String.valueOf(DogList.get(position).getId()));
        holder.binding.txtViewBreed.setText(DogList.get(position).getDogBreed());
        holder.binding.txtViewName.setText(DogList.get(position).getDogName());
        holder.binding.imgViewDogPic.setImageResource(DogList.get(position).getDogPicDrawable());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        String dobStr = formatter.format(DogList.get(position).getDob());
        holder.binding.txtViewDOB.setText(dobStr);



    }

    @Override
    public int getItemCount() {
        return DogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {

        //We will set up the binding as a property of the ViewHolder
        //in order to access views inside it in onBindViewHolder

        //Customize the constructor to use binding
        //instead of View to create the holder object

        LayoutDogitemBinding binding;



        public DogViewHolder(LayoutDogitemBinding itemBinding) {

            super(itemBinding.getRoot());
            binding = itemBinding; //set the class data, which is binding object

        }
    }
    public interface OnItemClickListener {
        public void onItemClick(int i);
    }


}
