package com.example.studentswhy;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.studentswhy.dummy.FAQContent;
import com.example.studentswhy.dummy.LessonContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuActivity extends AppCompatActivity implements SubjectFragment.OnListFragmentInteractionListener,
        TeacherFragment.OnListFragmentInteractionListener,
        NewsFragment.OnListFragmentInteractionListener,
        FAQFragment.OnListFragmentInteractionListener,
        LessonFragment.OnListFragmentInteractionListener
{
    private NewsFragment newsFragment = new NewsFragment();
    private FAQFragment faqFragment = new FAQFragment();
    private SubjectFragment subjectFragment = new SubjectFragment();
    private TeacherFragment teacherFragment = new TeacherFragment();
    private LessonFragment lessonFragment = new LessonFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @SuppressLint({"SetTextI18n", "StaticFieldLeak"})
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.app.FragmentManager fragmentManager = getFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId())
            {
                case R.id.navigation_Schedule:
                    transaction.replace(R.id.homepage, lessonFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_News:
                    transaction.replace(R.id.homepage, newsFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_UsefulInfo:
                    transaction.replace(R.id.homepage, faqFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_Subjectslist:
                    transaction.replace(R.id.homepage, subjectFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_TeachersList:
                    transaction.replace(R.id.homepage, teacherFragment);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    public void onChangeUserButtonClick(android.view.View e)
    {
        LessonContent.ITEMS.clear();

        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.app.FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.homepage, lessonFragment);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(FAQContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(News item)
    {

    }

    @Override
    public void onListFragmentInteraction(Subject item) {

    }

    @Override
    public void onListFragmentInteraction(Teacher item) {

    }

    @Override
    public void onListFragmentInteraction(Lesson item) {

    }
}
