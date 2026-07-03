package eite.ulpgc.eite.da.peluqueriaapp.ServicioListado;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.R;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class servicioListadoActivity extends AppCompatActivity implements servicioListadoContract.View {

    private servicioListadoContract.Presenter presenter;
    private LinearLayout llProjectContainer;
    private TextView tvListDescription;
    private TextView subHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_servicios);

        llProjectContainer = findViewById(R.id.llProjectContainer);
        tvListDescription = findViewById(R.id.tvListDescription);
        subHeader = findViewById(R.id.subHeader);

        servicioListadoScreen.configure(this);

        ImageView btnHeaderBack = findViewById(R.id.btnHeaderBack);
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
    public void injectPresenter(servicioListadoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayServices(List<ServicioEntity> services, String userEmail) {
        llProjectContainer.removeAllViews();

        boolean isFavMode = AppMediator.getInstance().isFavoriteFilterActive;

        if (isFavMode) {
            subHeader.setText("Mis Favoritos");
            tvListDescription.setVisibility(View.GONE);
        } else {
            subHeader.setText("Lista de Servicios");
            if (userEmail == null || userEmail.isEmpty()) {
                tvListDescription.setVisibility(View.GONE);
            } else {
                tvListDescription.setVisibility(View.VISIBLE);
            }
        }

        if (services == null) return;

        AppDataBase db = AppDataBase.getInstance(this);

        for (final ServicioEntity service : services) {
            View itemView = getLayoutInflater().inflate(R.layout.servicio, llProjectContainer, false);

            TextView tvName = itemView.findViewById(R.id.tvProjectNameItem1);
            TextView tvSubtitle = itemView.findViewById(R.id.tvProjectSubtitleItem);
            TextView tvServiceIdText = itemView.findViewById(R.id.tvServiceIdText);
            ImageView btnFavoriteLeft = itemView.findViewById(R.id.btnFavoriteLeft);
            ImageView btnFavorite = itemView.findViewById(R.id.btnFavorite);

            tvName.setText(service.nombre_servicio);

            btnFavoriteLeft.setVisibility(View.GONE);
            tvSubtitle.setText(service.duracion_min + " min · €" + String.format("%.2f", service.precio));

            if (isFavMode) {
                tvServiceIdText.setVisibility(View.VISIBLE);
                tvServiceIdText.setText("ID: SVC-" + String.format("%03d", service.id_servicio));

                btnFavorite.setVisibility(View.VISIBLE);
                btnFavorite.setImageResource(R.drawable.ic_heart_solid);

                btnFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onToggleFavorite(service);
                    }
                });
            } else {
                tvServiceIdText.setVisibility(View.GONE);

                if (userEmail == null || userEmail.isEmpty()) {
                    btnFavorite.setVisibility(View.GONE);
                } else {
                    btnFavorite.setVisibility(View.VISIBLE);
                    boolean isFav = db.favoriteDao().isFavorite(userEmail, service.id_servicio);
                    if (isFav) {
                        btnFavorite.setImageResource(R.drawable.ic_heart_solid);
                    } else {
                        btnFavorite.setImageResource(R.drawable.ic_heart_outline);
                    }

                    btnFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.onToggleFavorite(service);
                        }
                    });
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onServiceSelected(service);
                }
            });

            llProjectContainer.addView(itemView);
        }
    }

    @Override
    public void navigateToServiceDetail() {
        AppMediator.getInstance().goToProjectDetail(this);
    }

}
