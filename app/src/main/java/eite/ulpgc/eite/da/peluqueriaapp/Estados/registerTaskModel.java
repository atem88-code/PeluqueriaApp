package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import android.content.Context;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.CitaEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class registerTaskModel implements registerTaskContract.Model {

    private Context context;

    public registerTaskModel(Context context) {
        this.context = context;
    }

    @Override
    public CitaEntity getLatestCita(String email) {
        List<CitaEntity> list = AppDataBase.getInstance(context).citaDao().getByEmail(email);
        if (list != null && !list.isEmpty()) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    @Override
    public ServicioEntity getServiceById(int id) {
        return AppDataBase.getInstance(context).servicioDao().getById(id);
    }

    @Override
    public void deleteCita(CitaEntity cita) {
        if (cita != null) {
            AppDataBase.getInstance(context).citaDao().delete(cita);
        }
    }
}
