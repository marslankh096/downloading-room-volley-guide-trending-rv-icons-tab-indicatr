package com.sunarsappsjuly20.arslanvideodownloadervideosaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideHolder> {

    private Context context;
    private List<GuideModel> list;

    public GuideAdapter(Context context, List<GuideModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GuideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.guide_item, parent, false);
        return new GuideHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideHolder holder, int position) {

        if (position != 3) {
            holder.top.setVisibility(View.VISIBLE);
            holder.picture.setVisibility(View.VISIBLE);
            holder.last.setVisibility(View.GONE);

            Glide.with(holder.picture)
                    .load(list.get(position).getSliders_drawables())
                    .into(holder.picture);

            holder.number.setText(list.get(position).getNum());
            holder.message.setText(list.get(position).getTv_for_display());
        } else {
            holder.top.setVisibility(View.GONE);
            holder.picture.setVisibility(View.GONE);
            holder.last.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GuideHolder extends RecyclerView.ViewHolder {

        private TextView number;
        private TextView message;
        private ImageView picture;
        private LinearLayout last;
        private LinearLayout top;

        public GuideHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.info_number);
            message = itemView.findViewById(R.id.info_text);
            picture = itemView.findViewById(R.id.info_image);
            last = itemView.findViewById(R.id.last);
            top = itemView.findViewById(R.id.top);
        }
    }

}
