package eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import eite.ulpgc.eite.da.peluqueriaapp.R;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class solicitarCitaActivity extends AppCompatActivity implements solicitarCitaContract.View {

    private solicitarCitaContract.Presenter presenter;

    private EditText etProjectDate;
    private EditText etProjectTime;
    private Spinner spinnerServicio;
    private Button btnSubmitProject;
    private TextView tvCitaError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevacita);

        etProjectDate = findViewById(R.id.etProjectDate);
        etProjectTime = findViewById(R.id.etProjectTime);
        spinnerServicio = findViewById(R.id.spinnerServicio);
        btnSubmitProject = findViewById(R.id.btnSubmitProject);
        tvCitaError = findViewById(R.id.tvCitaError);

        solicitarCitaScreen.configure(this);

        android.widget.ImageView btnHeaderBack = findViewById(R.id.btnHeaderBack);
        if (btnHeaderBack != null) {
            btnHeaderBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        btnSubmitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSubmitClicked(
                        etProjectDate.getText().toString(),
                        etProjectTime.getText().toString(),
                        spinnerServicio.getSelectedItemPosition()
                );
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void injectPresenter(solicitarCitaContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayData(solicitarCitaViewModel viewModel) {
        if (viewModel != null) {
            etProjectDate.setText(viewModel.date);
            etProjectTime.setText(viewModel.time);

            if (viewModel.services != null) {
                ArrayList<String> serviceNames = new ArrayList<>();
                for (ServicioEntity s : viewModel.services) {
                    serviceNames.add(s.nombre_servicio + " (" + s.duracion_min + " min - €" + String.format("%.2f", s.precio) + ")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, serviceNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerServicio.setAdapter(adapter);
            }

            if (viewModel.errorMessage != null && !viewModel.errorMessage.isEmpty()) {
                tvCitaError.setText(viewModel.errorMessage);
                tvCitaError.setVisibility(View.VISIBLE);
            } else {
                tvCitaError.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void navigateToStatus() {
        AppMediator.getInstance().goToStatus(this);
        finish();
    }

    @Override
    public void showAppointmentError(String message) {
        tvCitaError.setText(message);
        tvCitaError.setVisibility(View.VISIBLE);
    }
}
