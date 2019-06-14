package br.com.lrssoftwares.socialtabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static br.com.lrssoftwares.socialtabs.PrincipalActivity.indice;
import static br.com.lrssoftwares.socialtabs.PrincipalActivity.listaUrls;

public class RedesSociaisFragment extends Fragment {
    //region [VARIÁVEIS]
    private WebView wvRedesSociais;
    private SwipeRefreshLayout srlAtualizarRedesSociais;
    //endregion

    //region [EVENTOS]
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_redes_sociais, container, false);

        try {
            wvRedesSociais = rootView.findViewById(R.id.wvRedesSociais);

            wvRedesSociais.getSettings().setJavaScriptEnabled(true);
            wvRedesSociais.getSettings().setAppCacheEnabled(true);
            wvRedesSociais.getSettings().setDatabaseEnabled(true);
            wvRedesSociais.getSettings().setDomStorageEnabled(true);
            wvRedesSociais.setWebChromeClient(new WebChromeClient());


            if (!new UtilidadesClass().verificarConexao(getContext())) {
                wvRedesSociais.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }

            // Setando eventos do WebView
            wvRedesSociais.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);

                    if (url.startsWith("intent://")) {
                        wvRedesSociais.stopLoading();
                        try {
                            Intent facebookIntent = new Intent(Intent.ACTION_SEND);
                            facebookIntent.setType("text/plain");
                            facebookIntent.setPackage("com.facebook.orca");

                            facebookIntent.putExtra(Intent.EXTRA_TEXT, "\0");

                            startActivity(facebookIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), R.string.facebook_messenger_nao_instalado, Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(wvRedesSociais, url);
                    srlAtualizarRedesSociais.setRefreshing(false);
                }
            });

            carregarWebView();

            wvRedesSociais.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    srlAtualizarRedesSociais.setRefreshing(false);
                    return false;
                }
            });

            wvRedesSociais.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        WebView webView = (WebView) v;

                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                if (webView.canGoBack()) {
                                    webView.goBack();
                                    return true;
                                }
                                break;
                        }
                    }
                    return false;
                }
            });

            // Componente para atualizar o WebView
            srlAtualizarRedesSociais = rootView.findViewById(R.id.srlAtualizarRedesSociais);
            srlAtualizarRedesSociais.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            wvRedesSociais.reload();
                        }
                    }
            );

        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(getActivity(), erro);
        }
        return rootView;
    }

    private void carregarWebView() {
        try {
            if (listaUrls.get(indice).equals("https://www.messenger.com")) {
                setarDesktopModo();
            }

            wvRedesSociais.loadUrl(listaUrls.get(indice));

            indice = indice + 1;
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(getActivity(), erro);
        }
    }

    private void setarDesktopModo() {
        try {
            String desktop = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";
            // String mobile = "Mozilla/5.0 (Linux; U; Android 4.4; en-us; Nexus 4 Build/JOP24G) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

            WebSettings settings = wvRedesSociais.getSettings();
            settings.setUserAgentString(desktop);
        } catch (Exception erro) {
            new UtilidadesClass().enviarMensagemContato(getActivity(), erro);
        }
    }
    //endregion
}
