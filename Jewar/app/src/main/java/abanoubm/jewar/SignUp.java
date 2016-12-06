package abanoubm.jewar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends Activity {
    private EditText email, password;
    private TextView signin, signup;

    private class SignUpTask extends AsyncTask<Void, Void, Integer> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(SignUp.this);
            pBar.setCancelable(false);
            pBar.setTitle("loading");
            pBar.setMessage("signing up ....");
            pBar.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return JewarApi.signUp(email.getText().toString().trim(), password.getText().toString()
                    .trim());

        }

        @Override
        protected void onPostExecute(Integer status) {
            switch (status.intValue()) {
                case -1:
                    Toast.makeText(getApplicationContext(), "check internet connection",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "invalid email or password",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "invalid email or password",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "invalid email or password",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "email is already used",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Intent intent = new Intent(getApplicationContext(), Home.class);
//				if (remember.isChecked()) {
//					SharedPreferences.Editor editor = getSharedPreferences(
//							"access", Context.MODE_PRIVATE).edit();
//					editor.putString("email", email.getText().toString().trim());
//					editor.putString("password", password.getText().toString()
//							.trim());
//					editor.putBoolean("remember", true);
//					editor.commit();
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//				}
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
            pBar.dismiss();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signin = (TextView) findViewById(R.id.sign_in);
        signup = (TextView) findViewById(R.id.sign_up);


        signup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				if (!validName)
//					name.setError("Ops, name must be at least 4 characters in length.");
//				else if (!validEmail)
//					email.setError("Ops, this does't seem to be a valid email.");
//				else if (!validPassword)
//					password.setError("Ops, password must be at least 6 characters in length.");
//				else if (!passwordMatch)
//					password2.setError("Ops, password not matching.");
//				else if (ConnectManager.isNetworkAvailable(getApplicationContext()))
                new SignUpTask().execute();
            }
        });
        signin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
                finish();
            }
        });
    }
}
