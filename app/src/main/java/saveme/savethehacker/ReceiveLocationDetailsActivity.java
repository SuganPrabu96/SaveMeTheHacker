package saveme.savethehacker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import util.ServiceHandler;


public class ReceiveLocationDetailsActivity extends FragmentActivity implements OnMapReadyCallback{

    MapFragment mapFragment;
    public static Handler receiveLocationHandler;
    public static final String receiveLocationURL = "http://noteshare.in/";
    public static String receiveLocationDetailsReturnedJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_receive_location_details);

        new ReceiveLocation().execute("a");

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

    }

    public static class ReceiveLocation extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected Void doInBackground(String... params) {

            List<NameValuePair> paramsList = new ArrayList<>();

            paramsList.add(new BasicNameValuePair("",""));
            ServiceHandler jsonParser = new ServiceHandler();
            receiveLocationDetailsReturnedJSON = jsonParser.makeServiceCall(receiveLocationURL,ServiceHandler.POST,paramsList);

            if(receiveLocationDetailsReturnedJSON!=null){

                try{
                    Log.i("Receive Loc Details", "Successful");
                    Message msg = new Message();
                    msg.arg1 = 1;

                    Bundle b = new Bundle();
                    double[] d= new double[]{0.0,0.0}; //TODO set the values as lat and long
                    b.putDoubleArray("Location", d);
                    msg.setData(b);

                    receiveLocationHandler.sendMessage(msg);
                }catch (Exception e){
                    Log.i("Receive Loc Details","Failure");
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_location_details, menu);
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
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(true);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0.0000,0.0000)));

        receiveLocationHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {

                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(msg.getData().getDoubleArray("Location")[0],msg.getData().getDoubleArray("Location")[1])));
                }
            }
        };
    }

}
