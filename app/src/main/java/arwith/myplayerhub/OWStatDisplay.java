package arwith.myplayerhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import arwith.myplayerhub.Overwatch.OverwatchStats;

public class OWStatDisplay extends AppCompatActivity implements View.OnClickListener{

    TextView name;
    TextView level;
    TextView endorseLevel;
    TextView gamesWon;
    TextView compSR;
    TextView compGamesPlayed;
    TextView compGamesWon;
    TextView winRate;
    TextView medals;
    TextView goldMedals;
    TextView goldMedalRate;

    ImageView icon;
    ImageView rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owstat_display);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        findViewById(R.id.backButtonStats).setOnClickListener(this);

        name = findViewById(R.id.overwatchName);
        level = findViewById(R.id.overwatchLevel);
        endorseLevel = findViewById(R.id.endorsementLevel);
        gamesWon = findViewById(R.id.gamesWon);
        compSR = findViewById(R.id.overwatchSR);
        compGamesPlayed = findViewById(R.id.gamesPlayed);
        compGamesWon = findViewById(R.id.gamesWonComp);
        winRate = findViewById(R.id.winRate);
        medals = findViewById(R.id.overwatchMedals);
        goldMedals = findViewById(R.id.goldMedals);
        goldMedalRate = findViewById(R.id.goldMedalsPerGame);

        icon = findViewById(R.id.profileImage);
        rank = findViewById(R.id.profileImage2);

        OverwatchStats overwatchStats;

        OverwatchStatistics stats = new OverwatchStatistics();
        try {
            overwatchStats = stats.downloadUserData("pc", "eu", getIntent().getStringExtra("name"), getIntent().getStringExtra("tag"));
        } catch (IOException e) {
            return;
        }

        if(overwatchStats.getPrivate()) {
            ImageView priv = findViewById(R.id.privateIMG);
            priv.setVisibility(View.VISIBLE);
        } else {
            ImageView priv = findViewById(R.id.privateIMG);
            priv.setVisibility(View.GONE);
            try {
                DecimalFormat decimalFormat = new DecimalFormat("##.#");

                double winR = ((double) overwatchStats.getCompetitiveStats().getGames().getWon() / (double) overwatchStats.getCompetitiveStats().getGames().getPlayed())*100;
                double medalR = ((double) overwatchStats.getCompetitiveStats().getAwards().getMedalsGold() / (double) overwatchStats.getCompetitiveStats().getGames().getPlayed());

                name.setText(overwatchStats.getName());
                level.setText(Integer.toString(overwatchStats.getLevel()+(overwatchStats.getPrestige()*100)));
                endorseLevel.setText(overwatchStats.getEndorsement());
                gamesWon.setText(overwatchStats.getGamesWon());
                compSR.setText(overwatchStats.getRating());
                compGamesPlayed.setText(Integer.toString(overwatchStats.getCompetitiveStats().getGames().getPlayed()));
                compGamesWon.setText(Integer.toString(overwatchStats.getCompetitiveStats().getGames().getWon()));
                winRate.setText(decimalFormat.format(winR)+"%");
                medals.setText(Integer.toString(overwatchStats.getCompetitiveStats().getAwards().getMedals()));
                goldMedals.setText(Integer.toString(overwatchStats.getCompetitiveStats().getAwards().getMedalsGold()));
                goldMedalRate.setText(decimalFormat.format(medalR));

                if(overwatchStats.getIcon() != "") {
                    icon.setImageBitmap(getBitmap(overwatchStats.getIcon()));
                }

                if(overwatchStats.getRatingIcon() != "") {
                    rank.setImageBitmap(getBitmap(overwatchStats.getRatingIcon()));
                }

            } catch (Exception e) {
                Log.e("SetStats", "Exception occured");
            }
        }
    }

    protected Bitmap getBitmap(String url) {
        String urldisplay = url;
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.backButtonStats) {
            Intent intent = new Intent(OWStatDisplay.this, ProfileDisplay.class);
            intent.putExtra("displayName", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
