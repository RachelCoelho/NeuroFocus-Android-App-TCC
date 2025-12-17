package com.rachelcoelho.neurofocus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    // Configurações do Banco
    public static int VERSION = 1;
    public static String NOME_DB = "DB_NEUROFOCUS";
    public static String TABELA_TAREFAS = "tarefas";

    // O Construtor (quem é chamado para abrir o banco)
    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    // Esse método roda UMA VEZ só, quando o app é instalado
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Aqui criamos a tabela (como se fosse uma aba do Excel)
        // Ela tem 2 colunas:
        // 1. id: Um número de crachá automático (1, 2, 3...)
        // 2. nome: O texto da tarefa

        String sql = "CREATE TABLE " + TABELA_TAREFAS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL );";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }

    // Esse método roda se a gente mudar a versão do banco no futuro
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se mudar a versão, a gente apaga a tabela velha e cria uma nova
        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + ";";
        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar App");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar App" + e.getMessage());
        }
    }
}