package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.CitaEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class registerTaskPresenter implements registerTaskContract.Presenter {

    private WeakReference<registerTaskContract.View> view;
    private registerTaskState state;
    private registerTaskContract.Model model;

    public registerTaskPresenter(registerTaskState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<registerTaskContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(registerTaskContract.Model model) {
        this.model = model;
    }

    @Override
    public void onResume() {
        AppMediator mediator = AppMediator.getInstance();
        UserEntity user = mediator.getLoggedUser();
        
        if (user != null) {
            CitaEntity latest = model.getLatestCita(user.email);
            state.cita = latest;
            if (latest != null) {
                state.dateTime = latest.fecha + " a las " + latest.hora;
                ServicioEntity s = model.getServiceById(latest.id_servicio);
                state.service = s;
                if (s != null) {
                    state.serviceName = s.nombre_servicio;
                } else {
                    state.serviceName = "Ninguno";
                }
                state.hasAppointment = true;
            } else {
                state.dateTime = "No tienes citas reservadas";
                state.serviceName = "-";
                state.hasAppointment = false;
            }
        } else {
            state.dateTime = "No tienes citas reservadas";
            state.serviceName = "-";
            state.hasAppointment = false;
        }

        if (view.get() != null) {
            view.get().displayAppointment(state);
        }
    }

    @Override
    public void onCancelClicked() {
        if (state.cita != null) {
            model.deleteCita(state.cita);
            state.cita = null;
            state.service = null;
            state.dateTime = "No tienes citas reservadas";
            state.serviceName = "-";
            state.hasAppointment = false;

            if (view.get() != null) {
                view.get().showCancellationSuccess();
                view.get().displayAppointment(state);
            }
        }
    }
}
