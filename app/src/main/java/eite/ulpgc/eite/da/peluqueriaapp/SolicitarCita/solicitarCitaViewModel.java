package eite.ulpgc.eite.da.peluqueriaapp.SolicitarCita;

import java.util.ArrayList;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class solicitarCitaViewModel {
    public String date = "";
    public String time = "";
    public List<ServicioEntity> services = new ArrayList<>();
    public String errorMessage = "";
}
