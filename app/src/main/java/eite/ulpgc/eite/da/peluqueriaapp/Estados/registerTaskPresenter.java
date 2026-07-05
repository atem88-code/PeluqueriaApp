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
        
        java.util.List<registerTaskViewModel.AppointmentItem> items = new java.util.ArrayList<>();
        
        if (user != null) {
            java.util.List<CitaEntity> list = model.getCitas(user.email);
            state.citas = list;
            
            if (list != null && !list.isEmpty()) {
                for (CitaEntity cita : list) {
                    registerTaskViewModel.AppointmentItem item = new registerTaskViewModel.AppointmentItem();
                    item.idCita = cita.id_cita;
                    item.dateTime = cita.fecha + " a las " + cita.hora;
                    
                    ServicioEntity s = model.getServiceById(cita.id_servicio);
                    if (s != null) {
                        item.serviceName = s.nombre_servicio;
                    } else {
                        item.serviceName = "Ninguno";
                    }
                    items.add(item);
                }
            }
        } else {
            state.citas = null;
        }
        
        state.appointments = items;

        if (view.get() != null) {
            view.get().displayAppointment(state);
        }
    }

    @Override
    public void onCancelClicked(int idCita) {
        if (state.citas != null) {
            for (CitaEntity cita : state.citas) {
                if (cita.id_cita == idCita) {
                    model.deleteCita(cita);
                    break;
                }
            }
            onResume();
            if (view.get() != null) {
                view.get().showCancellationSuccess();
            }
        }
    }
}
