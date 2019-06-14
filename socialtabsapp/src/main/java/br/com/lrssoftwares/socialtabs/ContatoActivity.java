package br.com.lrssoftwares.socialtabs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ContatoActivity extends AppCompatActivity {

    //region [ VARIAVEIS ]
    private EditText txtMensagem;

    private boolean aplicativoEmailAberto = false;
    //endregion

    //region [ EVENTOS ]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(R.string.tela_contato);
            }

            txtMensagem = findViewById(R.id.txtDescricao);

        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reportar_erro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnEnviarErro:
                CarregarMensagem();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicativoEmailAberto = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        aplicativoEmailAberto = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (aplicativoEmailAberto) {
            Toast.makeText(ContatoActivity.this, getString(R.string.mensagem_sucesso), Toast.LENGTH_SHORT).show();

            txtMensagem.getText().clear();
        }
    }
    //endregion

    //region [ METODOS ]
    private void CarregarMensagem() {
        try {
            if (new UtilidadesClass().verificarConexao(getBaseContext())) {
                if (!txtMensagem.getText().toString().equals("")) {

                    Intent erros = getIntent();

                    String destino = "rogerio.scudeletti@gmail.com";
                    String modelo = Build.MODEL;
                    String versao = Build.VERSION.RELEASE;
                    String mensagem = txtMensagem.getText().toString();

                    String erro = erros.getStringExtra("erro");

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{destino});
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.tela_contato));
                    intent.setType("message/rfc822");

                    if (erro != null && !erro.equals("")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(("<p>" + getString(R.string.modelo_dispositivo) + "<br>" + modelo + "</p>" + "<p>" + getString(R.string.versao_android) + "<br>" + versao + "</p>" + "<p>" + getString(R.string.mensagem) + "<br>" + mensagem + "</p>") + "<p>" + getString(R.string.erro) + "<br>" + erro + "</p>", Html.FROM_HTML_MODE_LEGACY));
                        else
                            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(("<p>" + getString(R.string.modelo_dispositivo) + "<br>" + modelo + "</p>" + "<p>" + getString(R.string.versao_android) + "<br>" + versao + "</p>" + "<p>" + getString(R.string.mensagem) + "<br>" + mensagem + "</p>") + "<p>" + getString(R.string.erro) + "<br>" + erro + "</p>"));
                    }
                    else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(("<p>" + getString(R.string.mensagem) + "<br>" + mensagem + "</p>"), Html.FROM_HTML_MODE_LEGACY));
                        else
                            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(("<p>" + getString(R.string.mensagem) + "<br>" + mensagem + "</p>")));
                    }

                    startActivityForResult(Intent.createChooser(intent, getString(R.string.aplicacao_email)), 0);

                } else {
                    Toast.makeText(ContatoActivity.this, getString(R.string.preencha_campos), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ContatoActivity.this, getString(R.string.verificar_conexao), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }
    //endregion
}


