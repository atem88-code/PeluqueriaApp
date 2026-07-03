package eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita;

import android.content.Context;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.CitaEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class solicitarCitaModel implements solicitarCitaContract.Model {

    private Context context;

    public solicitarCitaModel(Context context) {
        this.context = context;
    }

    @Override
    public List<ServicioEntity> getServices() {
        return AppDataBase.getInstance(context).servicioDao().getAll();
    }

    @Override
    public boolean createAppointment(String email, String date, String time, int serviceId) {
        UserEntity client = AppDataBase.getInstance(context).userDao().getUserByEmail(email);
        if (client == null) {
            return false;
        }

        CitaEntity appointment = new CitaEntity();
        appointment.id_cliente = client.id_cliente;
        appointment.fecha = date;
        appointment.hora = time;
        appointment.id_servicio = serviceId;
        appointment.id_empleado = 1; // Default first employee

        AppDataBase.getInstance(context).citaDao().insert(appointment);
        return true;
    }
}
