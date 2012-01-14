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
 * Module name: LogTask
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class LogTask extends AsyncTask<Void, Void, Boolean> {

    private ProgressDialog dialog;
    private LoginActivity act;

    public LogTask(LoginActivity act) {
        this.act = act;
    }

    @Override
    protected void onPreExecute() {

        dialog = new ProgressDialog(act);
        dialog.setMessage(act.getLoginMessage());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return act.doLogin();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        act.setLoginResult(result);
        dialog.dismiss();
    }
}