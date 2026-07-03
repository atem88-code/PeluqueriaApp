package eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class registerUserScreen {

    public static void configure(registerUserContract.View view) {
        WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        registerUserState state = mediator.getRegisterUserState();

        if (state == null) {
            state = new registerUserState();
        }

        registerUserContract.Presenter presenter = new registerUserPresenter(state);
        registerUserContract.Model model = new registerUserModel(context.get());

        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);
    }
}
