package br.com.lrssoftwares.socialtabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static List<String> listaUrls;
    public static int indice;
    private static boolean bloqueado = false;

    //region [ VARIÁVEIS ]
    private Toolbar toolbarPrincipal;
    private TabLayout tabLayoutPrincipal;
    private CustomViewPagerClass viewpagerPrincipal;
    private List<RedesSociaisClass> listaRedesSociais;
    private BotaoFlutuanteActivity botaoFlutuanteActivity;
    private Menu menuOpcoes;
    //endregion

    //region [ EVENTOS ]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_principal);

            toolbarPrincipal = findViewById(R.id.tPrincipal);
            setSupportActionBar(toolbarPrincipal);

            carregarTabs();

            DrawerLayout dlPrincipal = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle actionBarDrawerTogglePrincipal = new ActionBarDrawerToggle(this, dlPrincipal, toolbarPrincipal, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            dlPrincipal.addDrawerListener(actionBarDrawerTogglePrincipal);
            actionBarDrawerTogglePrincipal.syncState();

            NavigationView nvPrincipal = findViewById(R.id.nvPrincipal);
            nvPrincipal.setNavigationItemSelectedListener(this);

            botaoFlutuanteActivity = findViewById(R.id.btnBotaoFlutuante);
            botaoFlutuanteActivity.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    telaCheia(false);
                }
            });

            CrudClass crudClass = new CrudClass(this);
            int telaCheia = crudClass.PesquisarParametro().get(0).getAtivo();

            if (telaCheia == 0)
                telaCheia(false);
            else
                telaCheia(true);
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuOpcoes = menu;

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        if (!bloqueado) {
            menuOpcoes.findItem(R.id.itemBloqueado).setVisible(false);
            menuOpcoes.findItem(R.id.itemLiberado).setVisible(true);
            viewpagerPrincipal.setTabLiberada(true);
        } else {
            viewpagerPrincipal.setTabLiberada(false);
            menuOpcoes.findItem(R.id.itemLiberado).setVisible(false);
            menuOpcoes.findItem(R.id.itemBloqueado).setVisible(true);
        }

        return true;
    }

    @SuppressLint("InflateParams")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.itemLiberado) {
                bloqueado = true;
                viewpagerPrincipal.setTabLiberada(false);
                menuOpcoes.findItem(R.id.itemLiberado).setVisible(false);
                menuOpcoes.findItem(R.id.itemBloqueado).setVisible(true);
            }

            if (id == R.id.itemBloqueado) {
                bloqueado = false;
                viewpagerPrincipal.setTabLiberada(true);
                menuOpcoes.findItem(R.id.itemBloqueado).setVisible(false);
                menuOpcoes.findItem(R.id.itemLiberado).setVisible(true);
            }

            if (id == R.id.itemFullscreen) {
                telaCheia(true);
            }

            if (id == R.id.itemConfiguracoes) {
                startActivityForResult(new Intent(this, ConfiguracoesActivity.class), 999);
            }
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            int id = item.getItemId();

        /*
        if (id == R.id.itemCompras) {
        } else
        */

            if (id == R.id.itemAjuda) {
                Intent intentConfiguracoes = new Intent(PrincipalActivity.this, AjudaActivity.class);
                startActivity(intentConfiguracoes);

            }

            if (id == R.id.itemConfiguracoes) {
                Intent intentConfiguracoes = new Intent(PrincipalActivity.this, ConfiguracoesActivity.class);
                startActivity(intentConfiguracoes);

            } else if (id == R.id.itemCompartilhar) {
                try {
                    String appPackageName = getPackageName();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String descricao = "\n" + getString(R.string.recomendo_aplicativo) + "\n";
                    descricao += "https://play.google.com/store/apps/details?id=" + appPackageName + "\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, descricao);
                    startActivity(Intent.createChooser(intent, getString(R.string.selecione_opcao_compartilhar)));
                } catch (Exception erro) {
                    new UtilidadesClass().enviarMensagemContato(this, erro);
                }

            } else if (id == R.id.itemAvaliar) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            } else if (id == R.id.itemReportarErro) {
                Intent intentReportarErro = new Intent(PrincipalActivity.this, ContatoActivity.class);
                startActivity(intentReportarErro);

            } else if (id == R.id.itemSobre) {
                Intent intentSobre = new Intent(PrincipalActivity.this, SobreActivity.class);
                startActivity(intentSobre);
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            carregarTabs();
        }
    }
    //endregion

    //region [ MÉTODOS ]
    private void carregarTabs() {
        try {
            listaUrls = new ArrayList<>();
            indice = 0;

            // Setando as Tabs
            viewpagerPrincipal = findViewById(R.id.vpPrincipal);
            ViewPagerAdapter viewpageradapterPrincipal = new ViewPagerAdapter(getSupportFragmentManager());
            viewpagerPrincipal.setOffscreenPageLimit(30);

            CrudClass crudClass = new CrudClass(this);
            listaRedesSociais = crudClass.listarRedesSociais(1);

            for (int i = 0; i < listaRedesSociais.size(); i++) {
                RedesSociaisFragment tabRedesSociais = new RedesSociaisFragment();
                listaUrls.add(listaRedesSociais.get(i).getUrl());
                viewpageradapterPrincipal.addFragment(tabRedesSociais, listaRedesSociais.get(i).getNome());
            }

            // Setando cor da Tab e Bar
            String cor = listaRedesSociais.get(0).getCor();

            tabLayoutPrincipal = findViewById(R.id.tlPrincipal);
            tabLayoutPrincipal.setupWithViewPager(viewpagerPrincipal);
            toolbarPrincipal.setBackgroundColor(Color.parseColor(cor));
            tabLayoutPrincipal.setBackgroundColor(Color.parseColor(cor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(cor));
            }

            toolbarPrincipal.setBackgroundColor(Color.parseColor(cor));
            tabLayoutPrincipal.setBackgroundColor(Color.parseColor(cor));

            viewpagerPrincipal.setAdapter(viewpageradapterPrincipal);

            viewpagerPrincipal.addOnPageChangeListener(new CustomViewPagerClass.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    String cor = listaRedesSociais.get(position).getCor();

                    toolbarPrincipal.setBackgroundColor(Color.parseColor(cor));
                    tabLayoutPrincipal.setBackgroundColor(Color.parseColor(cor));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(Color.parseColor(cor));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(this, erro);
        }
    }

    private void telaCheia(boolean ativar) {
        if (ativar) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                tabLayoutPrincipal.setVisibility(View.GONE);
                botaoFlutuanteActivity.setVisibility(View.VISIBLE);

                ParametroClass parametroClass = new ParametroClass();
                parametroClass.setId(1);
                parametroClass.setNome("Tela Cheia Habilitada");
                parametroClass.setAtivo(1);

                CrudClass crudClass = new CrudClass(this);
                crudClass.AtualizarParametro(parametroClass);
            }
        } else {
            if (getSupportActionBar() != null && !getSupportActionBar().isShowing()) {
                getSupportActionBar().show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                tabLayoutPrincipal.setVisibility(View.VISIBLE);
                botaoFlutuanteActivity.setVisibility(View.GONE);

                ParametroClass parametroClass = new ParametroClass();
                parametroClass.setId(1);
                parametroClass.setNome("Tela Cheia Desabilitada");
                parametroClass.setAtivo(0);

                CrudClass crudClass = new CrudClass(this);
                crudClass.AtualizarParametro(parametroClass);
            }
        }
    }

    //endregion

    //region [ CLASSES ]
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> listaFragmentos = new ArrayList<>();
        private final List<String> listaTitulos = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return listaFragmentos.get(position);
        }

        @Override
        public int getCount() {
            return listaFragmentos.size();
        }

        void addFragment(Fragment fragment, String title) {
            listaFragmentos.add(fragment);
            listaTitulos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listaTitulos.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
    //endregion
}
