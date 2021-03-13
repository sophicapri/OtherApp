package com.jwhh.jim.otherapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.socap.notekeeper.NoteKeeperProviderContract.Courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_NOTEKEEPER_COURSES = 0;
    private SimpleCursorAdapter mCoursesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        mCoursesAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null,
                new String[]{"course_title", "course_id"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        ListView listCourses = findViewById(R.id.list_courses);
        listCourses.setAdapter(mCoursesAdapter);

        getLoaderManager().initLoader(LOADER_NOTEKEEPER_COURSES, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Courses.INSTANCE.getCONTENT_URI();
        String[] columns = {Courses.INSTANCE.get_ID(),
                Courses.INSTANCE.getCOLUMN_COURSE_TITLE(),
                Courses.INSTANCE.getCOLUMN_COURSE_ID()};

        return new CursorLoader(this, uri, columns, null, null, "course_title");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCoursesAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCoursesAdapter.changeCursor(null);
    }
}
