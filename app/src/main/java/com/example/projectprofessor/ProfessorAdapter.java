package com.example.projectprofessor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHol> {

    private ArrayList<Professor> profList;

    public ProfessorAdapter(ArrayList<Professor> profList) {
        this.profList = profList;
    }

    public static class ViewHol extends RecyclerView.ViewHolder {
        TextView tvNome, tvMatricula, tvIdade, tvSalario;

        public ViewHol(View itemVie) {
            super(itemVie);
            tvNome = itemVie.findViewById(R.id.tvNome);
            tvMatricula = itemVie.findViewById(R.id.tvMatricula);
            tvIdade = itemVie.findViewById(R.id.tvIdade);
            tvSalario = itemVie.findViewById(R.id.tvSalario);
        }
    }

    @Override
    public ViewHol onCreateViewHolder(ViewGroup par, int viewTyp) {
        View vie = LayoutInflater.from(par.getContext()).inflate(R.layout.item_professor, par, false);
        return new ViewHol(vie);
    }

    @Override
    public void onBindViewHolder(ViewHol hol, int pos) {
        Professor prof = profList.get(pos);
        hol.tvNome.setText(prof.getNome());
        hol.tvMatricula.setText(prof.getMatricula());
        hol.tvIdade.setText(String.valueOf(prof.getIdade()));
        hol.tvSalario.setText(String.format("%.2f", prof.calcSalario()));
    }

    @Override
    public int getItemCount() {
        return profList.size();
    }
}
