package eite.ulpgc.eite.da.peluqueriaapp.ServicioListado;

import java.lang.ref.WeakReference;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public interface servicioListadoContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayServices(List<ServicioEntity> services, String userEmail);
        void navigateToServiceDetail();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
        void onServiceSelected(ServicioEntity service);
        void onToggleFavorite(ServicioEntity service);
    }

    interface Model {
        List<ServicioEntity> getServices();
        List<ServicioEntity> getFavoriteServices(String email);
        boolean isFavorite(String email, int serviceId);
        void setFavorite(String email, int serviceId, boolean isFavorite);
    }
}
