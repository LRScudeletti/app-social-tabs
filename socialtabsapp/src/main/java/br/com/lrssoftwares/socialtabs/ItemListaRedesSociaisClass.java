package br.com.lrssoftwares.socialtabs;

class ItemListaRedesSociaisClass {

    //region [ VARIAVEIS ]
    private final int id;
    private final String nome;
    private final int posicaoTab;
    private final int corTab;
    private final int ativo;
    //endregion

    //region [ CONSTRUTOR ]
    ItemListaRedesSociaisClass(int id, String nome, int posicaoTab, int corTab, int ativo) {
        this.id = id;
        this.nome = nome;
        this.posicaoTab = posicaoTab;
        this.corTab = corTab;
        this.ativo = ativo;
    }
    //endregion

    //region [ GETS E SETS ]
    public int getId() {
        return id;
    }

    String getNome() {
        return nome;
    }

    int getPosicaoTab() {
        return posicaoTab;
    }

    int getCorTab() {
        return corTab;
    }

    int getAtivo() {
        return ativo;
    }
    //endregion
}