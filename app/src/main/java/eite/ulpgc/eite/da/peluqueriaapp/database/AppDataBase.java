package eite.ulpgc.eite.da.peluqueriaapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import eite.ulpgc.eite.da.peluqueriaapp.R;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import com.google.gson.Gson;

@Database(entities = {UserEntity.class, EmpleadoEntity.class, ServicioEntity.class, CitaEntity.class, FavoriteEntity.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract EmpleadoDao empleadoDao();
    public abstract ServicioDao servicioDao();
    public abstract CitaDao citaDao();
    public abstract FavoriteDao favoriteDao();


    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "peluqueria_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                    INSTANCE.seedIfEmpty(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private void seedIfEmpty(Context context) {
        if (servicioDao().getAll().isEmpty()) {
            try {
                InputStream is = context.getResources().openRawResource(R.raw.datos_iniciales);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                String json = sb.toString();

                Gson gson = new Gson();
                InitialData data = gson.fromJson(json, InitialData.class);

                if (data.clientes != null) {
                    for (UserEntity c : data.clientes) {
                        userDao().insertUser(c);
                    }
                }
                if (data.empleados != null) {
                    for (EmpleadoEntity e : data.empleados) {
                        empleadoDao().insert(e);
                    }
                }
                if (data.servicios != null) {
                    for (ServicioEntity s : data.servicios) {
                        servicioDao().insert(s);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class InitialData {
        List<UserEntity> clientes;
        List<EmpleadoEntity> empleados;
        List<ServicioEntity> servicios;
    }
}
