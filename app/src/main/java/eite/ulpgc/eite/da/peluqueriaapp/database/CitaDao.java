package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CitaDao {
    @Insert
    void insert(CitaEntity cita);

    @Query("SELECT citas.* FROM citas INNER JOIN clientes ON citas.id_cliente = clientes.id_cliente WHERE clientes.email = :email")
    List<CitaEntity> getByEmail(String email);

    @androidx.room.Delete
    void delete(CitaEntity cita);
}
