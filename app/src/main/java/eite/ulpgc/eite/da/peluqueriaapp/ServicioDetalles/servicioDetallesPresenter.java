package eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class servicioDetallesPresenter implements servicioDetallesContract.Presenter {

    private WeakReference<servicioDetallesContract.View> view;
    private servicioDetallesState state;
    private servicioDetallesContract.Model model;

    public servicioDetallesPresenter(servicioDetallesState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<servicioDetallesContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(servicioDetallesContract.Model model) {
        this.model = model;
    }

    @Override
    public void onResume() {
        int id = AppMediator.getInstance().currentServiceId;
        ServicioEntity service = model.getService(id);
        state.service = service;

        if (service != null) {
            state.serviceId = "SVC-" + String.format("%03d", service.id_servicio);
            state.name = service.nombre_servicio;
            state.duration = service.duracion_min + " min";
            state.price = "€" + String.format("%.2f", service.precio);
            state.schedule = calculateSchedule(service.duracion_min);
        }

        if (view.get() != null) {
            view.get().displayService(state);
        }
    }

    private String calculateSchedule(int durationMin) {
        int startHour = 9;
        int startMin = 0;
        int endMin = startMin + durationMin;
        int endHour = startHour + (endMin / 60);
        endMin = endMin % 60;
        return String.format("%02d:%02d - %02d:%02d", startHour, startMin, endHour, endMin);
    }
}
