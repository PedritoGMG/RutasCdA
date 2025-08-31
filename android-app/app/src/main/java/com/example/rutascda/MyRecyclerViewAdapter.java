package com.example.rutascda;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutascda.v2.activities.ActividadesActivity;
import com.example.rutascda.v2.entities.Actividad;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Actividad> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClicklListener;

    // datos de cada elemento
    public MyRecyclerViewAdapter(Context contexto, ArrayList<Actividad> data) {
        this.mInflater = LayoutInflater.from(contexto);
        this.mData = data;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View parten = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(parten);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Actividad actividad = mData.get(position);
        holder.myTextView.setText(actividad.getNombre());

        //TODO Realmente se pode la misma imagen para todas
        Integer idActividad = actividad.getActividadID();
        holder.myImageView.setImageResource(getImagen(idActividad > 4 ? ((idActividad-1) % 4) : idActividad-1));

    }
    private @DrawableRes int getImagen(int id){
        @DrawableRes int img = 0;
        switch (id) {
            case 0:
                img = R.mipmap.img_puerta;
                break;
            case 1:
                img = R.mipmap.img_paisaje;
                break;
            case 2:
                img = R.mipmap.img_casopla;
                break;
            case 3:
                img = R.mipmap.img_rio;
                break;
            default:
                img = R.mipmap.ic_launcher;
                break;
        }
        return img;
    }
    public int getItemCount(){
        return mData.size();
    }

    public void setClickListener(ActividadesActivity mainActivity) {
        mClicklListener = mainActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        public LinearLayout myLinearLayout;
        TextView myTextView;
        ImageView myImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView1);
            myImageView = itemView.findViewById(R.id.imageView1);
            myLinearLayout = itemView.findViewById(R.id.linearLayoutRutas);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setClickListener (ItemClickListener itemClickListener) {
            mClicklListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            if (mClicklListener!=null)
                mClicklListener.onItemClick(view, getAdapterPosition());
        }
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // Agrega los elementos al menú contextual
            /*
            MenuItem marcarCompletado = contextMenu.add("Marcar como Completado");
            MenuItem marcarFaltante = contextMenu.add("Marcar como Faltante");
            MenuItem reiniciar = contextMenu.add("Reiniciar");

            // Guarda la posición del elemento
            int position = getAdapterPosition();

            // Asigna el listener para cada elemento del menú contextual
            marcarCompletado.setOnMenuItemClickListener(item -> {
                if (mClicklListener != null && mClicklListener instanceof MainActivity) {
                    ((ActividadesActivity) mClicklListener).onMenuItemClick(item, position);
                    myLinearLayout.setBackgroundResource(R.drawable.bordergreen);
                    return true;
                }
                return false;
            });

            marcarFaltante.setOnMenuItemClickListener(item -> {
                if (mClicklListener != null && mClicklListener instanceof MainActivity) {
                    ((ActividadesActivity) mClicklListener).onMenuItemClick(item, position);
                    myLinearLayout.setBackgroundResource(R.drawable.borderred);
                    return true;
                }
                return false;
            });

            reiniciar.setOnMenuItemClickListener(item -> {
                if (mClicklListener != null && mClicklListener instanceof MainActivity) {
                    ((ActividadesActivity) mClicklListener).onMenuItemClick(item, position);
                    myLinearLayout.setBackgroundResource(R.drawable.borderwhite);
                    return true;
                }
                return false;
            });*/
        }
    }
}
