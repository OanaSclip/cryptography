package com.example.tinoa.criptme;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button encrypt;
    Button decrypt;
    EditText plaintText;
    EditText cipherText;
    Validator validator;
    Solver solver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        encrypt = findViewById(R.id.encrypt_button);
        decrypt = findViewById(R.id.decrypt_button);
        plaintText = findViewById(R.id.plainText);
        cipherText = findViewById(R.id.cipherText);
        validator = new Validator();
        solver = new Solver();

        encryptHandler();
        decryptHandler();


    }

    private void encryptHandler() {
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plain = plaintText.getText().toString();
                Boolean ok = validator.plainTextValidation(plain);
                if (!ok) {
                    Toast.makeText(MainActivity.this, "please enter only small letters and spaces", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "to be computed", Toast.LENGTH_SHORT).show();
                    String cipher = solver.encrypt(plain);
                    cipherText.setText(cipher.toUpperCase());
                }
            }
        });

    }

    private void decryptHandler() {
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cipher = cipherText.getText().toString();
                Boolean ok = validator.cipherTextValidation(cipher);
                if (!ok) {
                    Toast.makeText(MainActivity.this, "please enter only capital letters and spaces", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "to be computed", Toast.LENGTH_SHORT).show();
                    String plain = solver.decrypt(cipher);
                    plaintText.setText(plain.toLowerCase());
                }
            }
        });

    }
}
