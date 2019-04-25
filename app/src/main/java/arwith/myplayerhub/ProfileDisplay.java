package arwith.myplayerhub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Debug;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class ProfileDisplay extends AppCompatActivity implements View.OnClickListener {

    private String displayname = "Unknown User";
    private LinearLayout popup;
    private LinearLayout newCardPopup;
    private LinearLayout cardList;
    private EditText newCardInfo;
    private EditText newCardLink;
    private int accountType;

    private int bnetNum = 0;
    private int epicNum = 0;
    private int originNum = 0;
    private int steamNum = 0;

    private ConstraintLayout mainLayout;


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
        findViewById(R.id.addCard).setOnClickListener(this);
        findViewById(R.id.addCardSubmit).setOnClickListener(this);
        findViewById(R.id.closePopup2).setOnClickListener(this);

        popup = findViewById(R.id.popup);
        newCardPopup = findViewById(R.id.cardPopup);
        newCardInfo = findViewById(R.id.accountTag);
        newCardLink = findViewById(R.id.accountLink);
        cardList = findViewById(R.id.cardList);
        mainLayout = findViewById(R.id.mainLayout);

        popup.setVisibility(View.GONE);
        newCardPopup.setVisibility(View.GONE);

        popup.setFocusable(true);
        newCardPopup.setFocusable(true);
        mainLayout.setFocusable(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView usernameText = findViewById(R.id.Username);
        usernameText.setText(displayname);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.addCard && newCardPopup.getVisibility() == View.GONE) {
            popup.setVisibility(View.VISIBLE);
            popup.requestFocus();


        } else if(i == R.id.closePopup) {
            popup.setVisibility((View.GONE));
            mainLayout.requestFocus();

        } else if(i == R.id.closePopup2) {
            newCardPopup.setVisibility((View.GONE));
            mainLayout.requestFocus();

        } else if(i == R.id.bnetAdd) {
            accountType = 1;
            newCardPopup.setVisibility(View.VISIBLE);
            popup.setVisibility((View.GONE));
            newCardPopup.requestFocus();

        } else if(i == R.id.epicAdd) {
            accountType = 2;
            newCardPopup.setVisibility(View.VISIBLE);
            popup.setVisibility((View.GONE));
            newCardPopup.requestFocus();

        } else if(i == R.id.originAdd) {
            accountType = 3;
            newCardPopup.setVisibility(View.VISIBLE);
            popup.setVisibility((View.GONE));
            newCardPopup.requestFocus();

        } else if(i == R.id.steamAdd) {
            accountType = 4;
            newCardPopup.setVisibility(View.VISIBLE);
            popup.setVisibility((View.GONE));
            newCardPopup.requestFocus();

        } else if(i == R.id.addCardSubmit) {
            createCard(accountType);
            newCardPopup.setVisibility(View.GONE);
            mainLayout.requestFocus();

        } else if(i == R.id.changeUsername) {
            Intent intent = new Intent(this, ChangeUsername.class);
            startActivity(intent);
        }
    }

    private void createCard(int type) {
        boolean isLinked = false;

        if(type == 1) {
            bnetNum++;
            if (newCardLink.getText().toString().length() > 1) {
                isLinked = true;
            }

            LinearLayout card = createCard(
                    "BattleNet",
                    newCardInfo.getText().toString(),
                    bnetNum,
                    Color.rgb(65, 105, 225),
                    isLinked,
                    newCardLink.getText().toString()
            );

            cardList.addView(card);
        } else if(type == 2) {
            epicNum++;
            if (newCardLink.getText().toString().length() > 1) {
                isLinked = true;
            }

            LinearLayout card = createCard(
                    "Epic Games",
                    newCardInfo.getText().toString(),
                    epicNum,
                    Color.GRAY,
                    isLinked,
                    newCardLink.getText().toString()
            );

            cardList.addView(card);
        } else if(type == 3) {
            originNum++;
            if (newCardLink.getText().toString().length() > 1) {
                isLinked = true;
            }

            LinearLayout card = createCard(
                    "Origin",
                    newCardInfo.getText().toString(),
                    originNum,
                    Color.rgb(255, 165, 0),
                    isLinked,
                    newCardLink.getText().toString()
            );

            cardList.addView(card);
        } else if(type == 4) {
            steamNum++;
            if (newCardLink.getText().toString().length() > 1) {
                isLinked = true;
            }

            LinearLayout card = createCard(
                    "Steam",
                    newCardInfo.getText().toString(),
                    steamNum,
                    Color.BLACK,
                    isLinked,
                    newCardLink.getText().toString()
            );

            cardList.addView(card);
        }

        //SAVE TO DATABASE

        newCardPopup.setVisibility(View.GONE);
        newCardLink.setText("");
        newCardInfo.setText("");
    }

    private LinearLayout createCard(String accountType, String accountInfo, int cardID, int backCol, boolean isLinked, String link) {
        LinearLayout card = new LinearLayout(this);
        card.setBackgroundColor(backCol);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(5,5,0,5);

        TextView type = new TextView(this);
        if(cardID > 1) {
            type.setText(accountType+" "+cardID);
        } else {
            type.setText(accountType);
        }
        type.setTextSize(18);
        type.setTextColor(Color.WHITE);
        type.setTypeface(Typeface.DEFAULT_BOLD);

        TextView info = new TextView(this);
        info.setText(accountInfo);
        info.setTextSize(18);
        info.setTextColor(Color.WHITE);

        card.addView(type);
        card.addView(info);

        if(isLinked && link != "") {
            final String accountLink = link;

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

        return card;
    }
}
