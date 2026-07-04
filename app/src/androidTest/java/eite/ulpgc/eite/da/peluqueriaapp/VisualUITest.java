package eite.ulpgc.eite.da.peluqueriaapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.login.loginActivity;
import eite.ulpgc.eite.da.peluqueriaapp.database.AppDataBase;
import eite.ulpgc.eite.da.peluqueriaapp.database.CitaEntity;
import eite.ulpgc.eite.da.peluqueriaapp.database.UserEntity;
import eite.ulpgc.eite.da.peluqueriaapp.home.homeActivity;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class VisualUITest {

    @Before
    public void setUp() {
        // Reset mediator log-in state
        AppMediator.getInstance().setLoggedUser(null);
        
        // Clear Shared Preferences to remove any persisted session
        android.content.Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    @Test
    public void visualTest_01_testScreenRotation() throws InterruptedException {
        // Launch loginActivity scenario
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Rotate the screen to Landscape
            scenario.onActivity(activity -> {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            });

            // Wait for visual rotation to settle
            Thread.sleep(2000);

            // Rotate the screen back to Portrait
            scenario.onActivity(activity -> {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            });

            // Wait for rotation back to settle
            Thread.sleep(2000);
        }
    }

    @Test
    public void visualTest_02_closeinvited() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // 1. Click Guest button to enter guest mode
            onView(withId(R.id.btnLoginGuest)).perform(click());
            Thread.sleep(1000);

            // Verify they are in Guest Mode (layoutGuest is visible, or tvGuestLogout is visible)
            onView(withId(R.id.tvGuestLogout)).check(matches(isDisplayed()));

            // 2. Click "Cerrar modo invitado" to go back to Login screen
            onView(withId(R.id.tvGuestLogout)).perform(click());
            Thread.sleep(1000);

            // Verify they are back on Login Screen (btnLoginGuest is visible)
            onView(withId(R.id.btnLoginGuest)).check(matches(isDisplayed()));

            // 3. Re-enter guest mode to test the other navigation back option
            onView(withId(R.id.btnLoginGuest)).perform(click());
            Thread.sleep(1000);

            // 4. Click "Inicia sesión para gestionar tus citas y servicios"
            onView(withId(R.id.tvGuestGoToLogin)).perform(click());
            Thread.sleep(1000);

            // Verify they are back on Login Screen
            onView(withId(R.id.btnLoginGuest)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void visualTest_03_inviteoptions() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // 1. Enter Guest mode
            onView(withId(R.id.btnLoginGuest)).perform(click());
            Thread.sleep(1000);

            // 2. Click "Lista de Servicios" button
            onView(withId(R.id.btnGuestListaServicios)).perform(click());
            Thread.sleep(1000);

            // 3. Select "Tinte raiz" service
            onView(withText("Tinte raiz")).perform(scrollTo(), click());
            Thread.sleep(1000);

            // 4. Verify they are in service details screen (tvDetailServiceName is visible and displays "Tinte raiz")
            onView(withId(R.id.tvDetailServiceName)).check(matches(isDisplayed()));
            onView(withId(R.id.tvDetailServiceName)).check(matches(withText("Tinte raiz")));
        }
    }

    @Test
    public void visualTest_04_ErrorVacio() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Go to Register screen
            onView(withId(R.id.btnLoginRegister)).perform(click());
            Thread.sleep(1000);

            // Click continue button directly (with empty fields)
            onView(withId(R.id.btnRegisterContinue)).perform(click());
            Thread.sleep(1000);

            // Verify error message for empty fields
            onView(withId(R.id.tvRegisterError)).check(matches(isDisplayed()));
            onView(withId(R.id.tvRegisterError)).check(matches(withText("¡Error! Campo vacío o usuario ya existente.")));
        }
    }

    @Test
    public void visualTest_05_ErrorUsuarioExistente() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Go to Register screen
            onView(withId(R.id.btnLoginRegister)).perform(click());
            Thread.sleep(1000);

            // Type existing user email and password
            onView(withId(R.id.etEmail)).perform(typeText("laura@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.etPassword)).perform(typeText("1234"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click continue
            onView(withId(R.id.btnRegisterContinue)).perform(click());
            Thread.sleep(1000);

            // Verify error message for existing user
            onView(withId(R.id.tvRegisterError)).check(matches(isDisplayed()));
            onView(withId(R.id.tvRegisterError)).check(matches(withText("¡Error! Campo vacío o usuario ya existente.")));
        }
    }

    @Test
    public void visualTest_06_ErrorLongitudContrasena() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Go to Register screen
            onView(withId(R.id.btnLoginRegister)).perform(click());
            Thread.sleep(1000);

            // Type valid email but short password (3 characters)
            onView(withId(R.id.etEmail)).perform(typeText("test06@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.etPassword)).perform(typeText("123"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click continue
            onView(withId(R.id.btnRegisterContinue)).perform(click());
            Thread.sleep(1000);

            // Verify error message for password length
            onView(withId(R.id.tvRegisterError)).check(matches(isDisplayed()));
            onView(withId(R.id.tvRegisterError)).check(matches(withText("¡Error! La contraseña debe tener al menos 4 caracteres.")));
        }
    }

    @Test
    public void visualTest_07_FormatoEmailError() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Go to Register screen
            onView(withId(R.id.btnLoginRegister)).perform(click());
            Thread.sleep(1000);

            // Type invalid email (missing domain) and a valid password length
            onView(withId(R.id.etEmail)).perform(typeText("test07"), closeSoftKeyboard());
            onView(withId(R.id.etPassword)).perform(typeText("1234"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click continue
            onView(withId(R.id.btnRegisterContinue)).perform(click());
            Thread.sleep(1000);

            // Verify error message for email format
            onView(withId(R.id.tvRegisterError)).check(matches(isDisplayed()));
            onView(withId(R.id.tvRegisterError)).check(matches(withText("¡Error! El formato del correo no es correcto.")));
        }
    }

    @Test
    public void visualTest_08_ErrorVacioLogin() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Click enter/login button directly (with empty fields)
            onView(withId(R.id.btnLoginContinue)).perform(click());
            Thread.sleep(1000);

            // Verify error message for empty login
            onView(withId(R.id.tvLoginError)).check(matches(isDisplayed()));
            onView(withId(R.id.tvLoginError)).check(matches(withText("Campo vacío.")));
        }
    }

    @Test
    public void visualTest_09_Errordatosincorrectos() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Type email "laura" and wrong password "123" (since email must be laura@gmail.com)
            onView(withId(R.id.etLoginEmail)).perform(typeText("laura"), closeSoftKeyboard());
            onView(withId(R.id.etLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click continue
            onView(withId(R.id.btnLoginContinue)).perform(click());
            Thread.sleep(1000);

            // Verify error message for incorrect data
            onView(withId(R.id.tvLoginError)).check(matches(isDisplayed()));
            onView(withId(R.id.tvLoginError)).check(matches(withText("¡Error! Campo vacío o usuario no existente.")));
        }
    }

    @Test
    public void visualTest_10_Usuariologeado() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Type valid user email and password
            onView(withId(R.id.etLoginEmail)).perform(typeText("laura@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.etLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click continue
            onView(withId(R.id.btnLoginContinue)).perform(click());
            Thread.sleep(1000);

            // Verify logged in greeting matches "Hola, laura@gmail.com"
            onView(withId(R.id.tvUserGreeting)).check(matches(isDisplayed()));
            onView(withId(R.id.tvUserGreeting)).check(matches(withText("Hola, laura@gmail.com")));

            // Click logout button (Cerrar sesión)
            onView(withId(R.id.tvLogout)).perform(click());
            Thread.sleep(1000);

            // Verify back on login screen
            onView(withId(R.id.btnLoginContinue)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void visualTest_11_AccederypedirCita() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            // Type valid user email and password
            onView(withId(R.id.etLoginEmail)).perform(typeText("laura@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.etLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click continue
            onView(withId(R.id.btnLoginContinue)).perform(click());
            Thread.sleep(1000);

            // Click Solicitar Cita button
            onView(withId(R.id.btnNavSolicitarCita)).perform(click());
            Thread.sleep(1000);

            // Type date "04/07/26" and time "12:30"
            onView(withId(R.id.etProjectDate)).perform(typeText("04/07/26"), closeSoftKeyboard());
            onView(withId(R.id.etProjectTime)).perform(typeText("12:30"), closeSoftKeyboard());
            Thread.sleep(1000);

            // Click spinner to select first service "Corte de cabello"
            onView(withId(R.id.spinnerServicio)).perform(click());
            onData(anything()).atPosition(0).perform(click());
            Thread.sleep(1000);

            // Click submit to book appointment
            onView(withId(R.id.btnSubmitProject)).perform(click());
            Thread.sleep(1000);

            // Verify on status details screen and click button to return to home
            onView(withId(R.id.tvStatusTitle)).check(matches(isDisplayed()));
            onView(withId(R.id.btnStatusAction)).perform(click());
            Thread.sleep(1000);

            // Verify back on home screen
            onView(withId(R.id.tvUserGreeting)).check(matches(isDisplayed()));

            // Click logout button (Cerrar sesión) to reset the state for subsequent tests
            onView(withId(R.id.tvLogout)).perform(click());
            Thread.sleep(1000);

            // Verify back on login screen
            onView(withId(R.id.btnLoginContinue)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void visualTest_12_MostrarCita() throws InterruptedException {
        Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDataBase db = AppDataBase.getInstance(context);

        // 1. Ensure test user exists in DB
        UserEntity user = db.userDao().getUserByEmail("laura@gmail.com");
        if (user == null) {
            user = new UserEntity();
            user.nombre = "Laura Martinez";
            user.telefono = "600123456";
            user.email = "laura@gmail.com";
            user.password = "123";
            db.userDao().insertUser(user);
            user = db.userDao().getUserByEmail("laura@gmail.com");
        }

        // 2. Clear existing appointments for Laura and insert a clean test appointment
        List<CitaEntity> existing = db.citaDao().getByEmail("laura@gmail.com");
        for (CitaEntity c : existing) {
            db.citaDao().delete(c);
        }

        CitaEntity cita = new CitaEntity();
        cita.id_cliente = user.id_cliente;
        cita.id_empleado = 1;
        cita.id_servicio = 1; // Corte de Cabello
        cita.fecha = "2026-07-05";
        cita.hora = "09:00 - 09:30";
        db.citaDao().insert(cita);

        // 3. Set logged-in session state in AppMediator
        AppMediator.getInstance().saveSession(context, user);
        AppMediator.getInstance().setLoggedUser(user);

        // 4. Launch homeActivity
        try (ActivityScenario<homeActivity> scenario = ActivityScenario.launch(homeActivity.class)) {
            Thread.sleep(1000);

            // 5. Click on "Mostrar Cita"
            onView(withId(R.id.btnNavMostrarCita)).perform(click());
            Thread.sleep(1000);

            // 6. Verify appointment details are visible
            onView(withId(R.id.tvMiCitaFechaHora)).check(matches(isDisplayed()));
            onView(withId(R.id.tvMiCitaFechaHora)).check(matches(withText("2026-07-05 a las 09:00 - 09:30")));
            onView(withId(R.id.tvMiCitaServicio)).check(matches(isDisplayed()));
            onView(withId(R.id.cvCitaCard)).check(matches(isDisplayed()));

            Thread.sleep(1000);
        }
    }

    @Test
    public void visualTest_13_CancelarCita() throws InterruptedException {
        Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDataBase db = AppDataBase.getInstance(context);

        // 1. Ensure test user exists in DB
        UserEntity user = db.userDao().getUserByEmail("laura@gmail.com");
        if (user == null) {
            user = new UserEntity();
            user.nombre = "Laura Martinez";
            user.telefono = "600123456";
            user.email = "laura@gmail.com";
            user.password = "123";
            db.userDao().insertUser(user);
            user = db.userDao().getUserByEmail("laura@gmail.com");
        }

        // 2. Clear existing appointments for Laura and insert a clean test appointment
        List<CitaEntity> existing = db.citaDao().getByEmail("laura@gmail.com");
        for (CitaEntity c : existing) {
            db.citaDao().delete(c);
        }

        CitaEntity cita = new CitaEntity();
        cita.id_cliente = user.id_cliente;
        cita.id_empleado = 1;
        cita.id_servicio = 1; // Corte de Cabello
        cita.fecha = "2026-07-05";
        cita.hora = "09:00 - 09:30";
        db.citaDao().insert(cita);

        // 3. Set logged-in session state in AppMediator
        AppMediator.getInstance().saveSession(context, user);
        AppMediator.getInstance().setLoggedUser(user);

        // 4. Launch homeActivity
        try (ActivityScenario<homeActivity> scenario = ActivityScenario.launch(homeActivity.class)) {
            Thread.sleep(1000);

            // 5. Click on "Mostrar Cita"
            onView(withId(R.id.btnNavMostrarCita)).perform(click());
            Thread.sleep(1000);

            // 6. Click "Cancelar Cita"
            onView(withId(R.id.btnCancelCita)).perform(click());
            Thread.sleep(1000);

            // 7. Verify appointment card is now hidden and no appointments are shown
            onView(withId(R.id.tvCitaReservadaTitle)).check(matches(withText("No tienes citas reservadas")));
            onView(withId(R.id.cvCitaCard)).check(matches(not(isDisplayed())));
            onView(withId(R.id.btnCancelCita)).check(matches(not(isDisplayed())));

            Thread.sleep(1000);
        }
    }

    @Test
    public void visualTest_14_CorteCaballero() throws InterruptedException {
        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            Thread.sleep(1000);

            // 1. Enter guest mode
            onView(withId(R.id.btnLoginGuest)).perform(click());
            Thread.sleep(1000);

            // 2. Click "Lista de Servicios" button
            onView(withId(R.id.btnGuestListaServicios)).perform(click());
            Thread.sleep(1000);

            // 3. Choose "Corte de cabello" service
            onView(withText("Corte de cabello")).perform(click());
            Thread.sleep(1000);

            // 4. Verify detail page is opened for "Corte de cabello"
            onView(withId(R.id.tvDetailServiceName)).check(matches(isDisplayed()));
            onView(withId(R.id.tvDetailServiceName)).check(matches(withText("Corte de cabello")));

            Thread.sleep(1000);
        }
    }

    @Test
    public void visualTest_15_Favorito() throws InterruptedException {
        Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDataBase db = AppDataBase.getInstance(context);

        // 1. Ensure test user exists in DB
        UserEntity user = db.userDao().getUserByEmail("laura@gmail.com");
        if (user == null) {
            user = new UserEntity();
            user.nombre = "Laura Martinez";
            user.telefono = "600123456";
            user.email = "laura@gmail.com";
            user.password = "123";
            db.userDao().insertUser(user);
            user = db.userDao().getUserByEmail("laura@gmail.com");
        }

        // 2. Remove any pre-existing favorites to have a clean state
        db.favoriteDao().removeFavorite(user.email, 1); // Corte de Cabello (ID 1)
        db.favoriteDao().removeFavorite(user.email, 3); // Afeitado Barba (ID 3)

        // 3. Set logged-in session state in AppMediator
        AppMediator.getInstance().saveSession(context, user);
        AppMediator.getInstance().setLoggedUser(user);

        // 4. Launch homeActivity
        try (ActivityScenario<homeActivity> scenario = ActivityScenario.launch(homeActivity.class)) {
            Thread.sleep(1000);

            // 5. Click on "Lista de Servicios"
            onView(withId(R.id.btnNavListaServicios)).perform(click());
            Thread.sleep(1000);

            // 6. Mark "Corte de cabello" and "Afeitado barba" as favorites
            onView(allOf(withId(R.id.btnFavorite), hasSibling(hasDescendant(withText("Corte de cabello"))))).perform(scrollTo(), click());
            Thread.sleep(1000);
            onView(allOf(withId(R.id.btnFavorite), hasSibling(hasDescendant(withText("Afeitado barba"))))).perform(scrollTo(), click());
            Thread.sleep(1000);

            // 7. Return to main menu (homeActivity)
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 8. Go to "Favoritos" screen
            onView(withId(R.id.btnNavFavorito)).perform(click());
            Thread.sleep(1000);

            // 9. Verify both selected favorites are displayed
            onView(withText("Corte de cabello")).check(matches(isDisplayed()));
            onView(withText("Afeitado barba")).check(matches(isDisplayed()));

            // 10. Click on "Corte de cabello" to view details
            onView(withText("Corte de cabello")).perform(click());
            Thread.sleep(1000);

            // 11. Verify service details for "Corte de cabello" are displayed
            onView(withId(R.id.tvDetailServiceName)).check(matches(isDisplayed()));
            onView(withId(R.id.tvDetailServiceName)).check(matches(withText("Corte de cabello")));

            Thread.sleep(1000);
        }
    }

    @Test
    public void visualTest_16_pruebaencapsulamiento() throws InterruptedException {
        Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDataBase db = AppDataBase.getInstance(context);

        // 1. Ensure Laura and Carlos exist in database
        UserEntity laura = db.userDao().getUserByEmail("laura@gmail.com");
        if (laura == null) {
            laura = new UserEntity();
            laura.nombre = "Laura Martinez";
            laura.telefono = "600123456";
            laura.email = "laura@gmail.com";
            laura.password = "123";
            db.userDao().insertUser(laura);
            laura = db.userDao().getUserByEmail("laura@gmail.com");
        }

        UserEntity carlos = db.userDao().getUserByEmail("carlos@gmail.com");
        if (carlos == null) {
            carlos = new UserEntity();
            carlos.nombre = "Carlos Pérez";
            carlos.telefono = "611987654";
            carlos.email = "carlos@gmail.com";
            carlos.password = "123";
            db.userDao().insertUser(carlos);
            carlos = db.userDao().getUserByEmail("carlos@gmail.com");
        }

        // 2. Clear existing appointments and favorites for both to ensure a clean state
        for (CitaEntity c : db.citaDao().getByEmail("laura@gmail.com")) {
            db.citaDao().delete(c);
        }
        for (CitaEntity c : db.citaDao().getByEmail("carlos@gmail.com")) {
            db.citaDao().delete(c);
        }
        db.favoriteDao().removeFavorite("laura@gmail.com", 5); // Lavado y peinado (ID 5)
        db.favoriteDao().removeFavorite("laura@gmail.com", 6); // Tinte raiz (ID 6)
        db.favoriteDao().removeFavorite("carlos@gmail.com", 1); // Corte de Cabello (ID 1)
        db.favoriteDao().removeFavorite("carlos@gmail.com", 9); // Manicura semipermanente (ID 9)

        // 3. Clear session and launch loginActivity
        AppMediator.getInstance().saveSession(context, null);
        AppMediator.getInstance().setLoggedUser(null);

        try (ActivityScenario<loginActivity> scenario = ActivityScenario.launch(loginActivity.class)) {
            Thread.sleep(1000);

            // 4. Log in as Laura
            onView(withId(R.id.etLoginEmail)).perform(typeText("laura@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.etLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
            Thread.sleep(1000);
            onView(withId(R.id.btnLoginContinue)).perform(click());
            Thread.sleep(1000);

            // 5. Book appointment for July 5th at 16:00 for "Mechas balayage"
            onView(withId(R.id.btnNavSolicitarCita)).perform(click());
            Thread.sleep(1000);
            onView(withId(R.id.etProjectDate)).perform(typeText("05/07/26"), closeSoftKeyboard());
            onView(withId(R.id.etProjectTime)).perform(typeText("16:00"), closeSoftKeyboard());
            Thread.sleep(1000);
            onView(withId(R.id.spinnerServicio)).perform(click());
            Thread.sleep(1000);
            onData(anything()).atPosition(3).perform(click()); // Mechas balayage (Index 3)
            Thread.sleep(1000);
            onView(withId(R.id.btnSubmitProject)).perform(click());
            Thread.sleep(1000);
            onView(withId(R.id.btnStatusAction)).perform(click());
            Thread.sleep(1000);

            // 6. Go to "Lista de Servicios" and favorite "Lavado y peinado" and "Tinte raiz"
            onView(withId(R.id.btnNavListaServicios)).perform(click());
            Thread.sleep(1000);
            onView(allOf(withId(R.id.btnFavorite), hasSibling(hasDescendant(withText("Lavado y peinado"))))).perform(scrollTo(), click());
            Thread.sleep(1000);
            onView(allOf(withId(R.id.btnFavorite), hasSibling(hasDescendant(withText("Tinte raiz"))))).perform(scrollTo(), click());
            Thread.sleep(1000);
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 7. Verify both favorites are in Favorites screen
            onView(withId(R.id.btnNavFavorito)).perform(click());
            Thread.sleep(1000);
            onView(withText("Lavado y peinado")).check(matches(isDisplayed()));
            onView(withText("Tinte raiz")).check(matches(isDisplayed()));
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 8. Log out Laura
            onView(withId(R.id.tvLogout)).perform(click());
            Thread.sleep(1000);

            // 9. Log in as Carlos
            onView(withId(R.id.etLoginEmail)).perform(typeText("carlos@gmail.com"), closeSoftKeyboard());
            onView(withId(R.id.etLoginPassword)).perform(typeText("123"), closeSoftKeyboard());
            Thread.sleep(1000);
            onView(withId(R.id.btnLoginContinue)).perform(click());
            Thread.sleep(1000);

            // 10. Check Carlos has NO appointments in "Mostrar Cita"
            onView(withId(R.id.btnNavMostrarCita)).perform(click());
            Thread.sleep(1000);
            onView(withId(R.id.tvCitaReservadaTitle)).check(matches(withText("No tienes citas reservadas")));
            onView(withId(R.id.cvCitaCard)).check(matches(not(isDisplayed())));
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 11. Check Carlos has NO favorites
            onView(withId(R.id.btnNavFavorito)).perform(click());
            Thread.sleep(1000);
            onView(withId(R.id.llProjectContainer)).check(matches(hasChildCount(0)));
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 12. Add "Corte de cabello" and "Manicura semipermanente" to Carlos's favorites
            onView(withId(R.id.btnNavListaServicios)).perform(click());
            Thread.sleep(1000);
            onView(allOf(withId(R.id.btnFavorite), hasSibling(hasDescendant(withText("Corte de cabello"))))).perform(scrollTo(), click());
            Thread.sleep(1000);
            onView(allOf(withId(R.id.btnFavorite), hasSibling(hasDescendant(withText("Manicura semipermanente"))))).perform(scrollTo(), click());
            Thread.sleep(1000);
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 13. Verify Carlos's favorites screen has his new favorites
            onView(withId(R.id.btnNavFavorito)).perform(click());
            Thread.sleep(1000);
            onView(withText("Corte de cabello")).check(matches(isDisplayed()));
            onView(withText("Manicura semipermanente")).check(matches(isDisplayed()));
            onView(withId(R.id.btnHeaderBack)).perform(click());
            Thread.sleep(1000);

            // 14. Log out Carlos
            onView(withId(R.id.tvLogout)).perform(click());
            Thread.sleep(1000);
        }
    }

    public static org.hamcrest.Matcher<android.view.View> withDrawable(final int resourceId) {
        return new androidx.test.espresso.matcher.BoundedMatcher<android.view.View, android.widget.ImageView>(android.widget.ImageView.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with drawable from resource id: " + resourceId);
            }

            @Override
            protected boolean matchesSafely(android.widget.ImageView imageView) {
                if (resourceId < 0) {
                    return imageView.getDrawable() == null;
                }
                android.graphics.drawable.Drawable.ConstantState actual = null;
                if (imageView.getDrawable() != null) {
                    actual = imageView.getDrawable().getConstantState();
                }
                android.graphics.drawable.Drawable expected = imageView.getContext().getResources().getDrawable(resourceId);
                android.graphics.drawable.Drawable.ConstantState expectedState = null;
                if (expected != null) {
                    expectedState = expected.getConstantState();
                }
                return actual != null && actual.equals(expectedState);
            }
        };
    }

    @After
    public void tearDown() {
        Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDataBase db = AppDataBase.getInstance(context);

        // Clear session and restore to guest/clean state so subsequent tests don't fail
        AppMediator.getInstance().saveSession(context, null);
        AppMediator.getInstance().setLoggedUser(null);

        // Clear test appointments to avoid side effects
        List<CitaEntity> list = db.citaDao().getByEmail("laura@gmail.com");
        for (CitaEntity c : list) {
            db.citaDao().delete(c);
        }
        List<CitaEntity> list2 = db.citaDao().getByEmail("carlos@gmail.com");
        for (CitaEntity c : list2) {
            db.citaDao().delete(c);
        }

        // Clear test favorites to avoid side effects
        db.favoriteDao().removeFavorite("laura@gmail.com", 1);
        db.favoriteDao().removeFavorite("laura@gmail.com", 3);
        db.favoriteDao().removeFavorite("laura@gmail.com", 5);
        db.favoriteDao().removeFavorite("laura@gmail.com", 6);
        db.favoriteDao().removeFavorite("carlos@gmail.com", 1);
        db.favoriteDao().removeFavorite("carlos@gmail.com", 9);
    }
}
