package project.sjtalk.com.sjtalk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;


    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextMobile;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextMobile=(EditText)findViewById(R.id.editTextMobile);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            //Profile Activity here
            finish();
            startActivity(new Intent(getApplicationContext(),AccountActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister=(Button)findViewById(R.id.buttonRegister);


        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);

        textViewSignin=(TextView)findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String name=editTextName.getText().toString().trim();
        String mobile=editTextMobile.getText().toString().trim();



        if(TextUtils.isEmpty(name)){
            //email is empty
            Toast.makeText(this,"Please Enter your Name",Toast.LENGTH_SHORT).show();
            //stopping function executing further
            return;
        }
        if(TextUtils.isEmpty(mobile)){
            //email is empty
            Toast.makeText(this,"Please Enter your Mobile",Toast.LENGTH_SHORT).show();
            //stopping function executing further
            return;
        }
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            //stopping function executing further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            //stopping function executing further
            return;
        }
        //if validations are ok
        //we will first show a progressbar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    //user is Successfully Registered and LoggedIn
                    //we will Start the prifile activity here
                        //Profile Activity here
                        finish();
                    Toast.makeText(MainActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to Registered, Please Try Again",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });
    }

//    private void saveUserInformation(){
//        String name=editTextName.getText().toString().trim();
//        String mobile=editTextMobile.getText().toString().trim();
//        UserInformation userInformation=new UserInformation(name,mobile);
//
//        FirebaseUser user=firebaseAuth.getCurrentUser();
//        databaseReference.child(user.getUid()).setValue(userInformation);
//    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
        {
            registerUser();
//            saveUserInformation();
        }
        if (view == textViewSignin)
        {
            //will open login activity here
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}
