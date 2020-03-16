package fundamental;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import interfaces.ItemClickListener;
import objects.Brief;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Brief[] mDataSet;
    ItemClickListener itemClickListener;
    //private AdapterView.OnItemClickListener onItemClickListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        private ItemClickListener itemClickListener;
        public TextView title, year, imdbId, type;
        public MyViewHolder(@NonNull View itemView, ImageView imageView, TextView title, TextView year, TextView imdbId, TextView type) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.imageView = imageView;
            this.title = title;
            this.year = year;
            this.imdbId = imdbId;
            this.type = type;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }
    }
    public MyAdapter(Brief[] myDataSet){
        mDataSet = myDataSet;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_brief, parent, false);
        ImageView img = view.findViewById(R.id.imPoster);
        TextView ti =  view.findViewById(R.id.tvTitle);
        TextView ye =  view.findViewById(R.id.tvYear);
        TextView im =  view.findViewById(R.id.tvImdbID);
        TextView ty =  view.findViewById(R.id.tvType);
        MyViewHolder vh = new MyViewHolder(view, img, ti, ye, im, ty);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText("Title: "+mDataSet[position].getTitle());
        holder.year.setText("Year: "+mDataSet[position].getYear());
        holder.type.setText("Type: "+mDataSet[position].getType());
        holder.imdbId.setText("ImdbID: "+mDataSet[position].getImdbID());
        holder.setItemClickListener(itemClickListener);
        Picasso.get().load(mDataSet[position].getPoster()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
