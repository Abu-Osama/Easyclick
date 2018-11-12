package com.example.abuosama.easyclick;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abuosama.easyclick.Adapter.MyRecyclerAdapterBanner;
import com.example.abuosama.easyclick.Model.Config;
import com.example.abuosama.easyclick.Model.ListForBanner;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class BannerFragment extends Fragment implements MyRecyclerAdapterBanner.OnItemClickListener {


    ArrayList<ListForBanner> bannerlist;
    LinearLayout linearLayout;
    private MyRecyclerAdapterBanner myRecyclerAdapter;
    String lat1 = "25.3176";
    String lon1 = "82.9739";
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private MyRecyclerAdapterBanner.OnItemClickListener listener;

    public boolean checkInternet(){
        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=manager.getActiveNetworkInfo();

        if(networkInfo==null  ||networkInfo.isConnected()==false) {
            return  false;

        }

        return true;
    }


    public BannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_banner, container, false);
        recyclerView = v.findViewById(R.id.recyclerviewbanner);


//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        lat1 = bundle.getString("lat");
//        lon1 = bundle.getString("lon");
        // lat1 = getArguments().getString("lat");
        // lon1 = getArguments().getString("lon");

        if (this.checkInternet()) {

            new SendRequest().execute("http://spmnetwork.com/easyclicktoservice/banners.php");

        }

        bannerlist = new ArrayList<ListForBanner>();
        //till here

//        recyclerView.setAdapter(new MyRecyclerAdapterBanner(bannerlist,
//                new MyRecyclerAdapterBanner.OnItemClickListener() {
//                    @Override public void onItemClick(ListForBanner item) {
//                        Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
//                    }
//                }));

       myRecyclerAdapter = new MyRecyclerAdapterBanner(bannerlist, listener);
        recyclerView.setAdapter(myRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        return v;
    }

    @Override
    public void onItemClick(ListForBanner item) {


    }


    class SendRequest extends AsyncTask<String, Void, String> {

        String line;

        protected void onPreExecute() {

            super.onPreExecute();

        }

        protected String doInBackground(String... p1) {

            try {

                URL url = new URL(p1[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("latitude", lat1);
                postDataParams.put("longitude", lon1);
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

            try {

                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_Hall_ok);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jc = jsonArray.getJSONObject(i);
                    String name = jc.getString(Config.TAG_Banner_Name);
                    String image = jc.getString(Config.TAG_Banner_image);
//                    String halladd = jc.getString(Config.TAG_Address);
//                    String hallprice = jc.getString(Config.TAG_Price);
//                    String hallbanner = jc.getString(Config.TAG_Banner);
//                    String latitude=jc.getString("hall_latitude");
//                    String longitude=jc.getString("hall_longitute");
//                    String emailid=jc.getString("hall_email");
//                    String discount1=jc.getString("hall_price_dis");


                    ListForBanner list = new ListForBanner(name,image);
                    list.setHallname(name);
                    list.setHallnumber(image);
//                    list.setHalladd(halladd);
//                    list.setHallprice(hallprice);
//                    list.setHallimage(hallbanner);
//                    list.setHalllatitude(latitude);
//                    list.setHalllongitude(longitude);
//                    list.setHallemail(emailid);
//                    list.setdiscount(discount1);
                    bannerlist.add(list);

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


//    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
//
//
//        ArrayList<ListForBanner> halllist=new ArrayList<>();
//        Context context;
//
//        public MyRecyclerAdapter(ArrayList<ListForBanner> halllist, Context context) {
//            this.halllist = halllist;
//            this.context=context;
//
//        }
//
//        @Override
//        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            View v = getLayoutInflater().inflate(R.layout.rownewdesignbanner, parent, false);
//            MyRecyclerAdapter.MyViewHolder myViewHolder = new MyRecyclerAdapter.MyViewHolder(v,context,halllist);
//            return myViewHolder;
//        }
//
//
//        public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {
//            final ListForBanner get = halllist.get(position);
//            final String ven = "1";
//            if(holder!=null){
//
//
//                //holder.halladd.setText(get.getHalladd());
//                //holder.hallprice.setText("\u20B9" + get.getHallprice());
//
//                Glide.with(BannerFragment.this).load(get.getHallnumber())
//                        .thumbnail(0.5f)
//                        .override(200,200).
//                        diskCacheStrategy(DiskCacheStrategy.ALL).
//                        into(holder.hallimage);
//            }
//
////            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////
////                    Intent intent = new Intent(getContext(), VenDorList.class);
//////                    intent.putExtra("name", get.getHallname());
//////                    intent.putExtra("n", get.getHallnumber());
//////                    intent.putExtra("vendor",ven);
////                    startActivity(intent);
////                }
////            });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return halllist.size();
//        }
//
//        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//            public ImageView hallimage;
//
//            public TextView hallname, halladd, hallprice, hallnumber,discountprice;
//            ArrayList<ListForBanner> halllist=new ArrayList<>();
//            Context context;
//            LinearLayout linearLayout;
//            // @BindView(R.id.ratingbar)
//            RatingBar rating;
//            double ratingi = 3.0;
//
//            public MyViewHolder(View itemView, Context context, ArrayList<ListForBanner> halllist) {
//
//                super(itemView);
//                ButterKnife.bind(itemView);
//
//                itemView.setOnClickListener(this);
//                this.halllist=halllist;
//                this.context=context;
//
//                hallimage = (ImageView) itemView.findViewById(R.id.hallimageview1);
//                //linearLayout=itemView.findViewById(R.id.layoutidfordetail);
//
//
//            }
//
//            @Override
//            public void onClick(View view) {
//                int position=getAdapterPosition();
//                ListForBanner listnew=this.halllist.get(position);
//                Toast.makeText(context, ""+listnew.getHallname(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
