package ru.fordexsys.solomatintest.ui.login;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.ui.base.BaseActivity;

/**
 * Created by Altair on 23-Jun-17.
 */

public class WebFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "WebFragment";

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_reload)
    Button btnReload;

    @BindView(R.id.web)
    WebView webView;
    @BindView(R.id.web_progress)
    ProgressBar progressBar;

    @Inject
    DataManager dataManager;

    private Unbinder unbinder;

    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_web, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((BaseActivity) getActivity()).activityComponent().inject(this);

        init();

        return view;
    }

    private void init() {
        btnBack.setOnClickListener(this);
        btnReload.setOnClickListener(this);

        openWeb();
    }

    private void openWeb() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        String url = "https://oauth.vk.com/authorize?client_id=6087019&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=65540&response_type=token&v=5.52";
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                onResultUrl(view, url);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }

            private void onResultUrl(WebView webView, String url) {
                if (url.contains("https://oauth.vk.com/blank.html") && !url.contains("error")
                        && url.contains("access_token")) { // Выполнен вход
                    webView.loadUrl("about:blank");

                    Map<String, String> query_pairs = new HashMap<>();
                    String query = Uri.parse(url).getFragment();
                    String[] pairs = query.split("&");
                    for (String pair : pairs) {
                        int idx = pair.indexOf("=");
                        try {
                            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            throw new AssertionError("UTF-8 is unknown");
                        }
                    }

                    if (query_pairs.containsKey("access_token")
                            && !TextUtils.isEmpty(query_pairs.get("access_token"))) {
                        saveToken(query_pairs.get("access_token"));
                    }

                    ((LoginActivity) getActivity()).closeActivity();

                } else if (url.contains("error")) { // Ошибка
                    webView.loadUrl("about:blank");
                    Toast.makeText(getActivity(), R.string.error_login, Toast.LENGTH_SHORT).show();
                    saveToken("");
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

    }

    private void saveToken(String token) {
        dataManager.getPreferencesHelper().putToken(token);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                ((LoginActivity) getActivity()).changeFragment(LoginFragment.TAG);
                break;
            case R.id.btn_reload:
                String url = "https://oauth.vk.com/authorize?client_id=6087019&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=65540&response_type=token&v=5.52";
                webView.loadUrl(url);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
