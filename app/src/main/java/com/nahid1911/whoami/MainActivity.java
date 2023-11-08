package com.nahid1911.whoami;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView ipaddress,wifi_ip,mac_id,country,region_id,city_id
                ,latitude_id,longitude_id,zip_code_id,time_zone_id,asn_id,isp_id,is_proxy_id;
    private SwipeRefreshLayout swipeRefreshLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///transparent
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ////dark static bar text
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ////android rotating off
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Fixed portrait orientation

        ipaddress = findViewById(R.id.ipaddress);
        wifi_ip = findViewById(R.id.wifi_id);
        mac_id = findViewById(R.id.mac_ip);
        country = findViewById(R.id.country);
        region_id = findViewById(R.id.region_id);
        city_id = findViewById(R.id.city_id);

        latitude_id = findViewById(R.id.latitude_id);
        longitude_id = findViewById(R.id.longitude_id);
        zip_code_id = findViewById(R.id.zip_code_id);
        time_zone_id = findViewById(R.id.time_zone_id);
        asn_id = findViewById(R.id.asn_id);
        isp_id = findViewById(R.id.isp_id);
        is_proxy_id = findViewById(R.id.is_proxy_id);


        //progressBar...


        ///wifi ip
        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(WIFI_SERVICE);
        String key = (Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));

        ///mac address
        wifi_ip.setText(": "+key);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String mac = wifiInfo.getMacAddress();
        mac_id.setText(": "+mac);

        serverwork();


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Add your code to refresh the content here


                // Example: Simulate a refresh with a delay of 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the refreshing indicator after the task is complete
                        serverwork();
                        ///wifi ip
                        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(WIFI_SERVICE);
                        String key = (Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));

                        ///mac address
                        wifi_ip.setText(": "+key);

                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        String mac = wifiInfo.getMacAddress();
                        mac_id.setText(": "+mac);

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });






    }

    /////////////////////////////////////
    ////////////////////////////////////
    ////////////////////////////////////
    public void serverwork(){

        //////database//////////////////////////////
        String url = "https://nahidbd2003.000webhostapp.com/ipaddress.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
//                        textView.setText(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String ip = jsonObject.getString("ip");
                            String country_name = jsonObject.getString("country_name");
                            String region_name =jsonObject.getString("region_name");
                            String city_name = jsonObject.getString("city_name");
                            String latitude = jsonObject.getString("latitude");
                            String longitude = jsonObject.getString("longitude");
                            String zip_code = jsonObject.getString("zip_code");
                            String time_zone = jsonObject.getString("time_zone");
                            String asn = jsonObject.getString("asn");
                            String as = jsonObject.getString("as");
                            String is_proxy = jsonObject.getString("is_proxy");

                            ipaddress.setText(ip);
                            country.setText(": "+country_name);
                            region_id.setText(": "+region_name);
                            city_id.setText(": "+city_name);

                            latitude_id.setText(": "+latitude);
                            longitude_id.setText(": "+longitude);
                            zip_code_id.setText(": "+zip_code);
                            time_zone_id.setText(": "+time_zone);
                            asn_id.setText(": "+asn);
                            isp_id.setText(": "+as);
                            is_proxy_id.setText(": "+is_proxy);



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error


                    }
                });



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
}