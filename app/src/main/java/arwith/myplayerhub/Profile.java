package arwith.myplayerhub;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile {

    public String username;
    public String email;

    public FirebaseDatabase database;

    private String TAG = "Profile";

    public Profile() {

    }

    public Profile(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void checkOrAddProfile(String username, String email) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Accounts");

        ref.setValue("email");

        // Read from the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
