package eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class solicitarCitaScreen {

    public static void configure(solicitarCitaContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        solicitarCitaState state = mediator.getSolicitarCitaState();

        if (state == null) {
            state = new solicitarCitaState();
        }

        solicitarCitaContract.Presenter presenter = new solicitarCitaPresenter(state);
        solicitarCitaContract.Model model = new solicitarCitaModel(context.get());

        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
