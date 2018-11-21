package edu.sunyulster.meldiesestrella.getwebpage;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    ConnectionTask task;
    ConnectionCheck check;
    Spinner spinner;
    static  TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);
        check = new ConnectionCheck(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_url, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void getCode(View view) {
        String URL = spinner.getSelectedItem().toString()+editText.getText().toString().trim();
        boolean valid = Patterns.WEB_URL.matcher(URL).matches();



        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {

            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }



        if (check.isConnectingToNetwork())
        {
            if (URL.isEmpty()){
                Toast.makeText(this, "Please Fill the Form Correctly", Toast.LENGTH_SHORT).show();
            }
            else if(valid){
                task = new ConnectionTask(this);
                task.execute(URL);
            } else
            {
                textView.setText("URL NOT Valid");
            }

        } else
        {
            Toast.makeText(this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Network Connection.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                            @SuppressWarnings("unused") final int id){
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        public void onClick (final DialogInterface dialog , @SuppressWarnings("unused") final int id){
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }


    }

}
