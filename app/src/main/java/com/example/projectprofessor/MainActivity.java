/*
 *@author:<Wallace Moura Machado de Oliveira;1110482413004>
 */

package com.example.projectprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddProfessor = findViewById(R.id.btnCriarProfessor);
        Button btnViewProfessores = findViewById(R.id.btnConsultarProfessor);

        btnAddProfessor.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CriarProfessorActivity.class);
            startActivity(intent);
        });

        btnViewProfessores.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConsultarProfessorActivity.class);
            startActivity(intent);
        });
    }
}
