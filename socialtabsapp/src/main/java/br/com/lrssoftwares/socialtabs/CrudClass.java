package br.com.lrssoftwares.socialtabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

class CrudClass {
    private SQLiteDatabase sqLiteDatabase;
    private final SQLiteOpenHelper baseDadosSQLiteOpenHelper;

    CrudClass(Context contexto) {
        baseDadosSQLiteOpenHelper = new SQLiteOpenHelper(contexto);
    }

    //region [ REDES SOCIAIS ]
    // Inserir
    void inserirRedeSocial(RedesSociaisClass redesSociaisClass) {
        sqLiteDatabase = baseDadosSQLiteOpenHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", redesSociaisClass.getNome());
        valores.put("url", redesSociaisClass.getUrl());
        valores.put("cor", redesSociaisClass.getCor());
        valores.put("posicao", redesSociaisClass.getPosicao());
        valores.put("ativo", redesSociaisClass.getAtivo());

        sqLiteDatabase.insert("redessociais", null, valores);
        sqLiteDatabase.close();
    }

    // Atualizar
    void atualizarRedeSocial(RedesSociaisClass redesSociaisClass) {
        sqLiteDatabase = baseDadosSQLiteOpenHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("cor", redesSociaisClass.getCor());
        valores.put("posicao", redesSociaisClass.getPosicao());
        valores.put("ativo", redesSociaisClass.getAtivo());

        sqLiteDatabase.update("redessociais", valores, "_id = ?", new String[]{"" + redesSociaisClass.getId()});
        sqLiteDatabase.close();
    }

    // Excluir
    void excluirRedeSocial(int id) {
        sqLiteDatabase = baseDadosSQLiteOpenHelper.getWritableDatabase();

        sqLiteDatabase.delete("redessociais", "_id = ?", new String[]{"" + id});
        sqLiteDatabase.close();
    }

    // Listar
    List<RedesSociaisClass> listarRedesSociais(int ordenacao) {
        sqLiteDatabase = baseDadosSQLiteOpenHelper.getWritableDatabase();

        List<RedesSociaisClass> lista = new ArrayList<>();

        Cursor cursor;

        // se for 0, lista tudo, senão lista somente os ativos
        if (ordenacao == 0)
            cursor = sqLiteDatabase.query("redessociais", null, null, null, null, null, "nome COLLATE NOCASE ASC;");
        else
            cursor = sqLiteDatabase.query("redessociais", null, "ativo = ?", new String[]{"" + 1}, null, null, "posicao ASC;");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                RedesSociaisClass redesSociaisClass = new RedesSociaisClass();
                redesSociaisClass.setId(cursor.getInt(0));
                redesSociaisClass.setNome(cursor.getString(1));
                redesSociaisClass.setUrl(cursor.getString(2));
                redesSociaisClass.setCor(cursor.getString(3));
                redesSociaisClass.setPosicao(cursor.getInt(4));
                redesSociaisClass.setAtivo(cursor.getInt(5));
                lista.add(redesSociaisClass);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return lista;
    }
    //endregion

    //region [ PARÂMETRO ]
    List<ParametroClass> PesquisarParametro() {
        sqLiteDatabase = baseDadosSQLiteOpenHelper.getWritableDatabase();

        List<ParametroClass> lista = new ArrayList<>();
        String[] colunas = new String[]{"_id", "nome", "ativo"};

        Cursor cursor = sqLiteDatabase.query("parametros", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                ParametroClass parametroClass = new ParametroClass();
                parametroClass.setId(cursor.getInt(0));
                parametroClass.setNome(cursor.getString(1));
                parametroClass.setAtivo(cursor.getInt(2));
                lista.add(parametroClass);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return lista;
    }

    void AtualizarParametro(ParametroClass parametroClass) {
        sqLiteDatabase = baseDadosSQLiteOpenHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", parametroClass.getNome());
        valores.put("ativo", parametroClass.getAtivo());

        sqLiteDatabase.update("parametros", valores, "_id = ?", new String[]{"" + parametroClass.getId()});

        sqLiteDatabase.close();
    }
    //endregion
}
