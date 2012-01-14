/*
        Developed by PtLug <http://ptlug.org/>

        Ptwifiauth: Pistoia Wireless client for Android phones
        Copyright (C) 2010 PtLug <http://ptlug.org/>

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/***
 * 
 * Module name: UserActivity
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class UserActivity extends PtwifiauthActivity {

    private WifiManager wifiManager = null;
    private WifiInfo wifiInfo = null;
    private TextView conn_label;
    private TextView user_label;
    private ConnectionController conn_controller = null;
    private static final Handler handler = new Handler();
    private static boolean created = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useractivity);

        conn_label = (TextView) this.findViewById(R.id.wifi_name);
        user_label = (TextView) this.findViewById(R.id.username_logged);

    }

    @Override
    public void onStart() {
        super.onStart();
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        this.checkWireless();

        if (!created) {
            if (conn_controller == null)
                conn_controller = new ConnectionController();
            conn_controller.execute();
            created = true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.usermenu, menu);
        return true;
    }
    
    public void logout() {
        Intent myIntent = new Intent(this, Ptwifiauth.class);
        this.startActivity(myIntent);
        this.stopService(this.getIntent());
        handler.removeMessages(0);
        created = false;
        this.finish();
        overridePendingTransition(R.layout.fadein,R.layout.fadeout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.navigate:
            this.openBrowser();
            return true;
        case R.id.map:
            this.openMap();
            return true;
        case R.id.about:
            this.about();
            return true;
        case R.id.logout:
            this.logout();
            return true;    
        case R.id.exit:
            this.exit();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void checkWireless() {
        conn_label.setText(this.getString(R.string.connected_to) + " "
                + wifiInfo.getSSID());
        user_label.setText(GlobalSpace.usr_sess.getUserName());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return false;
    }

    private void doTheAutoRefresh(long time) {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                checkWireless();
                if (!conn_controller.isCancelled())
                    conn_controller.cancel(true);

                // Enable for debug
                // Toast.makeText(UserActivity.this, "Scanning wrl",
                // Toast.LENGTH_LONG).show();

                conn_controller = new ConnectionController();
                conn_controller.execute();
            }

        }, time);
    }

    private class ConnectionController extends AsyncTask<Void, Void, Boolean> {

        public ConnectionController() {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return Utilities.testConnection();
        }

        protected void onPostExecute(Boolean result) {
            if (result == true)
                UserActivity.this.doTheAutoRefresh(20000);
            else
                UserActivity.this.logout();
        }
        

    }

}
