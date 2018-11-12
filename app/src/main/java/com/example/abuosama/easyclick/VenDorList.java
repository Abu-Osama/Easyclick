package com.example.abuosama.easyclick;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.abuosama.easyclick.Model.Config;
import com.example.abuosama.easyclick.Model.ListforVendor;
import com.example.abuosama.easyclick.Model.Listnew;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VenDorList extends AppCompatActivity {

    ArrayList<ListforVendor> halllist;
    LinearLayout linearLayout;
    MyRecyclerAdapter myRecyclerAdapter;
    private String lat1="25.339500";
    private String lon1 ="83.003190";
    private String ven = "1";
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;


    public boolean checkInternet(){
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=manager.getActiveNetworkInfo();

        if(networkInfo==null  ||networkInfo.isConnected()==false) {
            return  false;

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ven_dor_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerviewvender);


//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        lat1 = bundle.getString("lat");
//        lon1 = bundle.getString("lon");


        if (this.checkInternet()) {

            new SendRequest().execute("http://spmnetwork.com/easyclicktoservice/getVenderDetail.php");

        }

        halllist = new ArrayList<ListforVendor>();

        myRecyclerAdapter = new MyRecyclerAdapter(halllist,this);
        recyclerView.setAdapter(myRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    class SendRequest extends AsyncTask<String, Void, String> {

        String line;

        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = ProgressDialog.show(VenDorList.this, "loading",
                    "Please Wait", true, true);

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        protected String doInBackground(String... p1) {

            try {

                URL url = new URL(p1[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("latitude", lat1);
                postDataParams.put("longitude", lon1);
                postDataParams.put("category_id",ven);
                // postDataParams.put("category",category1);

                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb=new StringBuilder();
                line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                Log.d("sb",sb.toString());
                return sb.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
                return "no internet permission";
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_Hall_ok1);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jc = jsonArray.getJSONObject(i);
                    String name = jc.getString(Config.TAG_Hall_Name);
                    String image = jc.getString(Config.TAG_Banner);
                    String price = jc.getString(Config.TAG_Price);
                    String actualprice = jc.getString(Config.TAG_Price_actual);
//                    String halladd = jc.getString(Config.TAG_Address);
//                    String hallprice = jc.getString(Config.TAG_Price);
//                    String hallbanner = jc.getString(Config.TAG_Banner);
//                    String latitude=jc.getString("hall_latitude");
//                    String longitude=jc.getString("hall_longitute");
//                    String emailid=jc.getString("hall_email");
//                    String discount1=jc.getString("hall_price_dis");


                    ListforVendor list = new ListforVendor(name,image,price,actualprice);
                    list.setHallname(name);
                    list.setHallnumber(image);
                    list.setHalladd(actualprice);
                     list.setHallprice(price);
//                    list.setHallimage(hallbanner);
//                    list.setHalllatitude(latitude);
//                    list.setHalllongitude(longitude);
//                    list.setHallemail(emailid);
//                    list.setdiscount(discount1);
                    halllist.add(list);

                }

                myRecyclerAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {


        ArrayList<ListforVendor> halllist=new ArrayList<>();
        Context context;

        public MyRecyclerAdapter(ArrayList<ListforVendor> halllist,Context context) {
            this.halllist = halllist;
            this.context=context;

        }

        @Override
        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = getLayoutInflater().inflate(R.layout.listdesign, parent, false);
            MyRecyclerAdapter.MyViewHolder myViewHolder = new MyRecyclerAdapter.MyViewHolder(v,context,halllist);
            return myViewHolder;
        }


        public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {
            final ListforVendor get = halllist.get(position);
            if(holder!=null){

                holder.hallname.setText(get.getHallname());
                holder.price.setText("\u20B9" +get.getHallprice());
                holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.price1.setText("\u20B9" +get.getHalladd());

                //holder.hallprice.setText("\u20B9" + get.getHallprice());

                Glide.with(VenDorList.this).load(get.getHallnumber())
                        .thumbnail(0.5f)
                        .override(200,200).
                        diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(holder.hallimage);
            }

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Log.d("hii","hellp");



//                    Intent intent = new Intent(getApplication(), LoginActivity.class);
////                    intent.putExtra("add", get.getHalladd());
////                    intent.putExtra("hallname", get.getHallname());
////                    intent.putExtra("mobilenumber", get.getHallnumber());
////                    intent.putExtra("imageslider", get.getHallimage());
////                    intent.putExtra("latidude",get.getHalllatitude());
////                    intent.putExtra("longitude",get.getHalllongitude());
////                    intent.putExtra("emailid",get.getHallemail());
////                    intent.putExtra("hallprice",get.getHallprice());
////                    intent.putExtra("discount",get.getdiscount());
//                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return halllist.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder  {

            public ImageView hallimage;

            public TextView hallname, price, price1, hallnumber,discountprice;
            ArrayList<Listnew> halllist=new ArrayList<>();
            Context context;
            LinearLayout linearLayout;

            public MyViewHolder(View itemView, Context context, ArrayList<ListforVendor> halllist) {

                super(itemView);



                hallimage = (ImageView) itemView.findViewById(R.id.vendorimage);
                hallname = (TextView) itemView.findViewById(R.id.listname);
                price   = itemView.findViewById(R.id.price);
                price1   = itemView.findViewById(R.id.price1);
                linearLayout=itemView.findViewById(R.id.layoutidfor);

            }


        }
    }


}
