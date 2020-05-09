package com.gymondo.gymondotask.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.gymondo.gymondotask.ExerciseDetailsActivity;
import com.gymondo.gymondotask.R;
import com.gymondo.gymondotask.model.Category;
import com.gymondo.gymondotask.model.Equipments;
import com.gymondo.gymondotask.model.Exercises;
import com.gymondo.gymondotask.model.Images;
import com.gymondo.gymondotask.model.Muscles;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Exercises> exerciseResults;
    private Context context;
    private List<Category> categories;
    private List<Images> images;
    private List<Equipments> equipmentsList;
    private List<Muscles> musclesList;
    private String equipment="";
    private String muscles="";
    private boolean isLoadingAdded = false;

    public ExerciseAdapter(Context context, List<Category> categories, List<Images> images, List<Muscles> muscles, List<Equipments> equipments) {
        this.context = context;
        this.categories=categories;
        this.musclesList=muscles;
        this.equipmentsList=equipments;
        this.images=images;
        exerciseResults = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.exercise_item, parent, false);
        viewHolder = new ExerciseVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Exercises result = exerciseResults.get(position); // Exercise

        switch (getItemViewType(position)) {
            case ITEM:
                final ExerciseVH exerciseVH = (ExerciseVH) holder;
                if(!result.getName().equals(""))
                exerciseVH.exerciseTitle.setText(result.getName());
                else  exerciseVH.exerciseTitle.setText(R.string.no_name);

                for (int i=0;i<categories.size();i++)
                {

                    if (result.getCategory()==categories.get(i).getId())
                    {
                        exerciseVH.exerciseCategory.setText(categories.get(i).getName());
                        break;

                    }

                }
                if(!result.getMuscles().isEmpty()) {
                    for (int i = 0; i < musclesList.size(); i++) {
                        for (int j=0;j<result.getMuscles().size();j++)
                        if (result.getMuscles().get(j) == musclesList.get(i).getId()) {
                            muscles=muscles+musclesList.get(i).getName()+", ";
                            j++;
                        }

                    }
                    exerciseVH.exerciseMuscles.setText(muscles);
                    exerciseVH.exerciseMuscles.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    exerciseVH.exerciseMuscles.setSelected(true);
                }
                else
                    exerciseVH.exerciseMuscles.setText(R.string.no_muscles);


                if(!result.getEquipment().isEmpty()) {
                    for (int i = 0; i < equipmentsList.size(); i++) {
                        for (int j=0;j<result.getEquipment().size();j++)
                            if (result.getEquipment().get(j) == equipmentsList.get(i).getId()) {
                                equipment=equipment+equipmentsList.get(i).getName()+", ";
                                j++;
                            }

                    }
                    exerciseVH.exersiceEquipments.setText(equipment);
                    exerciseVH.exersiceEquipments.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    exerciseVH.exersiceEquipments.setSelected(true);


                }
                else
                    exerciseVH.exersiceEquipments.setText(R.string.no_equipments);

                for (int j=0;j<images.size();j++)
                {
                    if (result.getId()==images.get(j).getExercise())
                    {
                        Picasso.get().load(images.get(j).getImage()).into(exerciseVH.exerciseImg);
                        break;
                    }
                    else Picasso.get().load(R.drawable.no_image).into(exerciseVH.exerciseImg);
                }
                exerciseVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(context, ExerciseDetailsActivity.class);
                        intent.putExtra("Exercise", result);
                        intent.putExtra("images", (Serializable) images);
                        intent.putExtra("muscles",  exerciseVH.exerciseMuscles.getText());
                        intent.putExtra("equipments", exerciseVH.exersiceEquipments.getText());
                        intent.putExtra("category", exerciseVH.exerciseCategory.getText());
                        context.startActivity(intent);
                    }
                });

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return exerciseResults == null ? 0 : exerciseResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == exerciseResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    private void add(Exercises r) {
        exerciseResults.add(r);
        notifyItemInserted(exerciseResults.size() - 1);
    }

    public void addAll(List<Exercises> exerciseResults) {
        for (Exercises result : exerciseResults) {
            add(result);

        }
    }

    private void remove(Exercises r) {
        int position = exerciseResults.indexOf(r);
        if (position > -1) {
            exerciseResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Exercises());
    }

    public void removeLoadingFooter() {

        isLoadingAdded = false;

        int position = exerciseResults.size() - 1;
        Exercises result = getItem(position);
        if (result != null&&position>=0) {
            exerciseResults.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void removeLoadingFooterSearch() {
        isLoadingAdded = false;
        if(LOADING!=1) {
            int position = exerciseResults.size() - 1;
            Exercises result = getItem(position);

            if (result != null) {
                exerciseResults.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    private Exercises getItem(int position) {
        return exerciseResults.get(position);
    }

    protected static class ExerciseVH extends RecyclerView.ViewHolder {
        private TextView exerciseTitle;
        private TextView exerciseMuscles;
        private TextView exersiceEquipments;
        private TextView exerciseCategory;
        private ImageView exerciseImg;

        ExerciseVH(View itemView) {
            super(itemView);

            exerciseTitle = (TextView) itemView.findViewById(R.id.ecersice_title);
            exerciseMuscles = (TextView) itemView.findViewById(R.id.exercise_muscles);
            exersiceEquipments=(TextView)itemView.findViewById(R.id.exercise_equipment);
            exerciseCategory = (TextView) itemView.findViewById(R.id.ex_category);
            exerciseImg = (ImageView) itemView.findViewById(R.id.exercise_image);
        }
    }

    protected static class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public void filter(List<Exercises> newList)
    {

        exerciseResults=new ArrayList<>();
        exerciseResults.addAll(newList);
        notifyDataSetChanged();
    }
}