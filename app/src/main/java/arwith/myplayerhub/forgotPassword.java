package arwith.myplayerhub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity implements View.OnClickListener {

    TextView emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById(R.id.resetPassword).setOnClickListener(this);
        findViewById(R.id.backButtonForgot).setOnClickListener(this);

        emailAddress = findViewById(R.id.emailAddressReset);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.resetPassword) {

            if (TextUtils.isEmpty(emailAddress.getText().toString())) {
                emailAddress.setError("Required.");
                return;
            } else {
                emailAddress.setError(null);
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.sendPasswordResetEmail(emailAddress.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(forgotPassword.this,
                                        "Reset Email sent to "+emailAddress.getText().toString(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(forgotPassword.this,
                                        "Failed to send email. Check email address is correct and registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if(i == R.id.backButtonForgot) {
            startActivity(new Intent(forgotPassword.this, Home.class));
        }
    }
}
