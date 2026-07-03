package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import java.lang.ref.WeakReference;

public interface statusContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayData(statusViewModel viewModel);
        void navigateToHomeOrLogin();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void onResume();
        void onActionClicked();
    }
}
