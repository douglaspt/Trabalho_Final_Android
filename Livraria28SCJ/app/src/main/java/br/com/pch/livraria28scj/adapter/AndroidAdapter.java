package br.com.pch.livraria28scj.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import br.com.pch.livraria28scj.MenuActivity;
import br.com.pch.livraria28scj.NovoActivity;
import br.com.pch.livraria28scj.R;
import br.com.pch.livraria28scj.dao.LivroDAO;
import br.com.pch.livraria28scj.listener.OnItemClickListener;
import br.com.pch.livraria28scj.model.Livro;

/**
 * Created by douglas.teixeira on 10/04/2017.
 */

public class AndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Livro> data = Collections.emptyList();

    private OnItemClickListener clickListener;

    public AndroidAdapter(Context context, List<Livro> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_livro,parent,false);
        AndroidItemHolder holder = new AndroidItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        AndroidItemHolder myHolder = (AndroidItemHolder) holder;
        Livro current = data.get(position);

        String idLivro = Integer.toString(current.getId());
        myHolder.tvId.setText("ID: "+idLivro);
        myHolder.tvTitulo.setText(current.getTitulo());
        myHolder.tvAutor.setText(context.getString(R.string.label_autor)  + current.getAutor());
        myHolder.tvCategoria.setText(context.getString(R.string.label_categoria) + current.getCategoria().getNome());

        myHolder.ibDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Livro livro = data.get(position);
                LivroDAO livroDAO = new LivroDAO(context);
                livroDAO.remove(livro);
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,data.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Livro getItem(int position){
        return data.get(position);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    private class AndroidItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvId;
        TextView tvTitulo;
        TextView tvAutor;
        TextView tvCategoria;
        ImageButton ibDelete;

        private AndroidItemHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvAutor = (TextView) itemView.findViewById(R.id.tvAutor);
            tvCategoria = (TextView) itemView.findViewById(R.id.tvCategoria);
            ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }

    }
}
