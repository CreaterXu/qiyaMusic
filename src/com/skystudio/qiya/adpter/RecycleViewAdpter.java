package com.skystudio.qiya.adpter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.R;
import com.skystudio.qiya.pojo.Friend;
import com.skystudio.qiya.pojo.Message;
import com.skystudio.qiya.pojo.Share;
import com.skystudio.qiya.ui.activity.ShowActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/27.
 * <p/>
 * RecycleView通用适配器
 */
public class RecycleViewAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private FragmentActivity context;
    private ArrayList<?> data = null;
    private ArrayList<Share> shares = null;//动态数据
    private ArrayList<Friend> friends = null;//通讯录数据
    private ArrayList<Message> messages = null;//推荐数据

    private int whichViewHolderFlag = 0;//决定那个card viewholder

    public RecycleViewAdpter(FragmentActivity context, ArrayList<?> data
            , int whichViewHolderFlag) {
        this.context = context;
        this.whichViewHolderFlag = whichViewHolderFlag;
        mLayoutInflater = LayoutInflater.from(context);
        this.data = data;
        if (whichViewHolderFlag == Contans.RECONMMEND_FRAGMENT) {
            this.messages = (ArrayList<Message>) data;
        } else if (whichViewHolderFlag == Contans.CONTACTERS_FRAGMENT) {
            this.friends = (ArrayList<Friend>) data;
        } else if (whichViewHolderFlag == Contans.ZONE_FRAGMENT) {
            this.shares = (ArrayList<Share>) data;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (whichViewHolderFlag == Contans.RECONMMEND_FRAGMENT) {
            viewHolder = new ReconmmendViewHolder(mLayoutInflater.inflate(R.layout.card_reconmmend, parent, false));
        } else if (whichViewHolderFlag == Contans.CONTACTERS_FRAGMENT) {
            viewHolder = new FriendsViewHolder(mLayoutInflater.inflate(R.layout.card_friends, parent, false));
        } else if (whichViewHolderFlag == Contans.ZONE_FRAGMENT) {
            viewHolder = new ZoneViewHolder(mLayoutInflater.inflate(R.layout.card_zone, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (whichViewHolderFlag == Contans.RECONMMEND_FRAGMENT) {
            ReconmmendViewHolder reconmmendViewHolder = (ReconmmendViewHolder) holder;
            bindReconmmend(reconmmendViewHolder, position);
        } else if (whichViewHolderFlag == Contans.CONTACTERS_FRAGMENT) {
            FriendsViewHolder friendsViewHolder = (FriendsViewHolder) holder;
            bindFriends(friendsViewHolder, position);
        } else if (whichViewHolderFlag == Contans.ZONE_FRAGMENT) {
            ZoneViewHolder zoneViewHolder = (ZoneViewHolder) holder;
            bindZone(zoneViewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 推荐消息ViewHolder类
     */
    public class ReconmmendViewHolder extends RecyclerView.ViewHolder {
        TextView recommendMusicText;
        TextView recommendDetailText;
        TextView dateText;
        ImageView headImage;
        RelativeLayout viewLayout;
        public ReconmmendViewHolder(View view) {
            super(view);
            viewLayout=(RelativeLayout)view.findViewById(R.id.reconmmend_card_layout);
            viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("xv","on click");
                    Intent in=new Intent(context,ShowActivity.class);
                    context.startActivity(in);
                }
            });
            recommendMusicText = (TextView) view.findViewById(R.id.recommend_music);
            recommendDetailText = (TextView) view.findViewById(R.id.recommend_detail);
            dateText = (TextView) view.findViewById(R.id.date_text);
            headImage = (ImageView) view.findViewById(R.id.head_pic);

        }
    }

    /**
     * 动态ViewHolder类
     */
    public class ZoneViewHolder extends RecyclerView.ViewHolder {
        TextView recommendMusicText;
        TextView recommendDetailText;
        TextView dateText;
        ImageView headImage;

        public ZoneViewHolder(View view) {
            super(view);
            recommendMusicText = (TextView) view.findViewById(R.id.recommend_music);
            recommendDetailText = (TextView) view.findViewById(R.id.recommend_detail);
            dateText = (TextView) view.findViewById(R.id.date_text);
            headImage = (ImageView) view.findViewById(R.id.head_pic);
        }
    }


    /**
     * 通讯录ViewHolder类
     */
    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        TextView recommendMusicText;
        TextView recommendDetailText;
        TextView dateText;
        ImageView headImage;

        public FriendsViewHolder(View view) {
            super(view);
            recommendMusicText = (TextView) view.findViewById(R.id.recommend_music);
            recommendDetailText = (TextView) view.findViewById(R.id.recommend_detail);
            dateText = (TextView) view.findViewById(R.id.date_text);
            headImage = (ImageView) view.findViewById(R.id.head_pic);
        }
    }


    /**
     * 绑定推荐holder
     */
    public void bindReconmmend(ReconmmendViewHolder viewHolder, int position) {



    }

    /**
     * 绑定动态holder
     */
    public void bindZone(ZoneViewHolder viewHolder, int position) {
    }

    /**
     * 绑定通讯录holder
     */
    public void bindFriends(FriendsViewHolder viewHolder, int position) {
    }
}
