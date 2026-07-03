package eite.ulpgc.eite.da.peluqueriaapp.login;

import android.content.Context;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;

public class loginModel implements loginContract.Model {

    private Context context;

    public loginModel(Context context) {
        this.context = context;
    }

    @Override
    public UserEntity login(String email, String password) {
        return AppDataBase.getInstance(context).userDao().login(email, password);
    }
}
