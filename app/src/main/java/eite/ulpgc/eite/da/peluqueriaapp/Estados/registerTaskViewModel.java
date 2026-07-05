package eite.ulpgc.eite.da.peluqueriaapp.Estados;

import java.util.List;

public class registerTaskViewModel {
    
    public static class AppointmentItem {
        public int idCita;
        public String dateTime;
        public String serviceName;
    }

    public List<AppointmentItem> appointments;
}
