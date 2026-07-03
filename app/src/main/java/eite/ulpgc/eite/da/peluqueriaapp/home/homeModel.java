package eite.ulpgc.eite.da.peluqueriaapp.home;

import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;


public class homeModel implements homeContract.Model {

    @Override
    public boolean isGuestUser() {
        return AppMediator.getInstance().getLoggedUser() == null;
    }
}