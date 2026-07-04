package eite.ulpgc.eite.da.peluqueriaapp;

import android.content.pm.ActivityInfo;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import eite.ulpgc.eite.da.peluqueriaapp.login.loginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class VisualUITest {

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
}
