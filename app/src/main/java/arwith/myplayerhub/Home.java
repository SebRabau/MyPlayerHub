package arwith.myplayerhub;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Home";

    private FirebaseAuth mAuth;


    private TextView Status;
    private EditText Email;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        Status = findViewById(R.id.LoginStatus);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);


        findViewById(R.id.Submit).setOnClickListener(this);
        findViewById(R.id.Create).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {


        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            //Send Verification Email
                            sendEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Home.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            if(user.isEmailVerified()) {
                                //Enter App
                                checkName();
                            } else {
                                Toast.makeText(Home.this, "Email Verification Required. Re-Sending Email",
                                        Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Home.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        if (!task.isSuccessful()) {
                            Status.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    private void enterApp(String username) {
        //Enter App
        Intent intent = new Intent(this, ProfileDisplay.class);

        intent.putExtra("displayName", username);
        startActivity(intent);
    }

    private void checkName() {
        String username = mAuth.getCurrentUser().getDisplayName();
        if(username == null || username.equals("") ||  username.equals("CHANGE_USERNAME")) {
            Intent intent = new Intent(this, ChangeUsername.class);
            startActivity(intent);
        } else {
            enterApp(username);
        }
    }

    private void sendEmailVerification() {
        Toast.makeText(Home.this, "Sending Verification Email.",
                Toast.LENGTH_SHORT).show();

        // Send verification email
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Home.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Home.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = Email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Email.setError("Required.");
            valid = false;
        } else {
            Email.setError(null);
        }

        String password = Password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Password.setError("Required.");
            valid = false;
        } else {
            Password.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        Status.setVisibility(View.VISIBLE);

        if (user != null) {
            Status.setText(getString(R.string.emailpassword_status_fmt, user.getEmail(), user.isEmailVerified()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        } else {
            Status.setText(R.string.signed_out);
            //mDetailTextView.setText(null);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.Create) {
            createAccount(Email.getText().toString(), Password.getText().toString());
        } else if (i == R.id.Submit) {
            signIn(Email.getText().toString(), Password.getText().toString());
        }
    }

    @Override
    public void onBackPressed() {

    }
}
