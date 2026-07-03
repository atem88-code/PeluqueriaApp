package eite.ulpgc.eite.da.peluqueriaapp.login;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;

public class loginPresenter implements loginContract.Presenter {

    private WeakReference<loginContract.View> view;
    private loginState state;
    private loginContract.Model model;

    public loginPresenter(loginState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<loginContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(loginContract.Model model) {
        this.model = model;
    }

    @Override
    public void onResume() {
        if (view.get() != null) {
            view.get().displayData(state);
        }
    }

    @Override
    public void onLoginClicked(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            state.errorMessage = "Campo vacío.";
            if (view.get() != null) {
                view.get().showLoginError(state.errorMessage);
            }
            return;
        }

        UserEntity user = model.login(email.trim(), password.trim());
        if (user != null) {
            AppMediator.getInstance().setLoggedUser(user);
            if (view.get() != null) {
                AppMediator.getInstance().saveSession((android.content.Context) view.get(), user);
                view.get().navigateToHome();
            }
        } else {
            state.errorMessage = "¡Error! Campo vacío o usuario no existente.";
            if (view.get() != null) {
                view.get().showLoginError(state.errorMessage);
            }
        }
    }

    @Override
    public void onGuestClicked() {
        AppMediator.getInstance().setLoggedUser(null);
        if (view.get() != null) {
            AppMediator.getInstance().saveSession((android.content.Context) view.get(), null);
            view.get().navigateToHome();
        }
    }

    @Override
    public void onRegisterClicked() {
        if (view.get() != null) {
            view.get().navigateToRegisterUser();
        }
    }
}
