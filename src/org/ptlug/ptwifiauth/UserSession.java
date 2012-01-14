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
 * Module name: UserSession
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class UserSession {

    private DefaultHttpClient httpclient = new DefaultHttpClient();
    private HttpPost httppost = new HttpPost(AppConsts.base_url);
    private HttpGet httpget = new HttpGet(AppConsts.ping_url);

    HttpResponse last_response;

    private String username;
    private String password;

    public UserSession(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserSession() {
    }

    public int getDefaultUrl() {
        try {

            httpget.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                    CookiePolicy.BROWSER_COMPATIBILITY);
            httpget.getParams().setParameter("http.protocol.handle-redirects",
                    false);
            last_response = httpclient.execute(httpget);

            this.debugCookies();

            String html = getHtmlContent();

            Logger.getInstance().doLog("HTMLLOGIN", html);

            if (html.contains("The document has moved")) {
                return 4;
            }

            if (html.contains("<h2>Please")) {
                int token_id = html.indexOf("<h2>Please");

                String addr = html.substring(token_id, html.length());

                GlobalSpace.login_url = addr.split("'")[1];

                Logger.getInstance().doLog("HTMLREDIRECT",
                        "" + addr.split("'")[1]);

                httppost = new HttpPost(GlobalSpace.login_url);

                return 1;
            }

            // else
            // AppConsts.login_url = "http://wifi.ptlug.org/login";

            return 0; // SET 1 ONLY FOR TESTING!!!

        }

        catch (ClientProtocolException e) {
            Logger.getInstance().doLog(LogConsts.login_result,
                    "ClientProtocolException " + e.toString());
            return 2;
        }

        catch (IOException e) {
            Logger.getInstance().doLog(LogConsts.login_result,
                    "IOException " + e.toString());
            return 2;
        }
    }

    public void create(String email) {
        String username_lower = email.split("@")[0];
        this.createUser(this.getToken(), username_lower, email);
    }

    public String getAttributes() {
        return this.username + AppConsts.file_separator + this.password;
    }

    public String getUserName() {
        return this.username;
    }

    private String getToken() {
        try {

            httppost.setEntity(new UrlEncodedFormEntity(PostParametersBuilder
                    .buildTokenRequestParameters()));

            httppost.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                    CookiePolicy.BROWSER_COMPATIBILITY);

            last_response = httpclient.execute(httppost);

            this.debugCookies();

            String html = getHtmlContent();

            int token_id = html.indexOf("ap_user[_csrf_token]");

            int start_parse_pos = 29;

            String token = html.substring(token_id + start_parse_pos, token_id
                    + start_parse_pos + AppConsts.cdrf_token_len);

            Logger.getInstance().doLog(LogConsts.token_acquired, "" + token);

            return token;

        }

        catch (ClientProtocolException e) {
            Logger.getInstance().doLog(LogConsts.login_result,
                    "ClientProtocolException " + e.toString());
            return null;
        }

        catch (IOException e) {
            Logger.getInstance().doLog(LogConsts.login_result,
                    "IOException " + e.toString());
            return null;
        }

    }

    private boolean createUser(String csrf_token, String username_lower,
            String email) {
        try {

            httppost = new HttpPost(AppConsts.base_url);

            httppost.setEntity(new UrlEncodedFormEntity(PostParametersBuilder
                    .buildCreateUserParameters(this.username, this.password,
                            csrf_token, username_lower, email)));

            httppost.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                    CookiePolicy.BROWSER_COMPATIBILITY);

            last_response = httpclient.execute(httppost);

            this.debugCookies();

            Logger.getInstance().doLog(LogConsts.html_acquired,
                    getHtmlContent());

            httppost = new HttpPost(GlobalSpace.login_url);

            return true;

        }

        catch (ClientProtocolException e) {
            Log.i(LogConsts.login_result, "ClientProtocolException "
                    + e.toString());
            return false;
        }

        catch (IOException e) {
            Log.i(LogConsts.login_result, "IOException " + e.toString());
            return false;
        }
    }

    public boolean login() {
        try {
            int status_def = this.getDefaultUrl();

            if (status_def != 1 && status_def != 4)
                return false;

            httppost.setEntity(new UrlEncodedFormEntity(PostParametersBuilder
                    .buildLoginParameters(this.username, this.password)));

            Logger.getInstance().doLog("UserNamePassword",
                    this.username + this.password);

            httppost.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                    CookiePolicy.BROWSER_COMPATIBILITY);

            last_response = httpclient.execute(httppost);

            this.debugCookies();

            return verifyLogin();

        }

        catch (ClientProtocolException e) {
            Logger.getInstance().doLog(LogConsts.login_result,
                    "ClientProtocolException " + e.toString());
            return false;
        }

        catch (IOException e) {
            Logger.getInstance().doLog(LogConsts.login_result,
                    "IOException " + e.getMessage());
            return false;
        }

    }

    private boolean verifyLogin() {
        String html = this.getHtmlContent();

        Logger.getInstance().doLog("LOGGINNN", html);

        return html.contains(AppConsts.logged_feedback);
    }

    private void debugCookies() {
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();

        if (cookies.isEmpty())
            Logger.getInstance().doLog(LogConsts.cookie_acquired, "None");
        else
            for (int i = 0; i < cookies.size(); i++)
                Logger.getInstance().doLog(LogConsts.cookie_acquired,
                        cookies.get(i).toString());
    }

    public String getHtmlContent() {
        InputStream is;
        HttpEntity entity = this.last_response.getEntity();

        try {

            is = entity.getContent();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer responsestr = new StringBuffer();

            while ((line = rd.readLine()) != null) {
                responsestr.append(line);
                responsestr.append('\r');
            }

            rd.close();

            return responsestr.toString();

        }

        catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        }

        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}