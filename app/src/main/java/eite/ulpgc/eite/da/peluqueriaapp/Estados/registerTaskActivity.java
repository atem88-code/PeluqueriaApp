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

    private TextView tvCitaReservadaTitle;
    private android.widget.LinearLayout llCitasContainer;
    private ImageView btnHeaderBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citareservada);

        tvCitaReservadaTitle = findViewById(R.id.tvCitaReservadaTitle);
        llCitasContainer = findViewById(R.id.llCitasContainer);
        btnHeaderBack = findViewById(R.id.btnHeaderBack);

        registerTaskScreen.configure(this);

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
        llCitasContainer.removeAllViews();
        if (viewModel != null && viewModel.appointments != null && !viewModel.appointments.isEmpty()) {
            tvCitaReservadaTitle.setText("Mis Citas Reservadas");
            for (final registerTaskViewModel.AppointmentItem item : viewModel.appointments) {
                View itemView = getLayoutInflater().inflate(R.layout.item_cita, llCitasContainer, false);

                TextView tvMiCitaFechaHora = itemView.findViewById(R.id.tvMiCitaFechaHora);
                TextView tvMiCitaServicio = itemView.findViewById(R.id.tvMiCitaServicio);
                Button btnCancelCita = itemView.findViewById(R.id.btnCancelCita);

                tvMiCitaFechaHora.setText(item.dateTime);
                tvMiCitaServicio.setText(item.serviceName);

                btnCancelCita.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onCancelClicked(item.idCita);
                    }
                });

                llCitasContainer.addView(itemView);
            }
        } else {
            tvCitaReservadaTitle.setText("No tienes citas reservadas");
        }
    }

    @Override
    public void showCancellationSuccess() {
        Toast.makeText(this, "Cita cancelada con éxito", Toast.LENGTH_LONG).show();
    }
}
