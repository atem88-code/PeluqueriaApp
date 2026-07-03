package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.database.CitaEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public interface registerTaskContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayAppointment(registerTaskViewModel viewModel);
        void showCancellationSuccess();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
        void onCancelClicked();
    }

    interface Model {
        CitaEntity getLatestCita(String email);
        ServicioEntity getServiceById(int id);
        void deleteCita(CitaEntity cita);
    }
}
