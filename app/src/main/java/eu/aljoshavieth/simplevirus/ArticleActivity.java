package eu.aljoshavieth.simplevirus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;


/**
 * Created by Aljosha Vieth on 30.05.2016.
 * https://aljoshavieth.eu
 */

public class ArticleActivity extends AppCompatActivity {

   // private TextView articleText;
    //private TextView articleDescription;
    //private TextView articleAuthor;
    private Document doc;
    private String articleTextString;
    //private String author;
    private ProgressDialog mProgressDialog;
    private String link;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //always call the superclass :)
        setContentView(R.layout.article);

        //articleText = (TextView) findViewById(R.id.articleText); //get the articleText from layout
        //articleAuthor = (TextView) findViewById(R.id.articleAuthor); //get the authorText ...
        webView = (WebView) findViewById(R.id.webView);


        //get the articleDescription from layout:
       // articleDescription = (TextView) findViewById(R.id.articleDescription);

        Intent intent = getIntent(); //get Intent
        Article article = (Article) intent.getSerializableExtra("Article"); //get ArticleObject
        link = article.getUrl(); //get article-url
        String title = article.getArticleTitle(); //get the article-title
        String description = article.getDescription(); //get the article-description
        String imageUrl = article.getImageUrl(); //get the image-url

        //load the article-image into the ImageView of article_layout by using Picasso
       // Picasso.with(this).load(imageUrl).placeholder(R.drawable.placeholder)
        //        .into((ImageView) findViewById(R.id.imageView));

        //articleDescription.setText(description); //set articleDescription in layout
        setTitle(title); //set the title of the Activity
        new FetchWebsiteData().execute(); //load the article-text from web


    }


    //load the article-text from web:
    private class FetchWebsiteData extends AsyncTask < Void, Void, Void > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //set ProgressDialog:
            mProgressDialog = new ProgressDialog(ArticleActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        //load the text from web:
        @Override
        protected Void doInBackground(Void...params) {
            try {
                doc = Jsoup.connect(link).get(); //connect to website
                //author = doc.body().getElementsByTag("author").text(); //get author
                articleTextString = doc.body().getElementsByTag("article").html(); //get text

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //set the text and author to layout
        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            if (articleTextString == null) { //if article-text is empty:
                //articleText.setText(R.string.article_load_error); //set the error-text
                mProgressDialog.dismiss(); //terminate the ProgressDialog

                //display error-message in toast:
                Toast.makeText(ArticleActivity.this,
                        R.string.article_load_error, Toast.LENGTH_LONG).show();


                ArticleActivity.super.finish(); //close activity
            } else { //if article-text isn't empty:
                webView.loadDataWithBaseURL(null, articleTextString, "text/html", "utf-8", null);
                //articleText.setText(Html.fromHtml(articleTextString)); //set article-text
               // articleAuthor.setText(author); //set article-author
                mProgressDialog.dismiss(); //terminate the ProgressDialog
            }
        }
    }

}