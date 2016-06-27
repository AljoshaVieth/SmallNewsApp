package eu.aljoshavieth.simplevirus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Aljosha Vieth on 30.05.2016.
 * http://aljosha.eu
 */
public class ArticleActivity extends AppCompatActivity{

    private TextView articleText;
    private TextView articleDescription;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);
        articleText = (TextView) findViewById(R.id.articleText);
        articleDescription = (TextView) findViewById(R.id.articleDescription);
        Intent intent = getIntent();
        String link = intent.getStringExtra(MainActivity.LINK);
        String title = intent.getStringExtra(MainActivity.TITLE);
        String description = intent.getStringExtra(MainActivity.DESCRIPTION);
        articleDescription.setText(description);
        setTitle(title);
        readWebpage(link);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            articleText.setText(Html.fromHtml(result));
        }
    }

    public void readWebpage(String link) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] { link });

    }

}
