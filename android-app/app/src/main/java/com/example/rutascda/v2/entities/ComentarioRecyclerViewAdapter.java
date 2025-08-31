package com.example.rutascda.v2.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutascda.ItemClickListener;
import com.example.rutascda.R;
import com.example.rutascda.v2.activities.ActividadesActivity;

import java.util.List;

public class ComentarioRecyclerViewAdapter extends RecyclerView.Adapter<ComentarioRecyclerViewAdapter.ViewHolder> {
    private List<Comentario> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClicklListener;

    // datos de cada elemento
    public ComentarioRecyclerViewAdapter(Context contexto, List<Comentario> data) {
        this.mInflater = LayoutInflater.from(contexto);
        this.mData = data;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View parten = mInflater.inflate(R.layout.recycler_view_item_1, parent, false);
        return new ViewHolder(parten);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comentario review = mData.get(position);
        holder.textView.setText(review.getName());
        holder.textView2.setText(review.getTexto());
        holder.textView3.setText(review.getFecha());
        int imagen = 0;
        switch (review.getValoracion()) {
            case 1:
                imagen = R.drawable.v1;
                break;
            case 2:
                imagen = R.drawable.v2;
                break;
            case 3:
                imagen = R.drawable.v3;
                break;
            case 4:
                imagen = R.drawable.v4;
                break;
            case 5:
                imagen = R.drawable.v5;
                break;
        }
        holder.imageView2.setImageResource(imagen);
    }
    public int getItemCount(){
        return mData.size();
    }

    public void insertNewElement(Comentario elemento){
        mData.add(elemento);
        this.notifyItemInserted(mData.size());
    }
    public void deleteElement(int index){
        mData.remove(index);
        this.notifyItemRemoved(index);
    }
    public void deleteAll(){
        int size = mData.size();
        mData.clear();
        this.notifyItemRangeRemoved(0, size);
    }
    public void modifyElement(int index, Comentario elemento){
        int size = mData.size();
        mData.set(index, elemento);
        this.notifyItemChanged(index);
    }
    public List<Comentario> getData() {
        return mData;
    }

    public void setClickListener(ActividadesActivity mainActivity) {
        mClicklListener = mainActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        TextView textView2;
        TextView textView3;
        ImageView imageView2;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView2 = itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(this);
        }

        public void setClickListener (ItemClickListener itemClickListener) {
            mClicklListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            if (mClicklListener!=null)
                mClicklListener.onItemClick(view, getAdapterPosition());
        }
    }
}
