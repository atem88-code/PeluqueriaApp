package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "servicios")
public class ServicioEntity {

    @PrimaryKey(autoGenerate = true)
    public int id_servicio;

    public String nombre_servicio;
    public double precio;
    public int duracion_min;
}