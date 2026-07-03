package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EmpleadoDao {
    @Insert
    void insert(EmpleadoEntity empleado);

    @Query("SELECT * FROM empleados")
    List<EmpleadoEntity> getAll();
}