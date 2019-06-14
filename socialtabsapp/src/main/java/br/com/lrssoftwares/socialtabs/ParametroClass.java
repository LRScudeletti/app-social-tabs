package br.com.lrssoftwares.socialtabs;

class ParametroClass {
    //region [ VARIAVEIS ]
    private int id;
    private String nome;
    private int ativo;
    //endregion

    //region [ GETS E SETS ]
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getNome() {
        return nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    int getAtivo() {
        return ativo;
    }

    void setAtivo(int ativo) {
        this.ativo = ativo;
    }
    //endregion
}
