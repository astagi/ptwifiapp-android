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
 * Module name: NewUser
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends Ptwifiauth {

    private EditText psw_again;
    private EditText mail;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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

    protected void initialize() {
        setContentView(R.layout.newuser);

        this.getElementsFromLayout();

        this.setLoginListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validFields()) {
                    NewUser.super.startLogin();
                }
            }
        });
    }

    protected void getElementsFromLayout() {
        super.getElementsFromLayout();
        mail = (EditText) this.findViewById(R.id.mail);
        psw_again = (EditText) this.findViewById(R.id.usr_repeat_psw);
    }

    private boolean validFields() {
        if (mail.getText().toString().compareTo("") == 0
                || psw_again.getText().toString().compareTo("") == 0
                || this.getUsername_txt().getText().toString().compareTo("") == 0
                || this.getPassword_txt().getText().toString().compareTo("") == 0) {
            Toast.makeText(this, "Campi vuoti presenti", Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (this.getPassword_txt().getText().toString().length() < 8) {
            Toast.makeText(this, "Password minima:8 caratteri",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (this.getPassword_txt().getText().toString().compareTo(
                psw_again.getText().toString()) != 0) {
            Toast.makeText(this, "Password diverse", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean doLogin() {
        GlobalSpace.usr_sess.create(mail.getText().toString());
        return super.doLogin();
    }

    @Override
    public String getLoginMessage() {
        return this.getString(R.string.login_progress);
    }

}