package com.example.anku.minor2;

import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class After extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    private ListView listView;


    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS =2 ;
    private static String TAG = "MyActivity";
    private BeaconManager mBeaconManager;
    static List<String> list = new ArrayList<String>();
    static List<Boolean> list1 = new ArrayList<Boolean>();


    ListViewAdapter adapter;

    ArrayAdapter<String> listadapter;

    String items[] = {"Clothes","Handbags","Shoes","Crockery","Food and Beverages","Others"};

    Integer[] imgid = {
            R.drawable.ic_shopping_basket_black_48dp,
            R.drawable.ic_shopping_basket_black_48dp,
            R.drawable.ic_shopping_basket_black_48dp,
            R.drawable.ic_shopping_basket_black_48dp,

            R.drawable.ic_shopping_basket_black_48dp,

            R.drawable.ic_shopping_basket_black_48dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);


        listView = (ListView) findViewById(R.id.list1);

        adapter = new ListViewAdapter(After.this, items, imgid);
        //Assign Above Array Adapter to ListView
        listView.setAdapter(adapter);


        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the URL frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
        mBeaconManager.bind(this);

        //Create ListView Item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), items[position], Toast.LENGTH_LONG).show();


            }
        });



    }

    @Override
    public void onBeaconServiceConnect() {  Region region = new Region("all-beacons-region", null, null, null);
        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

        for (Beacon beacon: beacons) {

            byte[] a;

            a = beacon.getId1().toByteArray();

            String url = UrlBeaconUrlCompressor.uncompress(a);
            Log.d("dede", "I see a beacon transmitting a url: " + url+ "   " + " approximately " +  " meters away.");
           /* String n;

            if(url.contains(".com"))
                n= (String) url.subSequence(url.indexOf("https")+8,url.indexOf(".com"));
            else
                n= (String) url.subSequence(url.indexOf("http")+8,23);
*/
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
            mBuilder.setSmallIcon(R.drawable.com_facebook_profile_picture_blank_portrait);
            mBuilder.setContentTitle("Near Buy");
            mBuilder.setContentText("shopping here come come");

            int mNotificationId = 001;
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(alarmSound);
            NotificationManager mNotifyMgr =
                    (NotificationManager) After.this.getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
         //   list.add(n);
           // list1.add(Boolean.TRUE);


        }



    }
}
