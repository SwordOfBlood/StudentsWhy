package com.example.studentswhy;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.studentswhy.dummy.TeacherContent;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    int counter = 0;
    boolean alloweder = true;
    public class NewThread extends AsyncTask<String, Void, String>
    {
        // благодоря этому классу мы будет разбирать данные на куски
        public Element title;
        // Метод выполняющий запрос в фоне, в версиях выше 4 андроида, запросы в главном потоке выполнять
        // нельзя, поэтому все что вам нужно выполнять - выносите в отдельный тред
        @Override
        protected String doInBackground(String... arg) {

            // класс который захватывает страницу
            Document doc;
            try {
                // определяем откуда будем воровать данные
                doc = Jsoup.connect(arg[0]).get();
                // задаем с какого места
                title = doc.select(".l-extra.small").first();
                if(title != null)
                   TeacherContent.ITEMS.get(Integer.parseInt(arg[1])).
                           setNumber(title.text().replace(',',' '));
                if (!doc.select(".l-extra.small").isEmpty())
                title = doc.select(".l-extra.small").last();
                if(title != null)
                    TeacherContent.ITEMS.get(Integer.parseInt(arg[1])).
                            setEmail(title.child(2).attr("data-at")
                                    .replaceAll("-at-","@")
                                    .replace("\"","")
                                    .replace("[","")
                                    .replace("]","")
                                    .replace(",",""));
                title = doc.select(".content__inner.content__inner_foot1").first();
                if(title != null)
                {
                    TeacherContent.ITEMS.get(Integer.parseInt(arg[1])).
                            setName(title.text());
                    TeacherContent.ITEMS.get(Integer.parseInt(arg[1])).
                            setPlace(title.getElementsByTag("a").attr("abs:href"));
                }
                alloweder = true;

            } catch (IOException e)
            {

            }
            // ничего не возвращаем потому что я так захотел)
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {

        }
    }

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
                    counter++;
                    if (TeacherContent.ITEMS.get(0).getNumber().equals(""))
                    {
                        for(int i = 0; i<TeacherContent.ITEMS.size()-1;i++)
                        {
                            String[] FIO = TeacherContent.ITEMS.get(i).getName().split(" ");
                            String searchLink = "https://www.hse.ru/org/persons/?search_person=";
                            for (int j = 1; j< FIO.length; j++)
                            {
                                searchLink+="+"+FIO[j];
                            }
                            do {
                               TeacherContent.ITEMS.get(i).getEmail();
                            } while(!alloweder);
                            alloweder = false;
                            new NewThread().execute(searchLink, Integer.toString(i));
                        }
                    }
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
