package com.rachelcoelho.neurofocus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    // MUDANÇA 1: A lista agora é de objetos "Tarefa", não mais apenas textos
    private List<Tarefa> listaDeTarefas;

    public TarefaAdapter(List<Tarefa> tarefas) {
        this.listaDeTarefas = tarefas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarefa, parent, false);
        return new TarefaViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        // MUDANÇA 2: Pegamos o objeto Tarefa inteiro
        Tarefa tarefa = listaDeTarefas.get(position);

        // MUDANÇA 3: Extraímos apenas o nome para exibir na tela
        holder.txtTitulo.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {
        return listaDeTarefas.size();
    }

    public class TarefaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txt_titulo_tarefa);
        }
    }
}