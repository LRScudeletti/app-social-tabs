package br.com.lrssoftwares.socialtabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    //region [ VARIAVEIS ]
    private static final String nomeBase = "BaseDados_SocialTabs";
    private static final int versaoBase = 2;
    //endregion

    SQLiteOpenHelper(Context contexto) {
        super(contexto, nomeBase, null, versaoBase);
    }

    //region [ EVENTOS ]
    @Override
    public void onCreate(SQLiteDatabase sdBaseDados) {
        // Criando tabela redessociais
        sdBaseDados.execSQL("create table redessociais (_id integer primary key autoincrement, nome text not null, url text not null, cor text not null, posicao int not null, ativo int not null);");

        // Inserindo as redes sociais padrões
        sdBaseDados.execSQL("insert into redessociais values (1, 'Ask.fm', 'https://ask.fm/login', '#db3552', 1, 0);");
        sdBaseDados.execSQL("insert into redessociais values (2, 'Facebook', 'https://web.facebook.com/', '#3b5999', 2, 1);");
        sdBaseDados.execSQL("insert into redessociais values (3, 'Flickr', 'https://www.flickr.com/login', '#ff0084', 3, 0);");
        sdBaseDados.execSQL("insert into redessociais values (4, 'Foursquare', 'https://www.foursquare.com/login', '#f94877', 4, 0);");
        sdBaseDados.execSQL("insert into redessociais values (5, 'Google+', 'https://plus.google.com/', '#dd4b39', 5, 0);");
        sdBaseDados.execSQL("insert into redessociais values (6, 'Instagram', 'https://www.instagram.com', '#e4405f', 6, 1);");
        sdBaseDados.execSQL("insert into redessociais values (7, 'Linkedin', 'https://www.linkedin.com', '#0077B5', 7, 0);");
        sdBaseDados.execSQL("insert into redessociais values (8, 'Messenger', 'https://www.messenger.com', '#0084ff', 8, 0);");
        sdBaseDados.execSQL("insert into redessociais values (9, 'Mix', 'https://www.mix.com/login', '#eb4924', 9, 0);");
        sdBaseDados.execSQL("insert into redessociais values (10, 'Pinterest', 'https://pinterest.com/', '#bd081c', 10, 0);");
        sdBaseDados.execSQL("insert into redessociais values (11, 'Quora', 'https://www.quora.com/', '#b92b27', 11, 0);");
        sdBaseDados.execSQL("insert into redessociais values (12, 'Reddit', 'https://www.reddit.com', '#ff5700', 12, 0);");
        sdBaseDados.execSQL("insert into redessociais values (13, 'SoundCloud', 'https://m.soundcloud.com/', '#ff3300', 13, 0);");
        sdBaseDados.execSQL("insert into redessociais values (14, 'Tumblr', 'https://tumblr.com/', '#34465d', 14, 0);");
        sdBaseDados.execSQL("insert into redessociais values (15, 'Twitter', 'https://twitter.com', '#55acee', 15, 1);");
        sdBaseDados.execSQL("insert into redessociais values (16, 'VK', 'https://m.vk.com/', '#4c75a3', 16, 0);");
        sdBaseDados.execSQL("insert into redessociais values (17, 'Vimeo', 'https://vimeo.com/login', '#1ab7ea', 17, 0);");
        sdBaseDados.execSQL("insert into redessociais values (18, 'Weibo', 'https://m.weibo.cn/', '#df2029', 18, 0);");

        // Criando tabela parâmetros
        sdBaseDados.execSQL("create table parametros (_id integer primary key autoincrement, nome text, ativo int)");

        // Inserindo parâmetro Tela Cheia
        sdBaseDados.execSQL("insert into parametros values (1, 'Tela Cheia', 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdBaseDados, int versaoAnterior, int versaoAtual) {
        switch (versaoAnterior) {
            case 1:
                // Criando tabela parâmetros
                sdBaseDados.execSQL("create table parametros (_id integer primary key autoincrement, nome text, ativo int)");

                // Inserindo parâmetro Tela Cheia
                sdBaseDados.execSQL("insert into parametros values (1, 'Tela Cheia', 0);");
                break;
        }
    }
    //endregion
}
