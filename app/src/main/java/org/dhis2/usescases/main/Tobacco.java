package org.dhis2.usescases.main;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.dhis2.R;
import org.dhis2.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2Manager;
import org.hisp.dhis.android.core.user.User;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class Tobacco extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    @Inject
    public AnalyticsHelper analyticsHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.working_list);
        setContentView(R.layout.activity_tobbacco);
        compositeDisposable = new CompositeDisposable();


        String user = "admin";
        String pass = "district";
        String pair = String.format("%s:%s", user, pass);
        String creds = Base64.encodeToString(pair.getBytes(), Base64.NO_WRAP);


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("Main", " data search" + newText);

                return true;
            }
        });


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.searchView) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
//        Intent intent=new Intent(this, MainActivity.class);
//        this.startActivity(intent);
    }


    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void clearApplicationData() {
    File cacheDirectory = getCacheDir();
    File applicationDirectory = new File(cacheDirectory.getParent());
    if (applicationDirectory.exists()) {
        String[] fileNames = applicationDirectory.list();
        for (String fileName : fileNames) {
            if (!fileName.equals("lib")) {
                deleteFile(new File(applicationDirectory, fileName));

            }
        }
    }
}

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}
