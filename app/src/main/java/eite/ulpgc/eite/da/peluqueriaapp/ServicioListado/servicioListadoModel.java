package eite.ulpgc.eite.da.peluqueriaapp.ServicioListado;

import android.content.Context;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.FavoriteEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class servicioListadoModel implements servicioListadoContract.Model {

    private Context context;

    public servicioListadoModel(Context context) {
        this.context = context;
    }

    @Override
    public List<ServicioEntity> getServices() {
        return AppDataBase.getInstance(context).servicioDao().getAll();
    }

    @Override
    public List<ServicioEntity> getFavoriteServices(String email) {
        return AppDataBase.getInstance(context).favoriteDao().getFavoriteServicesForUser(email);
    }

    @Override
    public boolean isFavorite(String email, int serviceId) {
        return AppDataBase.getInstance(context).favoriteDao().isFavorite(email, serviceId);
    }

    @Override
    public void setFavorite(String email, int serviceId, boolean isFavorite) {
        if (isFavorite) {
            FavoriteEntity f = new FavoriteEntity();
            f.userEmail = email;
            f.serviceId = serviceId;
            AppDataBase.getInstance(context).favoriteDao().insertFavorite(f);
        } else {
            AppDataBase.getInstance(context).favoriteDao().removeFavorite(email, serviceId);
        }
    }
}
