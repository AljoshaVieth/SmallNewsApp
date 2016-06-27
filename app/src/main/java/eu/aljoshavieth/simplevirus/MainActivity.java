package eu.aljoshavieth.simplevirus;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRssLoadListener, AdapterView.OnItemClickListener {

    ListView articleList;
    ArrayList<String> listItems = new ArrayList<>();
    HashMap<Integer, String> itemMap = new HashMap<>();
    HashMap<Integer, String> descriptionMap = new HashMap<>();
    public final static String LINK = "eu.aljoshavieth.simplevirus.LINK";
    public final static String TITLE = "eu.aljoshavieth.simplevirus.TITLE";
    public final static String DESCRIPTION = "eu.aljoshavieth.simplevirus.DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleList = (ListView) findViewById(R.id.articleList);
        loadFeeds();
    }

    //load feeds
    private void loadFeeds() {
        //you can also pass multiple urls
        String[] urlArr = {"http://android.aj-v.de/feed.xml"};
        new RssReader(MainActivity.this)
                .showDialog(true)
                .urls(urlArr)
                .parse(this);
    }

    @Override
    public void onSuccess(List<RssItem> rssItems) {

        for(int i = 0; i < rssItems.size(); i++){
            Log.d("SimpleRss2ParserDemo",rssItems.get(i).getTitle());
            listItems.add(rssItems.get(i).getTitle());
            itemMap.put(i, rssItems.get(i).getLink());
            descriptionMap.put(i, rssItems.get(i).getDescription());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                MainActivity.this, android.R.layout.simple_list_item_1, listItems);
        articleList.setAdapter(arrayAdapter);
        articleList.setOnItemClickListener(this);




    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(MainActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String link = itemMap.get(position);
        String title = listItems.get(position);
        String description = descriptionMap.get(position);
        Log.d("LINK ", link);

        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(LINK, link);
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, description);
        Log.d("VIRUS ", title);
        startActivity(intent);

    }
}

