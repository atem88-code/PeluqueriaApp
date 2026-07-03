package eite.ulpgc.eite.da.peluqueriaapp.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import eite.ulpgc.eite.da.peluqueriaapp.R;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class loginActivity extends AppCompatActivity implements loginContract.View {

    private loginContract.Presenter presenter;

    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private Button btnLoginContinue;
    private Button btnLoginGuest;
    private Button btnLoginRegister;
    private TextView tvLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppMediator.getInstance().loadSession(this);
        if (AppMediator.getInstance().getLoggedUser() != null) {
            navigateToHome();
            return;
        }

        setContentView(R.layout.login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLoginContinue = findViewById(R.id.btnLoginContinue);
        btnLoginGuest = findViewById(R.id.btnLoginGuest);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        tvLoginError = findViewById(R.id.tvLoginError);

        loginScreen.configure(this);

        btnLoginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginClicked(etLoginEmail.getText().toString(), etLoginPassword.getText().toString());
            }
        });

        btnLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onGuestClicked();
            }
        });

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void injectPresenter(loginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayData(loginViewModel viewModel) {
        if (viewModel != null) {
            etLoginEmail.setText(viewModel.email);
            etLoginPassword.setText(viewModel.password);
            if (viewModel.errorMessage != null && !viewModel.errorMessage.isEmpty()) {
                tvLoginError.setText(viewModel.errorMessage);
                tvLoginError.setVisibility(View.VISIBLE);
            } else {
                tvLoginError.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void navigateToHome() {
        AppMediator.getInstance().goToHome(this);
        if (AppMediator.getInstance().getLoggedUser() != null) {
            finish();
        }
    }

    @Override
    public void navigateToRegisterUser() {
        AppMediator.getInstance().goToRegisterUser(this);
    }

    @Override
    public void showLoginError(String message) {
        tvLoginError.setText(message);
        tvLoginError.setVisibility(View.VISIBLE);
    }
}
