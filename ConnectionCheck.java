package edu.sunyulster.meldiesestrella.getwebpage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by meldiesestrella on 11/13/18.
 */

public class ConnectionCheck {

    Context ctx ;

    public ConnectionCheck(Context ctx) {
        this.ctx = ctx;
    }

    // Check Connection Network
    public boolean isConnectingToNetwork()
    {
        ConnectivityManager connectivity =
                (ConnectivityManager) ctx.getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo[] info= connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }


}
