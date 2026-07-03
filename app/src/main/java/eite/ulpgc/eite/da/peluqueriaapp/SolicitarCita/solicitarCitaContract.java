package eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita;

import java.lang.ref.WeakReference;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public interface solicitarCitaContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayData(solicitarCitaViewModel viewModel);
        void navigateToStatus();
        void showAppointmentError(String message);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
        void onSubmitClicked(String date, String time, int selectedServiceIndex);
    }

    interface Model {
        List<ServicioEntity> getServices();
        boolean createAppointment(String email, String date, String time, int serviceId);
    }
}
