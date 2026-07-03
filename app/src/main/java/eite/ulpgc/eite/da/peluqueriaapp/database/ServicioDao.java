package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ServicioDao {

    @Insert
    void insert(ServicioEntity servicio);

    @Query("SELECT * FROM servicios")
    List<ServicioEntity> getAll();

    @Query("SELECT * FROM servicios WHERE id_servicio = :id")
    ServicioEntity getById(int id);
}
