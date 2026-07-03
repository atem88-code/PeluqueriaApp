package eite.ulpgc.eite.da.peluqueriaapp.home;

import java.lang.ref.WeakReference;

public interface homeContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayData(homeViewModel viewModel);
        void navigateToRegisterProject();
        void navigateToRegisterTask();
        void navigateToProjectList();
        void showGuestMessage();
        void showFavoritesMessage();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onResume();
        void onRegisterProjectBtnClicked();
        void onRegisterTaskBtnClicked();
        void onProjectListBtnClicked();
        void onFavoritesBtnClicked();
    }

    interface Model {
        boolean isGuestUser();
    }
}
