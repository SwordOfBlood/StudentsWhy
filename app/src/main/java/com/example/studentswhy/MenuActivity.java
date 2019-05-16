package com.example.studentswhy;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.studentswhy.dummy.NewsContent;
import com.example.studentswhy.dummy.TeacherContent;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                DatabaseReference myRef2 = database.getReference("tutors/"+
                        arg[2]);
                // определяем откуда будем воровать данные
                doc = Jsoup.connect(arg[0]).get();
                // задаем с какого места
                title = doc.select(".l-extra.small").first();

                if(title != null)
                    myRef2.child("/chairGid").setValue(title.text().replace(',',' '));
                if (!doc.select(".l-extra.small").isEmpty())
                    title = doc.select(".l-extra.small").last();
                if(title != null)
                    myRef2.child("/chairOid").setValue(title.child(2).attr("data-at")
                                    .replaceAll("-at-","@")
                                    .replace("\"","")
                                    .replace("[","")
                                    .replace("]","")
                                    .replace(",",""));
                title = doc.select(".content__inner.content__inner_foot1").first();
                if(title != null)
                {
                    myRef2.child("/lecturerOid").setValue(title.text());
                    myRef2.child("/lecturerGid").setValue(title.getElementsByTag("a").attr("abs:href"));
                }
                myRef2.child("person").setValue("-2");
                alloweder = true;

            } catch (Exception e)
            {
                alloweder = true;
            }
            finally {
                alloweder = true;
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
                    DatabaseReference myRef = database.getReference("News");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            if (dataSnapshot.getChildrenCount() > NewsContent.ITEMS.size())
                            for (int i = 0; i < dataSnapshot.getChildrenCount(); i++)
                            {
                                NewsContent.addItem(new News(dataSnapshot.child(Integer.toString(i+1)+"/Date").getValue().toString(),
                                        dataSnapshot.child(Integer.toString(i+1)+"/Header").getValue().toString(),
                                        dataSnapshot.child(Integer.toString(i+1)+"/Body").getValue().toString(),
                                        Integer.parseInt(dataSnapshot.child(Integer.toString(i+1)+"/Likes").getValue().toString()),
                                        Integer.toString(i+1)));
                            }
                            else
                                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++)
                                {

                                    NewsContent.ITEMS.get(i).setLikes(Integer.parseInt(dataSnapshot.child(Integer.toString(i+1)+"/Likes").getValue().toString()));
                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                    transaction.replace(R.id.homepage, newsFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_UsefulInfo:
                    DatabaseReference myRef1 = database.getReference("Info");
                    myRef1.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            for (int i = 0; i < FAQContent.ITEMS.size(); i++)
                            {
                                if(FAQContent.ITEMS.get(i).details != 0)
                                    FAQContent.ITEMS.get(i).setValueText(
                                            FAQContent.ITEMS.get(i).getValueText()+"\n"+
                                            dataSnapshot.child(Integer.toString
                                                    (FAQContent.ITEMS.get(i).details))
                                                    .getValue().toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                    transaction.replace(R.id.homepage, faqFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_Subjectslist:
                    transaction.replace(R.id.homepage, subjectFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_TeachersList:
                    counter++;
                    if (TeacherContent.ITEMS.size() != 0 && TeacherContent.ITEMS.get(0).getNumber().equals(""))
                    {
                        final DatabaseReference myRef3 = database.getReference("tutors");
                        myRef3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                for(int i = 0; i < TeacherContent.ITEMS.size();i++){
                                    String[] FIO = TeacherContent.ITEMS.get(i).getName().split(" ");
                                    String searchLink = "https://www.hse.ru/org/persons/?search_person=";
                                    String searchLink2 = "";
                                    for (int j = 1; j< FIO.length; j++)
                                    {
                                        if (!FIO[j].equals("-"))
                                            searchLink+="+"+FIO[j];
                                        if (j == FIO.length-1)
                                            searchLink2+=FIO[j];
                                        else
                                            searchLink2+=FIO[j]+" ";
                                    }
                                    if (dataSnapshot.hasChild(searchLink2+ "/person"))
                                    {
                                        if(!dataSnapshot.child(searchLink2 + "/person").getValue().toString().equals("-2"))
                                        {
                                            do {
                                                TeacherContent.ITEMS.get(i).getEmail();
                                            } while(!alloweder);
                                            alloweder = false;
                                            new NewThread().execute(searchLink, Integer.toString(i),searchLink2);

                                        }
                                        TeacherContent.ITEMS.get(i).setNumber(
                                                dataSnapshot.child(searchLink2+"/chairGid").getValue().toString());
                                        TeacherContent.ITEMS.get(i).setEmail(
                                                dataSnapshot.child(searchLink2+"/chairOid").getValue().toString());
                                        TeacherContent.ITEMS.get(i).setPlace(
                                                dataSnapshot.child(searchLink2+"/lecturerGid").getValue().toString());
                                        TeacherContent.ITEMS.get(i).setName(
                                                dataSnapshot.child(searchLink2+"/lecturerOid").getValue().toString());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                            }
                        });
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FAQContent.addItem(new FAQContent.DummyItem("1","#Вопросы по поступлению ", 0, 0, 1));
        FAQContent.addItem(new FAQContent.DummyItem("\t1.1", "#Перед подачей документов", 0,1,2));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.1.1", "С чего начать поступление в Вышку?",6,2, 10));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.1.2", "В Вышке есть бюджетные места?", 7,2, 11));
        FAQContent.addItem(new FAQContent.DummyItem("\t1.2", "#Подача документов", 0,1, 3));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.2.1", "В какие сроки мне нужно подать документы?", 4,3, 12));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.2.2", "Как подать документы?", 5,3, 13));
        FAQContent.addItem(new FAQContent.DummyItem("\t1.3", "#Конкурс", 0,1, 4));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.3.1", "Сколько мне нужно баллов?", 10,4, 14));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.3.2", "Сколько лет действует олимпиада?", 12,4, 15));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.3.3", "Какие документы нужно принести в приёмную комиссию, если я поступаю по олимпиаде?", 13,4, 16));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.3.4", "У меня есть две льготы в 100 баллов ЕГЭ. Могу ли я претендовать на каждую из них?", 14,4, 17));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t1.3.5", "У меня есть две льготы БВИ. Могу ли я претендовать на каждую из них?", 15,4, 18));
        FAQContent.addItem(new FAQContent.DummyItem("2", "#5 Мифов о Вышке", 0,0, 5));
        FAQContent.addItem(new FAQContent.DummyItem("\t2.1", "Миф № 1. Учат только экономике?", 17,5, 19));
        FAQContent.addItem(new FAQContent.DummyItem("\t2.2", "Миф № 2: В этом университете учатся преимущественно москвичи?", 18,5, 20));
        FAQContent.addItem(new FAQContent.DummyItem("\t2.3", "Миф № 3. Это платный вуз?", 19,5, 21));
        FAQContent.addItem(new FAQContent.DummyItem("\t2.4", "Миф № 4. Вышка — это только дополнительное образование?", 20,5, 22));
        FAQContent.addItem(new FAQContent.DummyItem("\t2.4", "Миф № 5. В Вышке одни «мажоры»?", 21,5, 23));
        FAQContent.addItem(new FAQContent.DummyItem("3", "#Скидки на обучение", 0,0, 6));
        FAQContent.addItem(new FAQContent.DummyItem("\t3.1", "Кто может рассчитывать на скидку в бакалавриате?", 23,6, 24));
        FAQContent.addItem(new FAQContent.DummyItem("\t3.2", "Я выпускник лицея НИУ ВШЭ, полагается ли мне скидка?", 24,6, 25));
        FAQContent.addItem(new FAQContent.DummyItem("4", "#Общежития НИУ ВШЭ", 0,0, 7));
        FAQContent.addItem(new FAQContent.DummyItem("\t4.1", "Кому предоставляется общежития?", 26,7, 26));
        FAQContent.addItem(new FAQContent.DummyItem("\t4.2", "Что такое Трилистник?", 27,7, 27));
        FAQContent.addItem(new FAQContent.DummyItem("\t4.3", "Что такое Дубки?", 28,7, 28));
        FAQContent.addItem(new FAQContent.DummyItem("\t4.4", "Что такое Шестерка?", 29,7, 29));
        FAQContent.addItem(new FAQContent.DummyItem("5", "Что такое ЛМС?", 30,0, 30));
        FAQContent.addItem(new FAQContent.DummyItem("6", "Как поучиться в иностранном вузе по обмену?", 31,0, 31));
        FAQContent.addItem(new FAQContent.DummyItem("7", "Как выучить дополнительный иностранный язык?", 32,0, 32));
        FAQContent.addItem(new FAQContent.DummyItem("8", "#Спорт", 0,0, 8));
        FAQContent.addItem(new FAQContent.DummyItem("\t8.1", "В вышке есть спорт. секции?", 34,8, 9));
        FAQContent.addItem(new FAQContent.DummyItem("\t\t8.1.1", "Что такое ССК?", 35,9, 33));
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
