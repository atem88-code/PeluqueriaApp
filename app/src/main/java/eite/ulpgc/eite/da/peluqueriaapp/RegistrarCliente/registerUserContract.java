package eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente;

import java.lang.ref.WeakReference;

public interface registerUserContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayData(registerUserViewModel viewModel);
        void navigateToStatus();
        void showRegisterError(String message);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
        void onRegisterClicked(String email, String password);
    }

    interface Model {
        boolean registerUser(String email, String password);
    }
}
