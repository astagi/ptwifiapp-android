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
 * Module name: Utilities
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Utilities {

    public static boolean saveUserToSdCard(String user, String password) {

        try {
            File file = new File(AppConsts.file_path, AppConsts.user_file);
            FileOutputStream fos = new FileOutputStream(file);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public static void showSimpleAlertDialog(Activity act, String title,
            String text, String button) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(act);
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static boolean testConnection() {
        /*
         * if(GlobalSpace.usr_sess.getDefaultUrl() == 0) return true; else
         * if(GlobalSpace.usr_sess.getDefaultUrl() == 4) return true; else
         * if(GlobalSpace.usr_sess.getDefaultUrl() == 1) return false; //SET
         * 'true' ONLY FOR TESTING else return false;
         */
        boolean result = false;
        try {
            HttpGet request = new HttpGet(AppConsts.ping_url);

            HttpParams httpParameters = new BasicHttpParams();

            // HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpResponse response = httpClient.execute(request);

            int status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_OK) {
                result = true;
            }
            return result;

        } catch (Exception e) {
            result = false;
        }
        /*
         * catch (SocketTimeoutException e) { result = false; // this is
         * somewhat expected } catch (ClientProtocolException e) { result =
         * false; } catch (IOException e) { result = false;; }
         */
        return result;
    }

}
