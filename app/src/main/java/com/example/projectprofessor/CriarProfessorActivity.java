package com.example.projectprofessor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CriarProfessorActivity extends AppCompatActivity {

    EditText txtNome, txtMatricula, txtIdade;
    RadioGroup rgTipo;
    RadioButton rbTitular, rbHorista;
    LinearLayout layTitular, layHorista;
    EditText txtAno, txtSalario;
    EditText txtHora, txtValor;
    Button btnSalvar, btnVoltar;
    DatabaseHelper dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_professor);

        txtNome = findViewById(R.id.txtNome);
        txtMatricula = findViewById(R.id.txtMatricula);
        txtIdade = findViewById(R.id.txtIdade);

        rgTipo = findViewById(R.id.rgTipoProfessor);
        rbTitular = findViewById(R.id.rbTitular);
        rbHorista = findViewById(R.id.rbHorista);

        layTitular = findViewById(R.id.layoutTitular);
        layHorista = findViewById(R.id.layoutHorista);

        txtAno = findViewById(R.id.txtAnosInstituicao);
        txtSalario = findViewById(R.id.txtSalarioBase);

        txtHora = findViewById(R.id.txtHorasAula);
        txtValor = findViewById(R.id.txtValorHoraAula);

        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        dbHelp = new DatabaseHelper(this);

        rgTipo.setOnCheckedChangeListener((rg, checkId) -> {
                if (checkId == R.id.rbTitular) {
                    layTitular.setVisibility(View.VISIBLE);
                    layHorista.setVisibility(View.GONE);
                } else if (checkId == R.id.rbHorista) {
                    layTitular.setVisibility(View.GONE);
                    layHorista.setVisibility(View.VISIBLE);
                }
        });

        btnSalvar.setOnClickListener(view -> {
                String nome = txtNome.getText().toString();
                String matricula = txtMatricula.getText().toString();
                int idade = Integer.parseInt(txtIdade.getText().toString());

                if (rbTitular.isChecked()) {
                    int anos = Integer.parseInt(txtAno.getText().toString());
                    double salario = Double.parseDouble(txtSalario.getText().toString());

                    ProfessorTitular prof = new ProfessorTitular(nome, matricula, idade, anos, salario);

                    dbHelp.inserirProfessorTitular(prof);

                } else if (rbHorista.isChecked()) {
                    int horas = Integer.parseInt(txtHora.getText().toString());
                    double valor = Double.parseDouble(txtValor.getText().toString());

                    ProfessorHorista prof = new ProfessorHorista(nome, matricula, idade, horas, valor);

                    dbHelp.inserirProfessorHorista(prof);
                }

                Toast.makeText(CriarProfessorActivity.this, "Professor salvo com sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
        });

        btnVoltar.setOnClickListener((view) -> {
                finish();
        });
    }

    private void limparCampos() {
        txtNome.setText("");
        txtMatricula.setText("");
        txtIdade.setText("");
        txtAno.setText("");
        txtSalario.setText("");
        txtHora.setText("");
        txtValor.setText("");
        rgTipo.clearCheck();
        layTitular.setVisibility(View.GONE);
        layHorista.setVisibility(View.GONE);
    }
}
