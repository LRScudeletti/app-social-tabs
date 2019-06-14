package br.com.lrssoftwares.socialtabs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

class UtilidadesClass {

    boolean verificarConexao(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null;
    }

    AlertDialog.Builder cabecalhoDialogo(Activity activity, String tituloDialogo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        TextView titulo = new TextView(activity);

        titulo.setText(tituloDialogo);
        titulo.setBackgroundColor(Color.parseColor("#0c69ac"));
        titulo.setPadding(20, 20, 20, 20);
        titulo.setGravity(Gravity.CENTER);
        titulo.setTextColor(Color.WHITE);
        titulo.setTextSize(18);

        builder.setCustomTitle(titulo);

        return builder;
    }

    void carregarMensagem(Activity activity, String mensagem) {
        Toast toast = Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT);
        TextView v = toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    void enviarMensagemContato(final Activity activity, final Exception erro) {
        new android.app.AlertDialog.Builder(activity)
                .setTitle(R.string.mensagem_erro_titulo)
                .setMessage(R.string.mensagem_erro)
                .setPositiveButton(R.string.botao_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, ContatoActivity.class);
                        intent.putExtra("erro", erro.getMessage());
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.botao_cancelar, null)
                .show();
    }
}



