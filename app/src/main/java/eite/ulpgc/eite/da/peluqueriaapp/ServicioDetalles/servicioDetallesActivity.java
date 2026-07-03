package eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import eite.ulpgc.eite.da.peluqueriaapp.R;

public class servicioDetallesActivity extends AppCompatActivity implements servicioDetallesContract.View {

    private servicioDetallesContract.Presenter presenter;

    private TextView tvDetailServiceName;
    private TextView tvDetailServiceId;
    private TextView tvDetailName;
    private TextView tvDetailDuration;
    private TextView tvDetailSchedule;
    private TextView tvDetailPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_servicio);

        tvDetailServiceName = findViewById(R.id.tvDetailServiceName);
        tvDetailServiceId = findViewById(R.id.tvDetailServiceId);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailDuration = findViewById(R.id.tvDetailDuration);
        tvDetailSchedule = findViewById(R.id.tvDetailSchedule);
        tvDetailPrice = findViewById(R.id.tvDetailPrice);

        servicioDetallesScreen.configure(this);

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
    public void injectPresenter(servicioDetallesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayService(servicioDetallesViewModel viewModel) {
        if (viewModel != null) {
            tvDetailServiceName.setText(viewModel.name);
            tvDetailServiceId.setText(viewModel.serviceId);
            tvDetailName.setText(viewModel.name);
            tvDetailDuration.setText(viewModel.duration);
            tvDetailSchedule.setText(viewModel.schedule);
            tvDetailPrice.setText(viewModel.price);
        }
    }
}
