package eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class servicioDetallesScreen {

    public static void configure(servicioDetallesContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        servicioDetallesState state = mediator.getServicioDetallesState();

        if (state == null) {
            state = new servicioDetallesState();
        }

        servicioDetallesContract.Presenter presenter = new servicioDetallesPresenter(state);
        servicioDetallesContract.Model model = new servicioDetallesModel(context.get());

        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
