package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import eite.ulpgc.eite.da.peluqueriaapp.R;

public class registerTaskActivity extends AppCompatActivity implements registerTaskContract.View {

    private registerTaskContract.Presenter presenter;

    private TextView tvMiCitaFechaHora;
    private TextView tvMiCitaServicio;
    private TextView tvCitaReservadaTitle;
    private View cvCitaCard;
    private Button btnCancelCita;
    private ImageView btnHeaderBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citareservada);

        tvMiCitaFechaHora = findViewById(R.id.tvMiCitaFechaHora);
        tvMiCitaServicio = findViewById(R.id.tvMiCitaServicio);
        tvCitaReservadaTitle = findViewById(R.id.tvCitaReservadaTitle);
        cvCitaCard = findViewById(R.id.cvCitaCard);
        btnCancelCita = findViewById(R.id.btnCancelCita);
        btnHeaderBack = findViewById(R.id.btnHeaderBack);

        registerTaskScreen.configure(this);

        btnCancelCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCancelClicked();
            }
        });

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
    public void injectPresenter(registerTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayAppointment(registerTaskViewModel viewModel) {
        if (viewModel != null) {
            tvMiCitaFechaHora.setText(viewModel.dateTime);
            tvMiCitaServicio.setText(viewModel.serviceName);

            if (viewModel.hasAppointment) {
                cvCitaCard.setVisibility(View.VISIBLE);
                btnCancelCita.setVisibility(View.VISIBLE);
                tvCitaReservadaTitle.setText("Cita Reservada");
            } else {
                cvCitaCard.setVisibility(View.GONE);
                btnCancelCita.setVisibility(View.GONE);
                tvCitaReservadaTitle.setText("No tienes citas reservadas");
            }
        }
    }

    @Override
    public void showCancellationSuccess() {
        Toast.makeText(this, "Cita cancelada con éxito", Toast.LENGTH_LONG).show();
    }
}
