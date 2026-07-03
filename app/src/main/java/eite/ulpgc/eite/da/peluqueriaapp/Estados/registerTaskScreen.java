package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class registerTaskScreen {

    public static void configure(registerTaskContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        registerTaskState state = mediator.getRegisterTaskState();

        if (state == null) {
            state = new registerTaskState();
        }

        registerTaskContract.Presenter presenter = new registerTaskPresenter(state);
        registerTaskContract.Model model = new registerTaskModel(context.get());

        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
