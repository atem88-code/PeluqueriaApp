package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import java.lang.ref.WeakReference;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;

public class statusPresenter implements statusContract.Presenter {

    private WeakReference<statusContract.View> view;
    private statusState state;

    public statusPresenter(statusState state) {
        this.state = state;
    }

    @Override
    public void injectView(WeakReference<statusContract.View> view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        statusState saved = AppMediator.getInstance().getStatusState();
        if (saved != null) {
            state.title = saved.title;
            state.message = saved.message;
            state.showDetail = saved.showDetail;
            state.date = saved.date;
            state.time = saved.time;
            state.serviceName = saved.serviceName;
        }

        if (view.get() != null) {
            view.get().displayData(state);
        }
    }

    @Override
    public void onActionClicked() {
        if (view.get() != null) {
            view.get().navigateToHomeOrLogin();
        }
    }
}
