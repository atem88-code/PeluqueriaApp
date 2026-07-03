package eite.ulpgc.eite.da.peluqueriaapp.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Query("SELECT * FROM clientes WHERE email = :email AND password = :password LIMIT 1")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM clientes WHERE email = :email LIMIT 1")
    UserEntity getUserByEmail(String email);
}