package eite.ulpgc.eite.da.peluqueriaapp.home;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class homeScreen {

    public static void configure(homeContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        homeState state = mediator.getHomeState();

        if (state == null) {
            state = new homeState();
        }

        homeContract.Presenter presenter = new homePresenter(state);
        homeContract.Model model = new homeModel();

        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}