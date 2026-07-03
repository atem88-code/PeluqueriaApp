package eite.ulpgc.eite.da.peluqueriaapp.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import eite.ulpgc.eite.da.peluqueriaapp.database.*;
import android.os.Bundle;
import eite.ulpgc.eite.da.peluqueriaapp.home.homeState;
import eite.ulpgc.eite.da.peluqueriaapp.home.homeActivity;
import eite.ulpgc.eite.da.peluqueriaapp.login.loginState;
import eite.ulpgc.eite.da.peluqueriaapp.login.loginActivity;
import eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente.registerUserState;
import eite.ulpgc.eite.da.peluqueriaapp.RegistrarCliente.registerUserActivity;
import eite.ulpgc.eite.da.peluqueriaapp.ServicioListado.servicioListadoState;
import eite.ulpgc.eite.da.peluqueriaapp.ServicioListado.servicioListadoActivity;
import eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles.servicioDetallesState;
import eite.ulpgc.eite.da.peluqueriaapp.ServicioDetalles.servicioDetallesActivity;
import eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita.solicitarCitaState;
import eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita.solicitarCitaActivity;
import eite.ulpgc.eite.da.peluqueriaapp.Estados.registerTaskState;
import eite.ulpgc.eite.da.peluqueriaapp.Estados.registerTaskActivity;
import eite.ulpgc.eite.da.peluqueriaapp.Estados.statusState;
import eite.ulpgc.eite.da.peluqueriaapp.Estados.statusActivity;

import androidx.appcompat.app.AppCompatActivity;

import eite.ulpgc.eite.da.peluqueriaapp.R;

public class AppMediator {

    public static String TAG = "AppMediator";
    private static AppMediator INSTANCE;

    public boolean isFavoriteFilterActive = false;
    public int currentServiceId;
    public String lastSelectedDate;
    public String lastSelectedTime;
    public int lastSelectedServiceId;

    private boolean sessionChecked = false;

    private homeState savedHomeState;
    private loginState savedLoginState;
    private servicioListadoState savedServicioListadoState;
    private servicioDetallesState savedServicioDetallesState;
    private solicitarCitaState savedSolicitarCitaState;
    private registerTaskState savedRegisterTaskState;
    private registerUserState savedRegisterUserState;
    private statusState savedStatusState;

    private UserEntity loggedUser;

    public UserEntity getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserEntity loggedUser) {
        this.loggedUser = loggedUser;
        if (loggedUser == null) {
            sessionChecked = true;
        }
    }

    private AppMediator() { }

    public static AppMediator getInstance() {
        if (INSTANCE == null) { INSTANCE = new AppMediator(); }
        return INSTANCE;
    }

    public void saveSession(Context context, UserEntity user) {
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (user != null) {
            editor.putString("user_email", user.email);
            editor.putString("user_pass", user.password);
            sessionChecked = true;
        } else {
            editor.clear();
        }
        editor.commit();
    }

    public void loadSession(Context context) {
        if (sessionChecked && loggedUser == null) return;
        if (loggedUser != null) return;

        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("user_email", null);
        if (email != null) {
            UserEntity user = new UserEntity();
            user.email = email;
            user.password = prefs.getString("user_pass", "");
            this.loggedUser = user;
        }
        sessionChecked = true;
    }

    public homeState getHomeState() { return savedHomeState; }
    public void setHomeState(homeState state) { savedHomeState = state; }

    public loginState getLoginState() { return savedLoginState; }
    public void setLoginState(loginState state) { savedLoginState = state; }

    public servicioListadoState getServicioListadoState() { return savedServicioListadoState; }
    public void setServicioListadoState(servicioListadoState state) { savedServicioListadoState = state; }

    public servicioDetallesState getServicioDetallesState() { return savedServicioDetallesState; }
    public void setServicioDetallesState(servicioDetallesState state) { savedServicioDetallesState = state; }

    public solicitarCitaState getSolicitarCitaState() { return savedSolicitarCitaState; }
    public void setSolicitarCitaState(solicitarCitaState state) { savedSolicitarCitaState = state; }

    public registerTaskState getRegisterTaskState() { return savedRegisterTaskState; }
    public void setRegisterTaskState(registerTaskState state) { savedRegisterTaskState = state; }

    public registerUserState getRegisterUserState() { return savedRegisterUserState; }
    public void setRegisterUserState(registerUserState state) { savedRegisterUserState = state; }

    public statusState getStatusState() { return savedStatusState; }
    public void setStatusState(statusState state) { savedStatusState = state; }

    public void goToHome(Context context) {
        context.startActivity(new Intent(context, homeActivity.class));
    }

    public void goToLogin(Context context) {
        Intent intent = new Intent(context, loginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void goToRegisterUser(Context context) {
        context.startActivity(new Intent(context, registerUserActivity.class));
    }

    public void goToProjectList(Context context) {
        context.startActivity(new Intent(context, servicioListadoActivity.class));
    }

    public void goToProjectDetail(Context context) {
        context.startActivity(new Intent(context, servicioDetallesActivity.class));
    }

    public void goToRegisterProject(Context context) {
        context.startActivity(new Intent(context, solicitarCitaActivity.class));
    }

    public void goToRegisterTask(Context context) {
        context.startActivity(new Intent(context, registerTaskActivity.class));
    }

    public void goToStatus(Context context) {
        context.startActivity(new Intent(context, statusActivity.class));
    }
}