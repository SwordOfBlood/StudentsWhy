package com.example.studentswhy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentswhy.dummy.LessonContent;
import com.example.studentswhy.dummy.SubjectContent;
import com.example.studentswhy.dummy.TeacherContent;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>
{
    SharedPreferences sPref;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private View mProgressView;
    private View mLoginFormView;

    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null)
            return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        final String email = "";
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString("SAVED_DATA", "");
        mEmailView.setText(savedText);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void attemptLogin() throws IOException {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            mEmailView.setError("Your email adress should contain @edu.hse.ru or @hse.ru.\n" +
                    "If you are not hse member, enter \"support@hse.ru\"");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email))
        {
            mEmailView.setError("Your email adress should contain @edu.hse.ru or @hse.ru.\n" +
                    "If you are not hse member, enter \"support@hse.ru\"");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if ( !isOnline() ){
                Toast.makeText(getApplicationContext(),
                        "Нет соединения с интернетом!",Toast.LENGTH_LONG).show();
                return;
            }
            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("SAVED_DATA", email);
            ed.commit();
            showProgress(true);
            mAuthTask = new UserLoginTask(email);
            loaderForLessons(email);
            loaderForTeachers(email);
            loaderForSubjects(email);
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loaderForLessons(String email) throws IOException {
        Callback<List<Lesson>> cb = new Callback<List<Lesson>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<Lesson>> call, @NonNull Response<List<Lesson>> response)
            {
                try{
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        LessonContent.addItem(new Lesson(response.body().get(i).getAuditorium(),
                            response.body().get(i).getBuilding(),
                            response.body().get(i).getDayOfWeekString(),
                            response.body().get(i).getBeginLesson(),
                            response.body().get(i).getDate(),
                            response.body().get(i).getDiscipline(),
                            response.body().get(i).getKindOfWork(),
                            response.body().get(i).getLecturer()));
                    }
                }
                catch (Exception e)
                {
                    LessonContent.addItem(new Lesson(" "," ",
                            " ", " "," ",
                            "You should be logged in as HSE member"," "," "));
                };
            }

            @Override
            public void onFailure(@NonNull Call<List<Lesson>> call, @NonNull Throwable t)
            {
                LessonContent.addItem(new Lesson(" "," ",
                        " ", " "," ",
                        "You should be logged in as hse member"," "," "));
            }
        };
        Date dateStart = new Date();
        Date dateEnd = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        switch (dateStart.getDay())
        {
            case 0:
                dateStart.setDate(dateStart.getDate() + 1);
                dateEnd.setDate(dateEnd.getDate() + 7);
                break;
            case 1:
                dateStart.setDate(dateStart.getDate());
                dateEnd.setDate(dateEnd.getDate() + 6);
                break;
            case 2:
                dateStart.setDate(dateStart.getDate() - 1);
                dateEnd.setDate(dateEnd.getDate() + 5);
                break;
            case 3:
                dateStart.setDate(dateStart.getDate() - 2);
                dateEnd.setDate(dateEnd.getDate() + 4);
                break;
            case 4:
                dateStart.setDate(dateStart.getDate() - 3);
                dateEnd.setDate(dateEnd.getDate() + 3);
                break;
            case 5:
                dateStart.setDate(dateStart.getDate() - 4);
                dateEnd.setDate(dateEnd.getDate() + 2);
                break;
            case 6:
                dateStart.setDate(dateStart.getDate() - 5);
                dateEnd.setDate(dateEnd.getDate() + 1);
                break;
        }
        if (email.contains("@edu"))
        {
            NetworkService.getInstance()
                .getJSONApi()
                .getDataForStudents(email,dateFormat.format(dateStart),dateFormat.format(dateEnd))
                .enqueue(cb);
        }
        else
            {
                NetworkService.getInstance()
                        .getJSONApi()
                        .getDataForTeachers(email,dateFormat.format(dateStart),dateFormat.format(dateEnd), "1")
                        .enqueue(cb);
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loaderForTeachers(String email) throws IOException {
        Callback<List<Lesson>> cb = new Callback<List<Lesson>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<Lesson>> call, @NonNull Response<List<Lesson>> response)
            {
                try{
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        if (!TeacherContent.ITEM_MAP.containsKey(response.body().get(i).getLecturer()))
                        {
                            TeacherContent.addItem(new Teacher(response.body().get(i).getLecturer(),
                                    response.body().get(i).getLecturer(),"",
                                    "","",""));
                        }
                    }
                }
                catch (Exception e)
                {
                    TeacherContent.addItem(new Teacher("","",
                            "", "","",
                            "You should be logged in as HSE member"));
                };
            }

            @Override
            public void onFailure(@NonNull Call<List<Lesson>> call, @NonNull Throwable t)
            {
                TeacherContent.addItem(new Teacher(" "," ",
                        " ", " "," ",
                        "You should be logged in as hse member"));
            }
        };
        Date dateStart = new Date();
        Date dateEnd = new Date();
        if (dateStart.getMonth() < 6)
        {
            dateStart.setMonth(0);
            dateEnd.setMonth(4);
        }
        else
        {
            dateStart.setMonth(8);
            dateEnd.setMonth(11);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        if (email.contains("@edu"))
        {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getDataForStudents(email,dateFormat.format(dateStart),dateFormat.format(dateEnd))
                    .enqueue(cb);
        }
        else
        {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getDataForTeachers(email,dateFormat.format(dateStart),dateFormat.format(dateEnd), "1")
                    .enqueue(cb);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loaderForSubjects(String email) throws IOException {
        Callback<List<Lesson>> cb = new Callback<List<Lesson>>()
        {
            @Override
            public void onResponse(@NonNull Call<List<Lesson>> call, @NonNull Response<List<Lesson>> response)
            {
                try{
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        if (!SubjectContent.ITEM_MAP.containsKey(response.body().get(i).getDiscipline()))
                            SubjectContent.addItem(new Subject(response.body().get(i).getDiscipline(),
                                response.body().get(i).getLecturer(),Integer.toString(i),"",
                                ""));
                    }
                }
                catch (Exception e)
                {
                    SubjectContent.addItem(new Subject(" "," ",
                            " ", " ",
                            "You should be logged in as HSE member"));
                };
            }

            @Override
            public void onFailure(@NonNull Call<List<Lesson>> call, @NonNull Throwable t)
            {
                SubjectContent.addItem(new Subject(" "," ",
                        " ", " ",
                        "You should be logged in as hse member"));
            }
        };
        Date dateStart = new Date();
        Date dateEnd = new Date();
        if (dateStart.getMonth() < 6)
        {
            dateStart.setMonth(0);
            dateEnd.setMonth(4);
        }
        else
        {
            dateStart.setMonth(8);
            dateEnd.setMonth(11);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        if (email.contains("@edu"))
        {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getDataForStudents(email,dateFormat.format(dateStart),dateFormat.format(dateEnd))
                    .enqueue(cb);
        }
        else
        {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getDataForTeachers(email,dateFormat.format(dateStart),dateFormat.format(dateEnd), "1")
                    .enqueue(cb);
        }
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@edu.hse.ru")||email.contains("@hse.ru");
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;

        UserLoginTask(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return true;
                }
            }
            return true;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}