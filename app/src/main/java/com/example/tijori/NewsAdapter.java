package com.example.tijori;

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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<HelperClass> list;
    private Context context;

    public NewsAdapter(ArrayList<HelperClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = (list.size() - 1) - pos;
        HelperClass model = list.get(position);
        Picasso.get().load(model.getNewsimage()).placeholder(R.drawable.safebox).into(holder.itemNewsImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemNewsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNewsImage = itemView.findViewById(R.id.item_news_image);
        }
    }
}
