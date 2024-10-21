package com.example.projectprofessor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConsultarProfessorActivity extends AppCompatActivity {

    EditText txtNome;
    Button btnBuscar, btnVoltar;
    RecyclerView recView;
    ProfessorAdapter profAdapter;
    ArrayList<Professor> profList;
    DatabaseHelper dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_professor);

        txtNome = findViewById(R.id.txtBuscaNome);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVoltar = findViewById(R.id.btnVoltar);
        recView = findViewById(R.id.recyclerProfessores);

        dbHelp = new DatabaseHelper(this);
        profList = new ArrayList<>();

        profAdapter = new ProfessorAdapter(profList);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(profAdapter);

        btnBuscar.setOnClickListener(view -> {
                String nome = txtNome.getText().toString();
                profList.clear();
                profList.addAll(dbHelp.buscaPorNome(nome));
                profAdapter.notifyDataSetChanged();
        });

        btnVoltar.setOnClickListener(view -> {
                finish();
        });
    }
}
