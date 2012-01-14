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
 * Module name: PostParametersBuild
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PostParametersBuilder {

    public static ArrayList<NameValuePair> buildTokenRequestParameters() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("authenticator",
                "apAuthLocalUser"));
        nameValuePairs.add(new BasicNameValuePair("link", "signup"));
        nameValuePairs.add(new BasicNameValuePair("apAuthLocalUser[username]",
                null));
        nameValuePairs.add(new BasicNameValuePair("apAuthLocalUser[password]",
                null));

        return nameValuePairs;
    }

    public static ArrayList<NameValuePair> buildCreateUserParameters(
            String username, String password, String csrf_token,
            String username_lower, String email) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("authenticator",
                "apAuthLocalUser"));
        nameValuePairs.add(new BasicNameValuePair("ap_user[username_lower]",
                username_lower));
        nameValuePairs.add(new BasicNameValuePair("ap_user[id]", ""));
        nameValuePairs.add(new BasicNameValuePair("ap_user[username_lower]",
                username_lower));
        nameValuePairs.add(new BasicNameValuePair("ap_user[_csrf_token]",
                csrf_token));
        nameValuePairs.add(new BasicNameValuePair("link", "signup"));
        nameValuePairs.add(new BasicNameValuePair(
                "submit[apAuthLocalUsersignup]", "Sign me up"));
        nameValuePairs
                .add(new BasicNameValuePair("ap_user[username]", username));
        nameValuePairs
                .add(new BasicNameValuePair("ap_user[password]", password));
        nameValuePairs.add(new BasicNameValuePair("ap_user[password_again]",
                password));
        nameValuePairs.add(new BasicNameValuePair("ap_user[email]", email));
        nameValuePairs
                .add(new BasicNameValuePair("ap_user[email_again]", email));
        nameValuePairs.add(new BasicNameValuePair("ap_user[warning]", "1"));
        nameValuePairs.add(new BasicNameValuePair("ap_user[username_lower]",
                username_lower));
        nameValuePairs.add(new BasicNameValuePair("ap_user[id]", ""));
        nameValuePairs.add(new BasicNameValuePair("ap_user[username_lower]",
                username_lower));
        nameValuePairs.add(new BasicNameValuePair("ap_user[_csrf_token]",
                csrf_token));

        return nameValuePairs;
    }

    public static ArrayList<NameValuePair> buildLoginParameters(
            String username, String password) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("apAuthLocalUser[username]",
                username));
        nameValuePairs.add(new BasicNameValuePair("apAuthLocalUser[password]",
                password));
        nameValuePairs.add(new BasicNameValuePair("authenticator",
                "apAuthLocalUser"));
        nameValuePairs.add(new BasicNameValuePair(
                "submit[apAuthLocalUserconnect]", "Connect"));

        return nameValuePairs;
    }

}
