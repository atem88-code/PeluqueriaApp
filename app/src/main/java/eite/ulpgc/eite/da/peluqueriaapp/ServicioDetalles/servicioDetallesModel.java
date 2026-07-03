package eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles;

import android.content.Context;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class servicioDetallesModel implements servicioDetallesContract.Model {

    private Context context;

    public servicioDetallesModel(Context context) {
        this.context = context;
    }

    @Override
    public ServicioEntity getService(int id) {
        return AppDataBase.getInstance(context).servicioDao().getById(id);
    }
}
