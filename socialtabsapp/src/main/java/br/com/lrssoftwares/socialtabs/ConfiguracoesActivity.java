package br.com.lrssoftwares.socialtabs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracoesActivity extends AppCompatActivity implements RecyclerViewButtonClickInterface, RecyclerViewImageButtonClickInterface {

    //region [ VARIAVEIS ]
    private RecyclerView rvConfiguracoesRedesSociais;
    private AlertDialog dialogoAtivo;
    private CrudClass crudClass;
    private boolean salvou = false;
    //endregion

    //region [ EVENTOS ]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(R.string.tela_configuracoes);
            }

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            rvConfiguracoesRedesSociais = findViewById(R.id.rvConfiguracoesRedesSociais);
            rvConfiguracoesRedesSociais.setHasFixedSize(true);
            rvConfiguracoesRedesSociais.setNestedScrollingEnabled(false);
            rvConfiguracoesRedesSociais.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            LinearLayoutManager llmExercicios = new LinearLayoutManager(this);
            llmExercicios.setOrientation(LinearLayoutManager.VERTICAL);

            rvConfiguracoesRedesSociais.setLayoutManager(llmExercicios);

            crudClass = new CrudClass(this);

            carregarConfiguracoes();
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
        return true;
    }

    @SuppressLint("InflateParams")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.itemSalvarTodos) {

                Parcelable recyclerViewEstado = rvConfiguracoesRedesSociais.getLayoutManager().onSaveInstanceState();

                boolean validarRedeSocial = false;

                for (int i = 0; i < rvConfiguracoesRedesSociais.getChildCount(); ++i) {
                    List<RedesSociaisClass> listarRedesSociais = crudClass.listarRedesSociais(1);

                    RedesSociaisAdapter.ViewHolder holder = (RedesSociaisAdapter.ViewHolder) rvConfiguracoesRedesSociais.getChildViewHolder(rvConfiguracoesRedesSociais.getChildAt(i));
                    int ativo = holder.swtRedeSocial.isChecked() ? 1 : 0;

                    if (listarRedesSociais.size() == 1 && ativo == 0) {
                        validarRedeSocial = true;
                        new UtilidadesClass().carregarMensagem(this, getString(R.string.mensagem_alterar_validacao));
                        break;
                    } else {
                        int idRedeSocial = Integer.parseInt(holder.txtIdLista.getText().toString());

                        int posicaoTab;

                        if (holder.txtPosicaoTab.getText().toString().equals(""))
                            posicaoTab = 1;
                        else
                            posicaoTab = Integer.parseInt(holder.txtPosicaoTab.getText().toString());
                        atualizarRedeSocial(idRedeSocial, ativo, posicaoTab, holder.btnAlterarCorTab);
                    }
                }

                if (!validarRedeSocial)
                    new UtilidadesClass().carregarMensagem(this, getString(R.string.rede_social_alterada_sucesso));

                carregarConfiguracoes();

                rvConfiguracoesRedesSociais.getLayoutManager().onRestoreInstanceState(recyclerViewEstado);
            }

            if (id == R.id.itemAdicionar) {

                AlertDialog.Builder dialogAdicionarRedeSocial = new UtilidadesClass().cabecalhoDialogo(this, getString(R.string.popup_adicionar_rede_social));

                LayoutInflater inflaterAdicionarRedeSocial = getLayoutInflater();
                final View viewLayoutDialogo = inflaterAdicionarRedeSocial.inflate(R.layout.activity_adicionar_rede_social, null);

                final FloatingActionButton btnCorTabNovo = viewLayoutDialogo.findViewById(R.id.btnCorTab);
                btnCorTabNovo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carregarPaletaCores(btnCorTabNovo);
                    }
                });

                dialogAdicionarRedeSocial.setView(viewLayoutDialogo)
                        .setPositiveButton(R.string.botao_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton(R.string.botao_cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialogoAtivo.dismiss();
                            }
                        });

                dialogoAtivo = dialogAdicionarRedeSocial.create();
                dialogoAtivo.show();

                dialogoAtivo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText txtNomeRedeSocial = viewLayoutDialogo.findViewById(R.id.txtNomeRedeSocial);
                        EditText txtUrlRedeSocial = viewLayoutDialogo.findViewById(R.id.txtEnderecoRedeSocial);
                        EditText txtPosicaoTab = viewLayoutDialogo.findViewById(R.id.txtPosicaoTab);

                        if (!txtNomeRedeSocial.getText().toString().equals("") &&
                                !txtUrlRedeSocial.getText().toString().equals("") &&
                                !txtPosicaoTab.getText().toString().equals("")) {
                            inserirRedeSocial(btnCorTabNovo, txtNomeRedeSocial.getText().toString(), txtUrlRedeSocial.getText().toString(), txtPosicaoTab.getText().toString());
                            dialogoAtivo.dismiss();

                            new UtilidadesClass().carregarMensagem(ConfiguracoesActivity.this, getString(R.string.rede_social_inserida_sucesso));

                        } else {
                            new UtilidadesClass().carregarMensagem(ConfiguracoesActivity.this, getString(R.string.preencha_campos));
                        }
                    }
                });
            }
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
        return super.onOptionsItemSelected(item);
    }

    // Botão alterar cor tab
    @Override
    public void onButtonClick(FloatingActionButton btnAlterarCorTab) {
        carregarPaletaCores(btnAlterarCorTab);
    }

    // Botão alterar ou excluir tab
    @Override
    public void onButtonClick(ImageButton imageButton, final int idRedeSocial, final String nome) {
        try {
            switch (imageButton.getId()) {
                case R.id.btnExcluirRedeSocial:
                    List<RedesSociaisClass> listarRedesSociais = crudClass.listarRedesSociais(1);
                    if (listarRedesSociais.size() > 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(getString(R.string.mensagem_excluir_confirmacao, nome))
                                .setPositiveButton(R.string.botao_ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        removerRedeSocial(idRedeSocial, nome);
                                    }
                                })
                                .setNegativeButton(R.string.botao_cancelar, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                        builder.create();
                        builder.show();
                    } else
                        new UtilidadesClass().carregarMensagem(this, getString(R.string.mensagem_excluir_validacao));
                    break;
            }
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void finish() {
        if (salvou) {
            Intent retornoIntent = new Intent();
            setResult(RESULT_OK, retornoIntent);
            salvou = false;
        }
        super.finish();
    }
    //endregion

    //region [ MÉTODOS ]
    private void carregarConfiguracoes() {
        List<RedesSociaisClass> listarRedesSociais = crudClass.listarRedesSociais(0);

        ArrayList<ItemListaRedesSociaisClass> itemListaRedesSociaisClasses = new ArrayList<>();

        for (int i = 0; i < listarRedesSociais.size(); i++) {
            ItemListaRedesSociaisClass linhaItemClass = new ItemListaRedesSociaisClass(listarRedesSociais.get(i).getId(),
                    listarRedesSociais.get(i).getNome(),
                    listarRedesSociais.get(i).getPosicao(),
                    Color.parseColor(listarRedesSociais.get(i).getCor()),
                    listarRedesSociais.get(i).getAtivo());
            itemListaRedesSociaisClasses.add(linhaItemClass);
        }

        RedesSociaisAdapter redesSociaisAdapter = new RedesSociaisAdapter(itemListaRedesSociaisClasses);
        redesSociaisAdapter.setRecyclerViewButtonClickClass(this);
        redesSociaisAdapter.setRecyclerViewImageButtonClickClass(this);
        rvConfiguracoesRedesSociais.setAdapter(redesSociaisAdapter);
    }

    private void carregarPaletaCores(final FloatingActionButton btnCorTabNovo) {
        try {
            int corInicial = 0xffffffff;

            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle(getString(R.string.selecione_cor))
                    .initialColor(corInicial)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton(getString(R.string.botao_ok), new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                            String cor = String.format("#%06X", (0xFFFFFF & selectedColor));
                            btnCorTabNovo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(cor)));
                        }
                    })
                    .setNegativeButton(getString(R.string.botao_cancelar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .showColorEdit(true)
                    .setColorEditTextColor(ContextCompat.getColor(this, android.R.color.black))
                    .build()
                    .show();
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }

    private void inserirRedeSocial(FloatingActionButton btnCorTabNovo, String nome, String url, String posicao) {
        RedesSociaisClass redesSociaisClass = new RedesSociaisClass();
        redesSociaisClass.setNome(nome);
        redesSociaisClass.setUrl(url);

        redesSociaisClass.setPosicao(Integer.valueOf(posicao));

        String cor = "#000000";

        if (btnCorTabNovo.getBackgroundTintList() != null) {
            cor = "#" + Integer.toHexString(btnCorTabNovo.getBackgroundTintList().getDefaultColor());
        }
        redesSociaisClass.setCor(cor);
        redesSociaisClass.setAtivo(1);

        crudClass.inserirRedeSocial(redesSociaisClass);

        carregarConfiguracoes();

        salvou = true;
    }

    private void atualizarRedeSocial(int idRedeSocial, int ativo, int posicaoTab, FloatingActionButton btnCorTab) {
        RedesSociaisClass redesSociaisClass = new RedesSociaisClass();
        redesSociaisClass.setId(idRedeSocial);
        redesSociaisClass.setAtivo(ativo);
        redesSociaisClass.setPosicao(posicaoTab);

        String cor = "#000000";

        if (btnCorTab.getBackgroundTintList() != null) {
            cor = "#" + Integer.toHexString(btnCorTab.getBackgroundTintList().getDefaultColor());
        }

        redesSociaisClass.setCor(cor);

        crudClass = new CrudClass(this);
        crudClass.atualizarRedeSocial(redesSociaisClass);

        salvou = true;
    }

    private void removerRedeSocial(int idRedeSocial, String nome) {
        crudClass.excluirRedeSocial(idRedeSocial);

        carregarConfiguracoes();

        new UtilidadesClass().carregarMensagem(this, getString(R.string.rede_social_removida_sucesso, nome));
    }
    //endregion
}