package eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import eite.ulpgc.eite.da.peluqueriaapp.R;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class registerUserActivity extends AppCompatActivity implements registerUserContract.View {

    private registerUserContract.Presenter presenter;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegisterContinue;
    private TextView tvRegisterError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearusuario);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegisterContinue = findViewById(R.id.btnRegisterContinue);
        tvRegisterError = findViewById(R.id.tvRegisterError);

        registerUserScreen.configure(this);

        btnRegisterContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterClicked(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        android.widget.ImageView btnHeaderBack = findViewById(R.id.btnHeaderBack);
        if (btnHeaderBack != null) {
            btnHeaderBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void injectPresenter(registerUserContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayData(registerUserViewModel viewModel) {
        if (viewModel != null) {
            etEmail.setText(viewModel.email);
            etPassword.setText(viewModel.password);
            if (viewModel.errorMessage != null && !viewModel.errorMessage.isEmpty()) {
                tvRegisterError.setText(viewModel.errorMessage);
                tvRegisterError.setVisibility(View.VISIBLE);
            } else {
                tvRegisterError.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void navigateToStatus() {
        AppMediator.getInstance().goToStatus(this);
        finish();
    }

    @Override
    public void showRegisterError(String message) {
        tvRegisterError.setText(message);
        tvRegisterError.setVisibility(View.VISIBLE);
    }
}
