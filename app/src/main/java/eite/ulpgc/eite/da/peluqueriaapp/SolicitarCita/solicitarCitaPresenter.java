package eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita;

import java.lang.ref.WeakReference;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;
import eite.ulpgc.eite.da.peluqueriaapp.Estados.statusState;

public class solicitarCitaPresenter implements solicitarCitaContract.Presenter {

    private WeakReference<solicitarCitaContract.View> view;
    private solicitarCitaState state;
    private solicitarCitaContract.Model model;

    public solicitarCitaPresenter(solicitarCitaState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<solicitarCitaContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(solicitarCitaContract.Model model) {
        this.model = model;
    }

    @Override
    public void onResume() {
        List<ServicioEntity> services = model.getServices();
        state.services = services;

        if (view.get() != null) {
            view.get().displayData(state);
        }
    }

    @Override
    public void onSubmitClicked(String date, String time, int selectedServiceIndex) {
        if (date.isEmpty() || time.isEmpty()) {
            state.errorMessage = "Por favor rellene todos los campos.";
            if (view.get() != null) {
                view.get().showAppointmentError(state.errorMessage);
            }
            return;
        }

        if (state.services == null || state.services.isEmpty()) {
            state.errorMessage = "No hay servicios disponibles.";
            if (view.get() != null) {
                view.get().showAppointmentError(state.errorMessage);
            }
            return;
        }

        if (selectedServiceIndex < 0 || selectedServiceIndex >= state.services.size()) {
            state.errorMessage = "Servicio no válido.";
            if (view.get() != null) {
                view.get().showAppointmentError(state.errorMessage);
            }
            return;
        }

        AppMediator mediator = AppMediator.getInstance();
        UserEntity user = mediator.getLoggedUser();
        if (user == null) {
            state.errorMessage = "Debe iniciar sesión para reservar una cita.";
            if (view.get() != null) {
                view.get().showAppointmentError(state.errorMessage);
            }
            return;
        }

        if (model.getAppointmentsCount(user.email) >= 5) {
            state.errorMessage = "Ya tienes el máximo de 5 citas reservadas.";
            if (view.get() != null) {
                view.get().showAppointmentError(state.errorMessage);
            }
            return;
        }

        ServicioEntity selectedService = state.services.get(selectedServiceIndex);
        boolean success = model.createAppointment(user.email, date, time, selectedService.id_servicio);
        if (success) {
            statusState sState = new statusState();
            sState.title = "¡Cita solicitada!";
            sState.message = "";
            sState.showDetail = true;
            sState.date = date;
            sState.time = time;
            sState.serviceName = selectedService.nombre_servicio;
            mediator.setStatusState(sState);

            state.errorMessage = "";
            if (view.get() != null) {
                view.get().navigateToStatus();
            }
        } else {
            state.errorMessage = "Error al crear la cita.";
            if (view.get() != null) {
                view.get().showAppointmentError(state.errorMessage);
            }
        }
    }
}
