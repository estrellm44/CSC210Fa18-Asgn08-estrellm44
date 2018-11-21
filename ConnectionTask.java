package edu.sunyulster.meldiesestrella.getwebpage;

/**
 * Created by meldiesestrella on 11/13/18.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionTask extends AsyncTask<String, Void, String> {
    Context ctx;
    public ConnectionTask(Context ct) {
        ctx = ct;
    }

    ProgressDialog loading;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(ctx, "Get Page Source", "Please Wait ... ", false,false);
    }

    @Override
    protected String doInBackground(String... strings) {
        String s1 = strings[0];

        InputStream inS;
        try {
            URL myURL = new URL(s1);
            HttpURLConnection myCon = (HttpURLConnection) myURL.openConnection();
            myCon.setReadTimeout(10000);
            myCon.setConnectTimeout(20000);
            myCon.setRequestMethod("GET");
            myCon.connect();

            if (myCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                inS = myCon.getInputStream();
                BufferedReader myBuf = new BufferedReader(new InputStreamReader(inS));
                StringBuilder st = new StringBuilder();
                String line = "";

                while ((line = myBuf.readLine()) != null){
                    st.append(line + " \n");
                }

                myBuf.close();
                inS.close();
                return  st.toString();
            }
            else {
                return "Error Response Code"+myCon.getResponseCode();
            }



        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Error Checking Connection to Internet", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        MainActivity.textView.setText(s);
        super.onPostExecute(s);
        loading.dismiss();
    }

}
