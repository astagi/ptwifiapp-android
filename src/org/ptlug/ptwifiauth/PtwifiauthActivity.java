package org.ptlug.ptwifiauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class PtwifiauthActivity extends Activity{
    
    public void about() {
        Utilities.showSimpleAlertDialog(this, this
                .getString(R.string.about_title), this
                .getString(R.string.about_message), this
                .getString(R.string.about_btn));
    }
    
    public void exit()
    {
        System.exit(0);
    }
    
    public void openBrowser() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri
                .parse(AppConsts.default_redirect_url)));
    }

    public void openMap() {
        Intent myIntent = new Intent(this, MapView.class);
        this.startActivity(myIntent);
        overridePendingTransition(R.layout.fadein,R.layout.fadeout); 
    }

}
