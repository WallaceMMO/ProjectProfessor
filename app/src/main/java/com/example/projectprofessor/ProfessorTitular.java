package com.example.projectprofessor;

public class ProfessorTitular extends Professor {
    private int anosInstituicao;
    
    public ProfessorTitular(String nome, String matricula, int idade, int anosInstituicao, double salario) {
        super(nome, matricula, idade);
        this.anosInstituicao = anosInstituicao;
        super.setSalario(salario);
        super.setSalario(calcSalario());
    }

    public int getAnosInstituicao() {
        return anosInstituicao;
    }

    public void setAnosInstituicao(int anosInstituicao) {
        this.anosInstituicao = anosInstituicao;
    }



    @Override
    public double calcSalario() {
        return super.getSalario() * (1.0 + (anosInstituicao / 5) * 0.05);
    }
}
