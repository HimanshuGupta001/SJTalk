package project.sjtalk.com.sjtalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextMobile;
    private Button buttonSave;
    private Spinner staticSpinner;
    private Spinner dynamicSpinner;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


         staticSpinner = (Spinner) findViewById(R.id.batch_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.profile_array,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        dynamicSpinner = (Spinner) findViewById(R.id.branch_spinner);

        String[] items = new String[] { "CSE", "ECE", "EEE", "ME" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);
        dynamicSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            //Profile Activity here
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextMobile=(EditText)findViewById(R.id.editTextMobile);
        buttonSave=(Button)findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(this);
   }


    private void saveUserInformation(){
        String name=editTextName.getText().toString().trim();
        String mobile=editTextMobile.getText().toString().trim();
        String batch=staticSpinner.getSelectedItem().toString().trim();
        String branch=dynamicSpinner.getSelectedItem().toString().trim();
        UserInformation userInformation=new UserInformation(name,mobile,batch,branch);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Information Saved...",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View v) {

        if(v==buttonSave)
        {
            saveUserInformation();
        }

    }




}
