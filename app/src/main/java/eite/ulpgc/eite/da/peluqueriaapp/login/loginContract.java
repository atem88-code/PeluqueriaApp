package eite.ulpgc.eite.da.peluqueriaapp.login;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;

public interface loginContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayData(loginViewModel viewModel);
        void navigateToHome();
        void navigateToRegisterUser();
        void showLoginError(String message);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
        void onLoginClicked(String email, String password);
        void onGuestClicked();
        void onRegisterClicked();
    }

    interface Model {
        UserEntity login(String email, String password);
    }
}
