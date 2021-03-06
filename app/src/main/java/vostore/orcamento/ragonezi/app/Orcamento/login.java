package vostore.orcamento.ragonezi.app.Orcamento;

import android.content.Intent;

import vostore.orcamento.ragonezi.app.Firebase.ConfiguracaoFirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import vostore.apporcamentoragonezi.R;

public class login extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private EditText senhausuario,emailusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        verificarUsuarioLogado();

        //Cast
        btnLogin = findViewById(R.id.btn_login);

        senhausuario = findViewById(R.id.senhaid);
        emailusuario = findViewById(R.id.emailid);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = emailusuario.getText().toString();
        if (validaremail(email) && validarsenha()){
            String senha = senhausuario.getText().toString();
            mAuth.signInWithEmailAndPassword(email,senha)
                    .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // Toast.makeText(login.this,"login bem sucedido",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this,Main2Activity.class);
                                startActivity(intent);
                                finish();
                                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            } else{
                                Toast toast = new Toast(login.this);
                                ImageView view = new ImageView(login.this);
                                view.setImageResource(R.drawable.toast_erro);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.setView(view);
                                toast.show();



                                // Toast.makeText(login.this,"Não foi possível entrar no ambiente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(login.this,"Erro de login.",Toast.LENGTH_SHORT).show();
        }

    }
    private boolean validaremail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean validarsenha(){
        String contraseña;
        contraseña = senhausuario.getText().toString();
        if(contraseña.length()>=6 && contraseña.length()<=16){
            return true;
        }else return false;
    }
    //Abrir tela seguinte
    private  void updateUI(){
        // Toast.makeText(login.this, "login Realizado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login.this, Main2Activity.class);
        startActivity(intent);
        finish();

    }
    private void verificarUsuarioLogado(){
        mAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(mAuth.getCurrentUser()!= null ){
            updateUI();
        }
    }
}
