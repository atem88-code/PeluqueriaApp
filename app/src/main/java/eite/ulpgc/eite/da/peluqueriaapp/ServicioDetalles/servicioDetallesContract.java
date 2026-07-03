package eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public interface servicioDetallesContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayService(servicioDetallesViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
    }

    interface Model {
        ServicioEntity getService(int id);
    }
}
