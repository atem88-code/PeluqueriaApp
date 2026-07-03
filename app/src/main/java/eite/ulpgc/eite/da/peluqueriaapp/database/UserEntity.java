package eite.ulpgc.eite.da.peluqueriaapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id_cliente;

    public String nombre;
    public String telefono;
    public String email;
    public String password; // Added for app logic
}