package br.com.lrssoftwares.socialtabs;

class RedesSociaisClass {
    //region [ VARIÁVEIS ]
    private int id;
    private String nome;
    private String url;
    private String cor;
    private int posicao;
    private int ativo;
    //endregion

    //region [ GETS E SETS ]
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
    //endregion
}