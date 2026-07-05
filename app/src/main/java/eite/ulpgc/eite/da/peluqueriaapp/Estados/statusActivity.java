package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import eite.ulpgc.eite.da.peluqueriaapp.R;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class statusActivity extends AppCompatActivity implements statusContract.View {

    private statusContract.Presenter presenter;

    private ImageView ivSuccessIcon;
    private TextView tvStatusTitle;
    private CardView cvStatusDetail;
    private TextView tvStatusFecha;
    private TextView tvStatusHora;
    private TextView tvStatusServicio;
    private TextView tvStatusMessage;
    private Button btnStatusAction;
    private Button btnViewAppointment;

    private boolean showDetail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        ivSuccessIcon = findViewById(R.id.ivSuccessIcon);
        tvStatusTitle = findViewById(R.id.tvStatusTitle);
        cvStatusDetail = findViewById(R.id.cvStatusDetail);
        tvStatusFecha = findViewById(R.id.tvStatusFecha);
        tvStatusHora = findViewById(R.id.tvStatusHora);
        tvStatusServicio = findViewById(R.id.tvStatusServicio);
        tvStatusMessage = findViewById(R.id.tvStatusMessage);
        btnStatusAction = findViewById(R.id.btnStatusAction);
        btnViewAppointment = findViewById(R.id.btnViewAppointment);

        statusScreen.configure(this);

        ImageView btnHeaderBack = findViewById(R.id.btnHeaderBack);
        if (btnHeaderBack != null) {
            btnHeaderBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        btnStatusAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onActionClicked();
            }
        });

        btnViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onViewAppointmentClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void injectPresenter(statusContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayData(statusViewModel viewModel) {
        if (viewModel != null) {
            tvStatusTitle.setText(viewModel.title);
            this.showDetail = viewModel.showDetail;

            if (showDetail) {
                cvStatusDetail.setVisibility(View.VISIBLE);
                tvStatusMessage.setVisibility(View.GONE);
                tvStatusFecha.setText(viewModel.date);
                tvStatusHora.setText(viewModel.time);
                tvStatusServicio.setText(viewModel.serviceName);
                btnStatusAction.setText("Volver al inicio");
                btnViewAppointment.setVisibility(View.VISIBLE);
            } else {
                cvStatusDetail.setVisibility(View.GONE);
                tvStatusMessage.setVisibility(View.VISIBLE);
                tvStatusMessage.setText(viewModel.message);
                btnStatusAction.setText("Iniciar Sesión");
                btnViewAppointment.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void navigateToHomeOrLogin() {
        if (showDetail) {
            AppMediator.getInstance().goToHome(this);
        } else {
            AppMediator.getInstance().goToLogin(this);
        }
        finish();
    }

    @Override
    public void navigateToMyAppointments() {
        AppMediator.getInstance().goToRegisterTask(this);
        finish();
    }
}
