package eu.aljoshavieth.simplevirus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.psdev.licensesdialog.LicensesDialog;

/**
 * Created by Aljosha Vieth on 04.06.2016.
 * https://aljoshavieth.eu
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    //set up the LicenseDialog
    public void onLicenseClick(final View view) {
        new LicensesDialog.Builder(this)
                .setNotices(R.raw.licenses)
                .build()
                .show();
    }
}