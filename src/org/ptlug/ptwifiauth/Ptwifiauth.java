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
 * Module name: Ptwifiauth
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.ptlug.ptwifiauth.R;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Ptwifiauth extends LoginActivity {

    private LogTask lg;

    private Button login_button;
    private Button new_button;
    
    private ImageButton map_button;

    private EditText username_txt;
    private EditText password_txt;

    private CheckBox check_remember;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initialize();
    }
    

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.miniusermenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.about:
            this.about();
            return true;
        case R.id.logout:
            this.exit();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    protected void initialize() {
        setContentView(R.layout.main);

        this.getElementsFromLayout();

        try {
            openFileInput("First_time");
        } catch (IOException ex) {
            try {
                openFileOutput("First_time", Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.showWelcomeMessage();
        }

        String[] usr_data = this.loadUser();

        if (usr_data != null) {
            username_txt.setText(usr_data[0]);
            password_txt.setText(usr_data[1]);
        }

        this.setLoginListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                Ptwifiauth.this.startLogin();
            }
        });

        this.new_button = (Button) this.findViewById(R.id.newusr_button);
        this.new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), NewUser.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(R.layout.fadein,R.layout.fadeout); 
            }
        });
        

    }

    protected void getElementsFromLayout() {
        username_txt = (EditText) this.findViewById(R.id.usr_name);
        password_txt = (EditText) this.findViewById(R.id.usr_psw);

        check_remember = (CheckBox) this.findViewById(R.id.chk_remember);

        this.login_button = (Button) this.findViewById(R.id.login_button);
        
        this.map_button = (ImageButton) this.findViewById(R.id.map_button);
        this.map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

    }

    protected void startLogin() {
        GlobalSpace.usr_sess = new UserSession(username_txt.getText()
                .toString(), password_txt.getText().toString());
        lg = new LogTask(Ptwifiauth.this);
        lg.execute();
    }

    @Override
    public boolean doLogin() {
        return GlobalSpace.usr_sess.login();
    }

    @Override
    public String getLoginMessage() {
        return this.getString(R.string.login_progress);
    }

    @Override
    public void onLoginSuccesful() {
        if (check_remember.isChecked())
            this.saveUser();

        Intent myIntent = new Intent(this, UserActivity.class);
        this.startActivity(myIntent);
        overridePendingTransition(R.layout.fadein,R.layout.fadeout); 
        this.finish();
    }

    public void saveUser() {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(AppConsts.user_file, Context.MODE_PRIVATE);
            fos.write(GlobalSpace.usr_sess.getAttributes().getBytes());
            fos.close();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public String[] loadUser() {
        FileInputStream fin = null;
        DataInputStream dis = null;
        String res = null;

        try {
            fin = openFileInput(AppConsts.user_file);
            dis = new DataInputStream(fin);
            res = dis.readLine();
            fin.close();
            return res.split(AppConsts.file_separator);
        } catch (IOException ex) {
            return null;
        }
    }

    private void showWelcomeMessage() {
        Utilities.showSimpleAlertDialog(this, this
                .getString(R.string.wel_title), this
                .getString(R.string.wel_message), this
                .getString(R.string.wel_btn));
    }

    protected void setLoginListener(View.OnClickListener v) {
        this.login_button.setOnClickListener(v);
    }

    @Override
    public void onLoginUnsuccesful() {
        Toast.makeText(this, this.getString(R.string.login_fail),
                Toast.LENGTH_LONG).show();
        login_button.setEnabled(true);
    }

    public EditText getUsername_txt() {
        return username_txt;
    }

    public EditText getPassword_txt() {
        return password_txt;
    }
}