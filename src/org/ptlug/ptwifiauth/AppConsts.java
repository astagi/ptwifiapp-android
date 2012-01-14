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
 * Module name: AppConsts
 * Date: 07/03/11
 * Author: Andrea Stagi <stagi.andrea(at)gmail.com>
 *
 ***/

package org.ptlug.ptwifiauth;

import android.os.Environment;

public class AppConsts {

    public static final String ping_url = "http://www.google.com";
    public static final int cdrf_token_len = 32;
    public static final String logged_feedback = "/authlocaluser/my-account";
    public static final String file_path = Environment.getDataDirectory().toString();
    public static final String base_url = "http://wifi.ptlug.org/login";
    public static final String user_file = "ptwifiusr";
    public static final String file_separator = "---";
    public static final String default_redirect_url = "http://www.google.com";

}
