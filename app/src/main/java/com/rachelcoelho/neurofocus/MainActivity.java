package com.rachelcoelho.neurofocus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTarefas;
    private EditText editTarefa;
    private Button btAdicionar;
    private List<String> listaDeTarefas;
    private TarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Encontrar os itens na tela
        rvTarefas = findViewById(R.id.rv_tarefas);
        editTarefa = findViewById(R.id.edit_tarefa);
        btAdicionar = findViewById(R.id.bt_adicionar);

        // 2. Criar a lista de dados (A MASSA)
        listaDeTarefas = new ArrayList<>();
        listaDeTarefas.add("Tarefa Vermelha 1");
        listaDeTarefas.add("Tarefa Vermelha 2");

        // 3. Configurar o Adaptador (O PADEIRO)
        adapter = new TarefaAdapter(listaDeTarefas);

        // 4. Configurar a Reciclagem (A ESTANTE) - ESSAS 2 LINHAS SÃO O SEGREDO
        rvTarefas.setLayoutManager(new LinearLayoutManager(this));
        rvTarefas.setAdapter(adapter);

        // 5. Configurar o Botão
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = editTarefa.getText().toString();
                if (!texto.isEmpty()) {
                    listaDeTarefas.add(texto);
                    adapter.notifyDataSetChanged(); // Avisa o padeiro!
                    editTarefa.setText("");
                    Toast.makeText(MainActivity.this, "Adicionado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}