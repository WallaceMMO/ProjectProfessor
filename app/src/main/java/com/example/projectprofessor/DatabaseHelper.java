package com.example.projectprofessor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NOME = "professores.db";
    private static final int DB_VERSAO = 1;

    // Tabela comum de Professor
    private static final String TABELA_PROF = "professor";
    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_MAT = "matricula";
    private static final String COL_IDADE = "idade";
    private static final String COL_TIPO = "tipo";

    // Campos para Professor Titular
    private static final String TABELA_TIT = "professor_titular";
    private static final String COL_ANOS = "anos_instituicao";
    private static final String COL_SAL = "salario_base";

    // Campos para Professor Horista
    private static final String TABELA_HOR = "professor_horista";
    private static final String COL_HOR = "horas_aula";
    private static final String COL_VAL = "valor_hora_aula";

    public DatabaseHelper(Context context) {
        super(context, DB_NOME, null, DB_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela comum de Professor
        String criaTabProf = "CREATE TABLE " + TABELA_PROF + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT, " +
                COL_MAT + " TEXT, " +
                COL_IDADE + " INTEGER, " +
                COL_TIPO + " TEXT)";
        db.execSQL(criaTabProf);

        // Tabela Professor Titular
        String criaTabTit = "CREATE TABLE " + TABELA_TIT + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ANOS + " INTEGER, " +
                COL_SAL + " REAL, " +
                "FOREIGN KEY(" + COL_ID + ") REFERENCES " + TABELA_PROF + "(" + COL_ID + "))";
        db.execSQL(criaTabTit);

        // Tabela Professor Horista
        String criaTabHor = "CREATE TABLE " + TABELA_HOR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HOR + " INTEGER, " +
                COL_VAL + " REAL, " +
                "FOREIGN KEY(" + COL_ID + ") REFERENCES " + TABELA_PROF + "(" + COL_ID + "))";
        db.execSQL(criaTabHor);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROF);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_TIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_HOR);
        onCreate(db);
    }

    // Inserir Professor Titular
    public void inserirProfessorTitular(ProfessorTitular prof) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valProf = new ContentValues();
        valProf.put(COL_NOME, prof.getNome());
        valProf.put(COL_MAT, prof.getMatricula());
        valProf.put(COL_IDADE, prof.getIdade());
        valProf.put(COL_TIPO, "Titular");

        long id = db.insert(TABELA_PROF, null, valProf);

        ContentValues valTit = new ContentValues();
        valTit.put(COL_ID, id);
        valTit.put(COL_ANOS, prof.getAnosInstituicao());
        valTit.put(COL_SAL, prof.getSalario());

        db.insert(TABELA_TIT, null, valTit);
    }

    // Inserir Professor Horista
    public void inserirProfessorHorista(ProfessorHorista prof) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valProf = new ContentValues();
        valProf.put(COL_NOME, prof.getNome());
        valProf.put(COL_MAT, prof.getMatricula());
        valProf.put(COL_IDADE, prof.getIdade());
        valProf.put(COL_TIPO, "Horista");

        long id = db.insert(TABELA_PROF, null, valProf);

        ContentValues valHor = new ContentValues();
        valHor.put(COL_ID, id);
        valHor.put(COL_HOR, prof.getHorasAula());
        valHor.put(COL_VAL, prof.getValorHoraAula());

        db.insert(TABELA_HOR, null, valHor);
    }

    // Buscar professores pelo nome
    public ArrayList<Professor> buscaPorNome(String nome) {
        ArrayList<Professor> listaProf = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PROF + " WHERE " + COL_NOME + " LIKE ?", new String[]{"%" + nome + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex(COL_TIPO));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                @SuppressLint("Range") String nomeProf = cursor.getString(cursor.getColumnIndex(COL_NOME));
                @SuppressLint("Range") String matr = cursor.getString(cursor.getColumnIndex(COL_MAT));
                @SuppressLint("Range") int idade = cursor.getInt(cursor.getColumnIndex(COL_IDADE));

                if ("Titular".equals(tipo)) {
                    Cursor cursorTit = db.rawQuery("SELECT * FROM " + TABELA_TIT + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});
                    if (cursorTit.moveToFirst()) {
                        @SuppressLint("Range") int anos = cursorTit.getInt(cursorTit.getColumnIndex(COL_ANOS));
                        @SuppressLint("Range") double salario = cursorTit.getDouble(cursorTit.getColumnIndex(COL_SAL));
                        ProfessorTitular profTit = new ProfessorTitular(nomeProf, matr, idade, anos, salario);
                        listaProf.add(profTit);
                    }
                    cursorTit.close();
                } else if ("Horista".equals(tipo)) {
                    Cursor cursorHor = db.rawQuery("SELECT * FROM " + TABELA_HOR + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});
                    if (cursorHor.moveToFirst()) {
                        @SuppressLint("Range") int horas = cursorHor.getInt(cursorHor.getColumnIndex(COL_HOR));
                        @SuppressLint("Range") double valorHora = cursorHor.getDouble(cursorHor.getColumnIndex(COL_VAL));
                        ProfessorHorista profHor = new ProfessorHorista(nomeProf, matr, idade, horas, valorHora);
                        listaProf.add(profHor);
                    }
                    cursorHor.close();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaProf;
    }
}
