package eite.ulpgc.eite.da.peluqueriaapp.ServicioListado;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class servicioListadoScreen {

    public static void configure(servicioListadoContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        servicioListadoState state = mediator.getServicioListadoState();

        if (state == null) {
            state = new servicioListadoState();
        }

        servicioListadoContract.Presenter presenter = new servicioListadoPresenter(state);
        servicioListadoContract.Model model = new servicioListadoModel(context.get());

        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
