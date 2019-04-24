package arwith.myplayerhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileDisplay extends AppCompatActivity {

    String displayname = "Unknown User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        Intent intent = getIntent();
        displayname = intent.getStringExtra("displayName");
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView usernameText = (TextView) findViewById(R.id.Username);
        usernameText.setText(displayname);
    }
}
