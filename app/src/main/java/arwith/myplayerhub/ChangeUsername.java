package arwith.myplayerhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeUsername extends AppCompatActivity implements View.OnClickListener{

    private EditText newUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        findViewById(R.id.EnterApp).setOnClickListener(this);
        newUsername = findViewById(R.id.newUsername);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.EnterApp) {
            if(newUsername.getText().toString() == "") {
                return;
            } else {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(newUsername.getText().toString()).build();
                user.updateProfile(profileUpdates);

                Intent intent = new Intent(this, ProfileDisplay.class);
                intent.putExtra("displayName", newUsername.getText().toString());
                startActivity(intent);
            }
        }
    }
}
