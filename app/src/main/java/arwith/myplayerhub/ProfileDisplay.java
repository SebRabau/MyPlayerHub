package arwith.myplayerhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileDisplay extends AppCompatActivity implements View.OnClickListener {

    String displayname = "Unknown User";
    LinearLayout popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        Intent intent = getIntent();
        displayname = intent.getStringExtra("displayName");

        findViewById(R.id.addCard).setOnClickListener(this);
        findViewById(R.id.bnetAdd).setOnClickListener(this);
        findViewById(R.id.epicAdd).setOnClickListener(this);
        findViewById(R.id.originAdd).setOnClickListener(this);
        findViewById(R.id.steamAdd).setOnClickListener(this);
        findViewById(R.id.closePopup).setOnClickListener(this);
        findViewById(R.id.changeUsername).setOnClickListener(this);

        popup = findViewById(R.id.popup);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView usernameText = (TextView) findViewById(R.id.Username);
        usernameText.setText(displayname);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.addCard) {
            popup.setVisibility(View.VISIBLE);
        } else if(i == R.id.closePopup) {
            popup.setVisibility((View.GONE));
        } else if(i == R.id.steamAdd) {

        } else if(i == R.id.originAdd) {

        } else if(i == R.id.bnetAdd) {

        } else if(i == R.id.epicAdd) {

        } else if(i == R.id.changeUsername) {
            Intent intent = new Intent(this, ChangeUsername.class);
            startActivity(intent);
        }
    }
}
