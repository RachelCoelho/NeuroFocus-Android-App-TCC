package com.rachelcoelho.neurofocus;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // 1. Encontrar os componentes na tela pelos IDs que criamos
        android.widget.EditText editTarefa = findViewById(R.id.edit_tarefa);
        android.widget.Button btAdicionar = findViewById(R.id.bt_adicionar);

// 2. Criar o ouvinte do clique (O que acontece quando clica)
        btAdicionar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Pega o texto que foi digitado
                String textoDigitado = editTarefa.getText().toString();

                // Mostra uma mensagem na tela (Toast)
                android.widget.Toast.makeText(MainActivity.this, "Tarefa salva: " + textoDigitado, android.widget.Toast.LENGTH_LONG).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}