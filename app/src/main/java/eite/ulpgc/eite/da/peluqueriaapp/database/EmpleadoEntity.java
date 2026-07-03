package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "empleados")
public class EmpleadoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id_empleado;

    public String nombre;
    public String especialidad;
    public String telefono;
}
