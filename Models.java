package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebViewAssetLoader;
import androidx.webkit.WebViewClientCompat;

import java.io.File;
import java.util.ArrayList;

public class Models extends AppCompatActivity {
    ArrayList<File> myModels;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myModels = (ArrayList) bundle.getParcelableArrayList("com.example.project.ModelCollections");
        int position = intent.getIntExtra("com.example.project.position", 0);
        String[] items = intent.getStringArrayExtra("com.example.project.ModelNames");
        Uri path = Uri.parse(myModels.get(position).toString());
//        File file = new File(path.getPath());//create path from uri
//        final String[] split = file.getPath().split(":");//split the path.
//         String filePath = split[1];//assign it to a string(your choice).
//        Toast.makeText(this, ""+myModels.get(position).toString(), Toast.LENGTH_SHORT).show();
//        WebView myWebView = (WebView) findViewById(R.id.webView);
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
//        Uri intentUri =
//                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
//                        .appendQueryParameter("file", myModels.get(position).toString())
//                        .appendQueryParameter("mode", "3d_only")
//                        .build();
//        sceneViewerIntent.setData(intentUri);
//        sceneViewerIntent.setPackage("com.google.ar.core");
//        startActivity(sceneViewerIntent);

//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//// read script file
//        engine.eval(Files.newBufferedReader(Paths.get("C:/Scripts/Jsfunctions.js"), StandardCharsets.UTF_8));
//
//        Invocable inv = (Invocable) engine;
//// call function from script file
//        inv.invokeFunction("yourFunction", "param");
//    }


        webView = findViewById(R.id.webView);




        final WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();


//        PrintWriter writer = null;
//        try {
//            writer = new PrintWriter(a);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        writer.print("");
//        writer.close();
//        File b=myModels.get(position);
//        File a=new File("face.obj");
//        File b=myModels.get(position);
//        try {
//            CopyContent.copyContent(b,a);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        webView.setWebViewClient(new WebViewClientCompat() {
            public void onPageFinished(@Nullable WebView view, @Nullable String url) {
                Models.this.loadObjModel();
            }

            public void onLoadResource(@Nullable WebView view, @Nullable String url) {
                super.onLoadResource(view, url);
            }
            @Override
            @RequiresApi(21)
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return assetLoader.shouldInterceptRequest(request.getUrl());
            }

            @Override
            @SuppressWarnings("deprecation") // for API < 21
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return assetLoader.shouldInterceptRequest(Uri.parse(url));
            }
        });

        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webView.loadUrl("https://appassets.androidplatform.net/assets/index.html");
//        webView.loadDataWithBaseURL( "file:///android_asset/","index.html", "text/html", "utf-8", null );

    }


    private final void loadObjModel() {
//        String path=myModels.get(position).toString().replace(items[position],"");
//        Toast.makeText(this, "javascript:loadModel('https://appassets.androidplatform.net"+path+"','"+items[position].replace(".obj","")+"','base')", Toast.LENGTH_LONG).show();

//        String action = "javascript:loadModel('"+path.replace(items[position],"")+"','"+items[position].replace(".obj","")+"','base')";

//        String action="javascript:loadModel('https://appassets.androidplatform.net"+filepath+"','"+items[position].replace(".obj","")+"','base')";
        String action="javascript:loadModel('content://java/com/example/project/','face')";
        WebView var10000 = this.webView;
        if (var10000 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("webView");
        }

        var10000.loadUrl(action);
    }


    public void copyContent(File a, File b)
            throws Exception
    {

//        System.out.println("File Copied");
    }
}
