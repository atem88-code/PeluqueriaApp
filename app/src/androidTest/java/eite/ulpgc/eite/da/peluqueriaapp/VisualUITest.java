package eite.ulpgc.eite.da.peluqueriaapp;

import android.content.pm.ActivityInfo;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import eite.ulpgc.eite.da.peluqueriaapp.app.AppMediator;
import eite.ulpgc.eite.da.peluqueriaapp.login.loginActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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
            onView(withText("Tinte raiz")).perform(click());
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
}
