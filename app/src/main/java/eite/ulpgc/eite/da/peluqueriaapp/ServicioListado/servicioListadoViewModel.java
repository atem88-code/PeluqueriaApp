package eite.ulpgc.eite.da.peluqueriaapp.ServicioListado;

import java.util.ArrayList;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.database.ServicioEntity;

public class servicioListadoViewModel {
    public List<ServicioEntity> services = new ArrayList<>();
    public boolean showOnlyFavorites = false;
}
