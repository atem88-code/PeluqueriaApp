package eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.Estados.statusState;

public class registerUserPresenter implements registerUserContract.Presenter {

    private WeakReference<registerUserContract.View> view;
    private registerUserState state;
    private registerUserContract.Model model;

    public registerUserPresenter(registerUserState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<registerUserContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(registerUserContract.Model model) {
        this.model = model;
    }

    @Override
    public void onResume() {
        if (view.get() != null) {
            view.get().displayData(state);
        }
    }

    @Override
    public void onRegisterClicked(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            state.errorMessage = "¡Error! Campo vacío o usuario ya existente.";
            if (view.get() != null) {
                view.get().showRegisterError(state.errorMessage);
            }
            return;
        }

        boolean success = model.registerUser(email.trim(), password.trim());
        if (success) {
            statusState sState = new statusState();
            sState.title = "¡Usuario creado!";
            sState.message = "Tu cuenta ha sido registrada correctamente. Ya puedes iniciar sesión.";
            sState.showDetail = false;
            AppMediator.getInstance().setStatusState(sState);

            if (view.get() != null) {
                view.get().navigateToStatus();
            }
        } else {
            state.errorMessage = "¡Error! Campo vacío o usuario ya existente.";
            if (view.get() != null) {
                view.get().showRegisterError(state.errorMessage);
            }
        }
    }
}
