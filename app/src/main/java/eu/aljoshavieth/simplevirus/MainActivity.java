package eu.aljoshavieth.simplevirus;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aljosha Vieth
 * https://aljoshavieth.eu
 */

public class MainActivity extends AppCompatActivity
        implements OnRssLoadListener, AdapterView.OnItemClickListener {

    ListView articleList; //the ListView which displays the articles
    SwipeRefreshLayout swipeRefreshLayout; //enables swipe-down-refresh
    ArrayList < String > listItems = new ArrayList < > (); //stores the entries for the ListView
    ArrayList < Object > articleObjects = new ArrayList < > (); //stores the ArticleObjects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //always call the superclass :)
        setContentView(R.layout.activity_main); //set the contentView
        articleList = (ListView) findViewById(R.id.articleList); //get the articleList from layout

        //get the swipe_refresh from layout :
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        loadFeeds(); //load the RSS-Feed

        //set the RefreshListener
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadFeeds(); //reload the RSS-Feed
                        swipeRefreshLayout.setRefreshing(false); //finish refresh-action
                    }
                }
        );
    }

    //load the RSS-Feed
    private void loadFeeds() {
        String[] urlArr = {
                "http://virus.aj-v.de/rss.php"
        }; //link to RSS-Feed
        new RssReader(MainActivity.this)
                .showDialog(false)
                .urls(urlArr)
                .parse(this);
    }

    //if the RSS-Feed is successfully loaded:
    @Override
    public void onSuccess(List < RssItem > rssItems) {

        listItems.clear(); //clear listItem to prevent duplicated entries
        articleObjects.clear(); //clear articleObjects to prevent duplicated entries

        for (int i = 0; i < rssItems.size(); i++) { //go through all entries

           // String imageUrl = rssItems.get(i).getImageUrl(); //get the ImgUrl

            String title = rssItems.get(i).getTitle(); //get the title
            String url = rssItems.get(i).getLink(); //get the URL
          //  String description = rssItems.get(i).getDescription(); //get the description
            String category = rssItems.get(i).getCategory(); //get the category

            listItems.add(rssItems.get(i).getTitle()); //add all entries to the listItems list

            //create new ArticleObject:
            Article article = new Article("bla", title, url, "bla", category);
            articleObjects.add(article); //add all article to the ArticleObjects list

        }

        //add the articles to ListView:
        ArrayAdapter < String > arrayAdapter = new ArrayAdapter < > (
                MainActivity.this, android.R.layout.simple_list_item_1, listItems);
        articleList.setAdapter(arrayAdapter);
        articleList.setOnItemClickListener(this); //set the OnItemClickListener




    }

    //if the RSS-Feed isn't successfully loaded:
    @Override
    public void onFailure(String message) {
        //show error-message
        Toast.makeText(MainActivity.this, R.string.rss_error, Toast.LENGTH_SHORT).show();
    }

    //handle clicked entry:
    @Override
    public void onItemClick(AdapterView < ? > parent, View view, int position, long id) {

        Article article = (Article) articleObjects.get(position); //get the ArticleObject by pos

        //create a new intent from ArticleActivity:
        Intent intent = new Intent(this, ArticleActivity.class);

        intent.putExtra("Article", article); //hand over the ArticleObject to ArticleActivity
        startActivity(intent); //start ArticleActivity
    }

    //set up the OptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle menu-interaction
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about: //if the about-option is clicked:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent); //start AboutActivity
                return true;
            case R.id.imprint: // if the imprint-option is clicked:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://virus.aj-v.de/impressum.php")); //link to imprint
                startActivity(browserIntent); //open imprint-website in browser
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}