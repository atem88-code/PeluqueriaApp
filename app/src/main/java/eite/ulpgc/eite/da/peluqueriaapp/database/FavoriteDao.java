package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavorite(FavoriteEntity favorite);

    @Query("DELETE FROM favorites WHERE userEmail = :email AND serviceId = :serviceId")
    void removeFavorite(String email, int serviceId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userEmail = :email AND serviceId = :serviceId)")
    boolean isFavorite(String email, int serviceId);

    @Query("SELECT s.* FROM servicios s INNER JOIN favorites f ON s.id_servicio = f.serviceId WHERE f.userEmail = :email")
    List<ServicioEntity> getFavoriteServicesForUser(String email);

    @Query("SELECT * FROM favorites WHERE userEmail = :email")
    List<FavoriteEntity> getFavoritesForUser(String email);
}