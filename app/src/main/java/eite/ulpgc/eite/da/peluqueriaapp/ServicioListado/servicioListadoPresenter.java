package eite.ulpgc.eite.da.peluqueriaapp.ServicioListado;

import java.lang.ref.WeakReference;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class servicioListadoPresenter implements servicioListadoContract.Presenter {

    private WeakReference<servicioListadoContract.View> view;
    private servicioListadoState state;
    private servicioListadoContract.Model model;

    public servicioListadoPresenter(servicioListadoState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<servicioListadoContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(servicioListadoContract.Model model) {
        this.model = model;
    }

    @Override
    public void onResume() {
        AppMediator mediator = AppMediator.getInstance();
        UserEntity user = mediator.getLoggedUser();
        String userEmail = (user != null) ? user.email : null;

        List<ServicioEntity> services;
        if (mediator.isFavoriteFilterActive) {
            if (userEmail != null) {
                services = model.getFavoriteServices(userEmail);
            } else {
                services = new java.util.ArrayList<>();
            }
        } else {
            services = model.getServices();
        }

        if (view.get() != null) {
            view.get().displayServices(services, userEmail);
        }
    }

    @Override
    public void onServiceSelected(ServicioEntity service) {
        AppMediator.getInstance().currentServiceId = service.id_servicio;
        if (view.get() != null) {
            view.get().navigateToServiceDetail();
        }
    }

    @Override
    public void onToggleFavorite(ServicioEntity service) {
        AppMediator mediator = AppMediator.getInstance();
        UserEntity user = mediator.getLoggedUser();
        if (user == null) {
            return;
        }

        boolean newFav = !model.isFavorite(user.email, service.id_servicio);
        model.setFavorite(user.email, service.id_servicio, newFav);
        onResume();
    }
}
