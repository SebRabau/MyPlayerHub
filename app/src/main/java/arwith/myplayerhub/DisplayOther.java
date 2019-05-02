package arwith.myplayerhub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DisplayOther extends AppCompatActivity implements View.OnClickListener {

    String otherUsername;
    TextView username;
    LinearLayout cardList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_other);

        Intent intent = getIntent();
        otherUsername = intent.getStringExtra("otherUsername");

        findViewById(R.id.backButton).setOnClickListener(this);
        cardList = findViewById(R.id.cardListOther);
        username = findViewById(R.id.UsernameOther);
        username.setText(otherUsername);

        Profile other = new Profile();
        other = other.getOtherProfile(otherUsername);

        final Profile otherFinal = other;

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                displayCards(otherFinal.cards);
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.backButton ) {
            Intent intent = new Intent(DisplayOther.this, ProfileDisplay.class);
            intent.putExtra("displayName", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            startActivity(intent);
        }
    }

    private void createCard(final Card dbCard) {
        final LinearLayout card = new LinearLayout(this);
        card.setBackgroundColor(dbCard.backCol);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(5,5,0,5);

        TextView type = new TextView(this);
        type.setText(dbCard.accountType);
        type.setTextSize(18);
        type.setTextColor(Color.WHITE);
        type.setTypeface(Typeface.DEFAULT_BOLD);

        TextView info = new TextView(this);
        info.setText(dbCard.accountInfo.trim());
        info.setTextSize(18);
        info.setTextColor(Color.WHITE);

        card.addView(type);
        card.addView(info);

        if(dbCard.isLinked && dbCard.link != "") {
            final String accountLink = dbCard.link.trim();

            Button profileLink = new Button(this);
            profileLink.setText("GO TO PROFILE");
            profileLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String url = URLUtil.guessUrl(accountLink);
                        Uri uriUrl = Uri.parse(url);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            card.addView(profileLink);
        }

        cardList.addView(card);
    }

    public void displayCards(List<Card> list) {
        if(list != null && !list.isEmpty()) {
            for(int i=0; i<list.size(); i++) {
                createCard(list.get(i));
            }
        }
    }
}
