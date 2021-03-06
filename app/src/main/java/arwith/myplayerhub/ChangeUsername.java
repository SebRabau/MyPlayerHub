package arwith.myplayerhub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeUsername extends AppCompatActivity implements View.OnClickListener{

    private EditText newUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        findViewById(R.id.EnterApp).setOnClickListener(this);
        newUsername = findViewById(R.id.newUsername);

        findViewById(R.id.UsernameInUse).setVisibility(View.GONE);
        findViewById(R.id.invalidUsername).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.EnterApp) {
            //Check for spaces/symbols and length
            if(TextUtils.isEmpty(newUsername.getText())) {
                newUsername.setError("Required.");
                return;
            } else {

                if((TextUtils.isDigitsOnly(newUsername.getText())) ||(newUsername.getText().toString().length() > 12) || !(newUsername.getText().toString().matches("^[a-zA-Z0-9]*$"))) {
                    findViewById(R.id.invalidUsername).setVisibility(View.VISIBLE);
                    return;
                }
                newUsername.setError(null);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("profiles");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean taken = false;

                        for(DataSnapshot data: dataSnapshot.getChildren()) {
                            if(data.child("username").getValue().equals(newUsername.getText().toString().trim())) {
                                taken = true;
                                break;
                            }
                        }

                        if(!taken) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(newUsername.getText().toString().trim()).build();
                            user.updateProfile(profileUpdates);

                            FirebaseDatabase.getInstance().getReference().child("profiles").child(user.getUid()).child("username").setValue(newUsername.getText().toString().trim());
                            FirebaseDatabase.getInstance().getReference().child("profiles").child(user.getUid()).child("email").setValue(user.getEmail());

                            Intent intent = new Intent(ChangeUsername.this, ProfileDisplay.class);
                            intent.putExtra("displayName", newUsername.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            findViewById(R.id.UsernameInUse).setVisibility(View.VISIBLE);
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
