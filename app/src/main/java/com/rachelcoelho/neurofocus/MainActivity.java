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

    // Novas peças importantes:
    private TarefaAdapter adapter;
    private List<Tarefa> listaDeTarefas = new ArrayList<>(); // Lista de objetos Tarefa
    private TarefaDAO dao; // O nosso gerente de banco de dados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar componentes visuais
        rvTarefas = findViewById(R.id.rv_tarefas);
        editTarefa = findViewById(R.id.edit_tarefa);
        btAdicionar = findViewById(R.id.bt_adicionar);

        // 2. Inicializar o Banco de Dados
        dao = new TarefaDAO(getApplicationContext());

        // 3. Configurar a Lista (RecyclerView)
        // O Adapter começa com a lista vazia, vamos encher ela já já
        adapter = new TarefaAdapter(listaDeTarefas);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvTarefas.setLayoutManager(layoutManager);
        rvTarefas.setHasFixedSize(true);
        rvTarefas.setAdapter(adapter);

        // ... (código anterior do rvTarefas.setAdapter)

        // 5. Configurar o evento de clique na lista
        rvTarefas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        rvTarefas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // Clique rápido (podemos usar para editar no futuro)
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // Clique Longo: DELETAR

                                // 1. Identificar qual tarefa foi clicada
                                Tarefa tarefaSelecionada = listaDeTarefas.get(position);

                                // 2. Criar o alerta de confirmação
                                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                                dialog.setPositiveButton("Sim", (dialogInterface, i) -> {

                                    // 3. Deletar do Banco de Dados
                                    if (dao.deletar(tarefaSelecionada)) {
                                        carregarLista(); // Atualiza a tela
                                        Toast.makeText(getApplicationContext(), "Tarefa excluída!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Erro ao excluir!", Toast.LENGTH_SHORT).show();
                                    }

                                });

                                dialog.setNegativeButton("Não", null);

                                // Exibir a janela
                                dialog.create();
                                dialog.show();
                            }
                        }
                )
        );

        // 4. Ação do Botão Adicionar
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pegar o que foi digitado
                String nomeTarefa = editTarefa.getText().toString();

                if (!nomeTarefa.isEmpty()) {
                    // Criar o objeto Tarefa
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(nomeTarefa);

                    // Salvar no Banco de Dados
                    if (dao.salvar(tarefa)) {
                        Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!", Toast.LENGTH_SHORT).show();

                        // Atualizar a lista na tela
                        carregarLista();

                        // Limpar o campo de texto
                        editTarefa.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Digite uma tarefa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Este método roda automaticamente toda vez que a tela aparece (início ou volta)
    @Override
    protected void onStart() {
        super.onStart();
        carregarLista();
    }

    // Método especial para buscar dados no banco e atualizar a tela
    public void carregarLista() {
        // 1. Pedir ao DAO a lista atualizada do banco
        listaDeTarefas = dao.listar();

        // 2. Criar um novo adaptador com essa lista nova
        /* (Dica técnica: Existem jeitos mais otimizados, mas esse é o mais seguro para garantir que atualiza) */
        adapter = new TarefaAdapter(listaDeTarefas);
        rvTarefas.setAdapter(adapter);
    }
}