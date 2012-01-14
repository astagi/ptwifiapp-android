package org.ptlug.ptwifiauth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;

public class MapView extends Activity{

    private WebView webView;
    
    private ProgressDialog dialog;

    private class MyPictureListener implements PictureListener {

        public void onNewPicture(WebView arg0, Picture arg1) {
           dialog.dismiss();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setPictureListener(new MyPictureListener());
        webView.loadUrl("file:///android_asset/home.html");
        
        dialog = new ProgressDialog(this);
        dialog.setMessage("Caricamento...");
        dialog.setIndeterminate(true);
        dialog.show();  

    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            overridePendingTransition(R.layout.fadein,R.layout.fadeout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
