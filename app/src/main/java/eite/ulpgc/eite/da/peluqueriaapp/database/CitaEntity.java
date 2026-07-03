package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "citas")
public class CitaEntity {

    @PrimaryKey(autoGenerate = true)
    public int id_cita;

    public int id_cliente;
    public int id_empleado;
    public int id_servicio;
    public String fecha;
    public String hora;
}