package eite.ulpgc.eite.da.peluqueriaapp.home;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import eite.ulpgc.eite.da.peluqueriaapp.R;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;

public class homeActivity extends AppCompatActivity implements homeContract.View {

    private homeContract.Presenter presenter;

    private TextView tvUserGreeting;
    private TextView tvUserInitial;
    private Button btnNavSolicitarCita;
    private Button btnNavMostrarCita;
    private Button btnNavListaServicios;
    private Button btnNavFavorito;
    private TextView tvLogout;

    private ConstraintLayout layoutLoggedIn;
    private ConstraintLayout layoutGuest;
    private Button btnGuestListaServicios;
    private TextView tvGuestLogout;
    private TextView tvGuestGoToLogin;
    private TextView subHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

        tvUserGreeting = findViewById(R.id.tvUserGreeting);
        tvUserInitial = findViewById(R.id.tvUserInitial);
        btnNavSolicitarCita = findViewById(R.id.btnNavSolicitarCita);
        btnNavMostrarCita = findViewById(R.id.btnNavMostrarCita);
        btnNavListaServicios = findViewById(R.id.btnNavListaServicios);
        btnNavFavorito = findViewById(R.id.btnNavFavorito);
        tvLogout = findViewById(R.id.tvLogout);

        layoutLoggedIn = findViewById(R.id.layoutLoggedIn);
        layoutGuest = findViewById(R.id.layoutGuest);
        btnGuestListaServicios = findViewById(R.id.btnGuestListaServicios);
        tvGuestLogout = findViewById(R.id.tvGuestLogout);
        tvGuestGoToLogin = findViewById(R.id.tvGuestGoToLogin);
        subHeader = findViewById(R.id.subHeader);

        homeScreen.configure(this);

        UserEntity user = AppMediator.getInstance().getLoggedUser();
        if (user != null) {
            layoutLoggedIn.setVisibility(View.VISIBLE);
            layoutGuest.setVisibility(View.GONE);
            subHeader.setText("Nombre de la Peluquería");

            tvUserGreeting.setText("Hola, " + user.email);
            if (!user.email.isEmpty()) {
                tvUserInitial.setText(user.email.substring(0, 1).toUpperCase());
            }
        } else {
            layoutLoggedIn.setVisibility(View.GONE);
            layoutGuest.setVisibility(View.VISIBLE);
            subHeader.setText("Modo Invitado");
        }

        btnNavSolicitarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterProjectBtnClicked();
            }
        });

        btnNavMostrarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterTaskBtnClicked();
            }
        });

        btnNavListaServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onProjectListBtnClicked();
            }
        });

        btnNavFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFavoritesBtnClicked();
            }
        });

        btnGuestListaServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onProjectListBtnClicked();
            }
        });

        tvGuestGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMediator.getInstance().goToLogin(homeActivity.this);
                finish();
            }
        });

        View.OnClickListener logoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMediator.getInstance().setLoggedUser(null);
                AppMediator.getInstance().saveSession(homeActivity.this, null);
                AppMediator.getInstance().goToLogin(homeActivity.this);
                finish();
            }
        };

        tvLogout.setOnClickListener(logoutListener);
        tvGuestLogout.setOnClickListener(logoutListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void injectPresenter(homeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayData(homeViewModel viewModel) {
    }

    @Override
    public void navigateToRegisterProject() {
        AppMediator.getInstance().goToRegisterProject(this);
    }

    @Override
    public void navigateToRegisterTask() {
        AppMediator.getInstance().goToRegisterTask(this);
    }

    @Override
    public void navigateToProjectList() {
        AppMediator.getInstance().goToProjectList(this);
    }

    @Override
    public void showFavoritesMessage() {
        Toast.makeText(this, "Favoritos actualizados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGuestMessage() {
        Toast.makeText(this,
                "Modo Invitado: Acceso limitado.",
                Toast.LENGTH_LONG).show();
    }
}
