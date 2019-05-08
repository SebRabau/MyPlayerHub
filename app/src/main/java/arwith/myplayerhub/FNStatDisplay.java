package arwith.myplayerhub;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import xyz.dodo.fortnite.Fortnite;
import xyz.dodo.fortnite.entity.FortniteData;
import xyz.dodo.fortnite.entity.League;
import xyz.dodo.fortnite.entity.Stat;

public class FNStatDisplay extends AppCompatActivity implements View.OnClickListener {

    TextView name, soloS, duoS, squadS, soloWins, soloTth, soloTtn, soloWinpc, soloKD, soloKPG, soloKT, duoWins, duoTth, duoTtn, duoWinpc, duoKD, duoKPG, duoKT, squadWins, squadTth, squadTtn, squadWinpc, squadKD, squadKPG, squadKT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fnstat_display);

        findViewById(R.id.backButtonFNStats).setOnClickListener(this);

        getRefs();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Fortnite fortnite = new Fortnite("f3ca2d48-a1bb-44fc-b433-a74b4cb8f04d");
        FortniteData fortniteData = fortnite.getPlayerInfo("pc", getIntent().getStringExtra("epicName"));

        if(fortniteData.getResult().isOk()) {
            Log.d("FNTEST", fortniteData.getLeague(League.Mode.solo_current).getStats().toString());

            List<Stat> soloStats = fortniteData.getLeague(League.Mode.solo).getStats();
            List<Stat> duoStats = fortniteData.getLeague(League.Mode.duo).getStats();
            List<Stat> squadStats = fortniteData.getLeague(League.Mode.squad).getStats();

            name.setText(fortniteData.getPlayer().getNickname());
            soloS.setText(soloStats.get(0).getValue());
            duoS.setText(duoStats.get(0).getValue());
            squadS.setText(squadStats.get(0).getValue());


            soloWins.setText(soloStats.get(1).getValue());
            soloTth.setText(soloStats.get(2).getValue());
            soloTtn.setText(soloStats.get(5).getValue());
            soloWinpc.setText(soloStats.get(9).getValue()+"%");
            soloKD.setText(soloStats.get(8).getValue());
            soloKPG.setText(soloStats.get(12).getValue());
            soloKT.setText(soloStats.get(11).getValue());

            duoWins.setText(duoStats.get(1).getValue());
            duoTth.setText(duoStats.get(2).getValue());
            duoTtn.setText(duoStats.get(5).getValue());
            duoWinpc.setText(duoStats.get(9).getValue()+"%");
            duoKD.setText(duoStats.get(8).getValue());
            duoKPG.setText(duoStats.get(12).getValue());
            duoKT.setText(duoStats.get(11).getValue());

            squadWins.setText(squadStats.get(1).getValue());
            squadTth.setText(squadStats.get(2).getValue());
            squadTtn.setText(squadStats.get(5).getValue());
            squadWinpc.setText(squadStats.get(9).getValue()+"%");
            squadKD.setText(squadStats.get(8).getValue());
            squadKPG.setText(squadStats.get(12).getValue());
            squadKT.setText(squadStats.get(11).getValue());
        }
    }

    private void getRefs() {
        name = findViewById(R.id.fortniteName);
        soloS = findViewById(R.id.soloScore);
        duoS = findViewById(R.id.duoScore);
        squadS = findViewById(R.id.squadScore);

        soloWins = findViewById(R.id.soloWins);
        soloTth = findViewById(R.id.soloTop3);
        soloTtn = findViewById(R.id.soloTop10);
        soloWinpc = findViewById(R.id.soloWinpc);
        soloKD = findViewById(R.id.soloKD);
        soloKPG = findViewById(R.id.soloKPG);
        soloKT = findViewById(R.id.soloKillsT);

        duoWins = findViewById(R.id.duoWins);
        duoTth = findViewById(R.id.duoTop3);
        duoTtn = findViewById(R.id.duoTop10);
        duoWinpc = findViewById(R.id.duoWinpc);
        duoKD = findViewById(R.id.duoKD);
        duoKPG = findViewById(R.id.duoKPG);
        duoKT = findViewById(R.id.duoKillsT);

        squadWins = findViewById(R.id.squadWins);
        squadTth = findViewById(R.id.squadTop3);
        squadTtn = findViewById(R.id.squadTop10);
        squadWinpc = findViewById(R.id.squadWinpc);
        squadKD = findViewById(R.id.squadKD);
        squadKPG = findViewById(R.id.squadKPG);
        squadKT = findViewById(R.id.squadKillsT);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.backButtonFNStats) {
            Intent intent = new Intent(FNStatDisplay.this, ProfileDisplay.class);
            intent.putExtra("displayName", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            startActivity(intent);
        }
    }
}
