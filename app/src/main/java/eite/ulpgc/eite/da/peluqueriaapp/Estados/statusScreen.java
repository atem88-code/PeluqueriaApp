package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class statusScreen {

    public static void configure(statusContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        statusState state = mediator.getStatusState();

        if (state == null) {
            state = new statusState();
        }

        statusContract.Presenter presenter = new statusPresenter(state);

        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
