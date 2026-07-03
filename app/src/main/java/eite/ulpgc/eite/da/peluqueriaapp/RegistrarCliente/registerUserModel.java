package eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente;

import android.content.Context;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;

public class registerUserModel implements registerUserContract.Model {

    private Context context;

    public registerUserModel(Context context) {
        this.context = context;
    }

    @Override
    public boolean registerUser(String email, String password) {
        AppDataBase db = AppDataBase.getInstance(context);
        UserEntity existing = db.userDao().getUserByEmail(email);
        if (existing != null) {
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.email = email;
        newUser.password = password;
        newUser.nombre = "";
        newUser.telefono = "";
        db.userDao().insertUser(newUser);
        return true;
    }
}
