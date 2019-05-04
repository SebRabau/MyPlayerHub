package arwith.myplayerhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

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

    //Database
    private FirebaseAuth mAuth;
    private Profile userProfile = new Profile();


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
        findViewById(R.id.signOutBtn).setOnClickListener(this);

        popup = findViewById(R.id.popup);
        newCardPopup = findViewById(R.id.cardPopup);
        newCardInfo = findViewById(R.id.accountTag);
        newCardLink = findViewById(R.id.accountLink);
        cardList = findViewById(R.id.cardList);
        mainLayout = findViewById(R.id.mainLayout);

        SearchView searchView = findViewById(R.id.SearchBox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent viewProfile = new Intent(ProfileDisplay.this, DisplayOther.class);
                viewProfile.putExtra("otherUsername", query.trim());
                startActivity(viewProfile);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        popup.setVisibility(View.GONE);
        newCardPopup.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        userProfile = userProfile.getProfile(mAuth.getCurrentUser().getUid());
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                cardList.removeAllViews();
                displayCards(userProfile.cards);
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView usernameText = findViewById(R.id.Username);
        usernameText.setText(displayname);
        displayCards(userProfile.cards);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mAuth.signOut();

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mAuth.signOut();

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
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
            FirebaseDatabase.getInstance().getReference().child("usernames").child(mAuth.getCurrentUser().getDisplayName()).removeValue();
            FirebaseDatabase.getInstance().getReference().child("profiles").child(mAuth.getCurrentUser().getUid()).child("username").setValue("CHANGE_USERNAME");

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName("CHANGE_USERNAME").build();
            mAuth.getCurrentUser().updateProfile(profileUpdates);

            Intent intent = new Intent(this, ChangeUsername.class);
            startActivity(intent);
        } else if(i == R.id.signOutBtn) {
            mAuth.signOut();

            Intent intent = new Intent(this, Home.class);
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
                    newCardLink.getText().toString(),
                    true
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
                    newCardLink.getText().toString(),
                    true
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
                    newCardLink.getText().toString(),
                    true
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
                    newCardLink.getText().toString(),
                    true
            );

            cardList.addView(card);
        }

        newCardPopup.setVisibility(View.GONE);
        newCardLink.setText("");
        newCardInfo.setText("");

        hideKeyboardFrom(this, mainLayout);
    }

    private LinearLayout createCard(final String accountType, final String accountInfo, int cardID, int backCol, boolean isLinked, String link, boolean deleter) {
        final LinearLayout card = new LinearLayout(this);
        card.setBackgroundColor(backCol);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(5,5,0,5);

        final Card dbCard = new Card(accountType, accountInfo, cardID, backCol, isLinked, link, deleter);

        TextView type = new TextView(this);
        if(cardID > 1) {
            type.setText(accountType);
        } else {
            type.setText(accountType);
        }
        type.setTextSize(18);
        type.setTextColor(Color.WHITE);
        type.setTypeface(Typeface.DEFAULT_BOLD);

        TextView info = new TextView(this);
        info.setText(accountInfo.trim());
        info.setTextSize(18);
        info.setTextColor(Color.WHITE);

        card.addView(type);
        card.addView(info);

        if(isLinked && link != "") {
            final String accountLink = link.trim();

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

        if(accountType.equals("BattleNet")) {
            Button OWStats = new Button(this);
            OWStats.setText("OW Stats");
            OWStats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringBuilder name = new StringBuilder();
                    StringBuilder tag = new StringBuilder();
                    boolean tagged = false;

                    for(char c: accountInfo.toCharArray()) {
                        if(!tagged) {
                            if(c == '#') {
                                tagged = true;
                            } else {
                                name.append(c);
                            }
                        } else {
                            tag.append(c);
                        }
                    }

                    Intent intent = new Intent(ProfileDisplay.this, OWStatDisplay.class);
                    intent.putExtra("name", name.toString());
                    intent.putExtra("tag", tag.toString());
                    startActivity(intent);
                }
            });

            card.addView(OWStats);
        }

        if(deleter) {
            Button deleteCard = new Button(this);
            deleteCard.setMaxWidth(100);
            deleteCard.setText("!^^ DELETE ^^!");
            deleteCard.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            deleteCard.setPadding(0,0,0,0);
            deleteCard.setBackgroundColor(Color.RED);
            deleteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(accountType == "Steam") {
                        steamNum--;
                    } else if(accountType == "BattleNet") {
                        bnetNum--;
                    } else if(accountType == "Origin") {
                        originNum--;
                    } else if(accountType == "Epic Games") {
                        epicNum--;
                    }

                    userProfile.removeCard(mAuth.getCurrentUser().getUid(), dbCard);

                    cardList.removeView(card);


                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60);
            deleteCard.setLayoutParams(params);

            card.addView(deleteCard);
        }

        userProfile.addCard(mAuth.getCurrentUser().getUid(), dbCard);
        return card;
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

        if(dbCard.accountType.equals("BattleNet")) {
            Button OWStats = new Button(this);
            OWStats.setText("OW Stats");
            OWStats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringBuilder name = new StringBuilder();
                    StringBuilder tag = new StringBuilder();
                    boolean tagged = false;

                    for(char c: dbCard.accountInfo.toCharArray()) {
                        if(!tagged) {
                            if(c == '#') {
                                tagged = true;
                            } else {
                                name.append(c);
                            }
                        } else {
                            tag.append(c);
                        }
                    }

                    Intent intent = new Intent(ProfileDisplay.this, OWStatDisplay.class);
                    intent.putExtra("name", name.toString());
                    intent.putExtra("tag", tag.toString());
                    startActivity(intent);
                }
            });

            card.addView(OWStats);
        }

        if(dbCard.deleter) {
            Button deleteCard = new Button(this);
            deleteCard.setMaxWidth(100);
            deleteCard.setText("!^^ DELETE ^^!");
            deleteCard.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            deleteCard.setPadding(0,0,0,0);
            deleteCard.setBackgroundColor(Color.RED);
            deleteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dbCard.accountType == "Steam") {
                        steamNum--;
                    } else if(dbCard.accountType == "BattleNet") {
                        bnetNum--;
                    } else if(dbCard.accountType == "Origin") {
                        originNum--;
                    } else if(dbCard.accountType == "Epic Games") {
                        epicNum--;
                    }

                    userProfile.removeCard(mAuth.getCurrentUser().getUid(), dbCard);

                    cardList.removeView(card);


                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60);
            deleteCard.setLayoutParams(params);

            card.addView(deleteCard);
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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {

    }
}
