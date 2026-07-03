package eite.ulpgc.eite.da.peluqueriaapp.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "favorites", primaryKeys = {"userEmail", "serviceId"})
public class FavoriteEntity {

    @NonNull
    public String userEmail = "";

    public int serviceId;
}