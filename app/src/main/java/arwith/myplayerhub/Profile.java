package arwith.myplayerhub;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Profile {

    public String username;
    public String email;
    public List<Card> cards;

    public Profile() {
        this.username = "";
        this.email = "";
        this.cards = new ArrayList<>();
    }

    public Profile(String username, String email, List<Card> cards) {
        this.username = username;
        this.email = email;
        this.cards = cards;
    }

    public Profile getProfile(final String userID) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("profiles");

        final Profile profile = new Profile();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(userID).exists()) {
                    //returning user
                    profile.username = (String) dataSnapshot.child(userID).child("username").getValue();
                    profile.email = (String) dataSnapshot.child(userID).child("email").getValue();

                    List<Card> cardList = new ArrayList<>();

                    for(DataSnapshot data: dataSnapshot.child(userID).child("info").getChildren()) {
                        Card newCard = new Card(
                                (String) data.child("accountType").getValue(),
                                (String) data.child("accountInfo").getValue(),
                                ((Long) data.child("cardID").getValue()).intValue(),
                                ((Long) data.child("backCol").getValue()).intValue(),
                                (boolean) data.child("isLinked").getValue(),
                                (String) data.child("link").getValue(),
                                (boolean) data.child("deleter").getValue()
                                );
                        cardList.add(newCard);
                    }

                    profile.cards = cardList;

                } else {
                    //new user
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("profiles");
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    profile.username = mAuth.getCurrentUser().getDisplayName();
                    profile.email = mAuth.getCurrentUser().getEmail();
                    ref.child(userID).setValue(profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Profile", "Failed to read value.", error.toException());
            }
        });

        return profile;
    }

    public Profile getOtherProfile(final String username) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("profiles");

        final Profile profileOther = new Profile();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    if(data.child("username").getValue().equals(username)) {

                        List<Card> cardList = new ArrayList<>();

                        for(DataSnapshot dataC: data.child("info").getChildren()) {
                            Card newCard = new Card(
                                    (String) dataC.child("accountType").getValue(),
                                    (String) dataC.child("accountInfo").getValue(),
                                    ((Long) dataC.child("cardID").getValue()).intValue(),
                                    ((Long) dataC.child("backCol").getValue()).intValue(),
                                    (boolean) dataC.child("isLinked").getValue(),
                                    (String) dataC.child("link").getValue(),
                                    false
                            );

                            cardList.add(newCard);
                        }


                        profileOther.username = (String) data.child("username").getValue();
                        profileOther.cards = cardList;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Profile", "Failed to read value.", error.toException());
            }
        });

        return profileOther;
    }

    public void addCard(String userID, Card card) {
        this.cards.add(card);
        FirebaseDatabase.getInstance().getReference().child("profiles").child(userID).child("info").setValue(this.cards);
    }

    public void removeCard(String userID, Card card) {
        this.cards.remove(card);
        FirebaseDatabase.getInstance().getReference().child("profiles").child(userID).child("info").setValue(this.cards);
    }
}
