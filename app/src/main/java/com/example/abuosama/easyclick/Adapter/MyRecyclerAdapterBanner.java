package com.example.abuosama.easyclick.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.abuosama.easyclick.BannerFragment;
import com.example.abuosama.easyclick.Model.ListForBanner;
import com.example.abuosama.easyclick.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MyRecyclerAdapterBanner extends RecyclerView.Adapter<MyRecyclerAdapterBanner.MyViewHolder> {


    ArrayList<ListForBanner> bannerlist=new ArrayList<>();
    Context context;
    private final OnItemClickListener listener;

    public MyRecyclerAdapterBanner(ArrayList<ListForBanner> bannerlist, OnItemClickListener listener) {
        this.bannerlist = bannerlist;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ListForBanner item);
    }

    @Override
    public MyRecyclerAdapterBanner.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rownewdesignbanner, parent, false);
        MyRecyclerAdapterBanner.MyViewHolder myViewHolder = new MyRecyclerAdapterBanner.MyViewHolder(v,context,bannerlist);
        return myViewHolder;
    }



    public void onBindViewHolder(MyRecyclerAdapterBanner.MyViewHolder holder, final int position) {
        final ListForBanner get = bannerlist.get(position);
        holder.bind(bannerlist.get(position),listener);
        final String ven = "1";
//        if(holder!=null){
//
//
//            //holder.halladd.setText(get.getHalladd());
//            //holder.hallprice.setText("\u20B9" + get.getHallprice());
//
//            Glide.with(context).load(get.getHallnumber())
//                    .thumbnail(0.5f)
//                    .override(200,200).
//                    diskCacheStrategy(DiskCacheStrategy.ALL).
//                    into(holder.hallimage);
//        }
//


            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(getContext(), VenDorList.class);
////                    intent.putExtra("name", get.getHallname());
////                    intent.putExtra("n", get.getHallnumber());
////                    intent.putExtra("vendor",ven);
//                    startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return bannerlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView hallimage;

        public TextView hallname, halladd, hallprice, hallnumber,discountprice;
        ArrayList<ListForBanner> halllist=new ArrayList<>();
        Context context;
        LinearLayout linearLayout;
        // @BindView(R.id.ratingbar)
        RatingBar rating;
        double ratingi = 3.0;

        public MyViewHolder(View itemView, Context context, ArrayList<ListForBanner> halllist) {

            super(itemView);
            ButterKnife.bind(itemView);


            this.halllist=halllist;
            this.context=context;

            hallimage = (ImageView) itemView.findViewById(R.id.hallimageview1);
            linearLayout=itemView.findViewById(R.id.bannerlayoutid);


        }

        public void bind(final ListForBanner item, final OnItemClickListener listener) {

            Glide.with(itemView.getContext()).load(item.getHallnumber()).into(hallimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }


    }

}

