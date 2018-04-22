package com.sh.ethan.sh_loveandroid.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sh.ethan.sh_loveandroid.R;
import com.sh.ethan.sh_loveandroid.beans.Home_News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ethan on 2018/4/22.
 */
public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeNews_RecyclerViewHolder> {
    private Context context;
    private OnClickToReadMore onClickToReadMore;
    private List<Home_News> home_news;

    public HomeNewsAdapter(Context context, List<Home_News> home_news, OnClickToReadMore onClickToReadMore) {
        this.context = context;
        this.onClickToReadMore = onClickToReadMore;
        this.home_news = home_news;
    }

    @Override
    public HomeNews_RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeNews_RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.home_news_recycler_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeNews_RecyclerViewHolder holder, final int position) {
        final Home_News home_new = home_news.get(position);
        holder.author.setText(home_new.getAuthor());
        holder.news_title.setText(home_new.getTitle());
        if (!home_new.getSuperChapterName().isEmpty()) {
            holder.superChapter.setVisibility(View.VISIBLE);
            holder.superChapter.setText(home_new.getSuperChapterName());
        } else {
            holder.superChapter.setVisibility(View.GONE);
        }

        if (!home_new.getChapterName().isEmpty()) {
            holder.chapter.setVisibility(View.VISIBLE);
            holder.chapter.setText(home_new.getChapterName());
        } else {
            holder.chapter.setVisibility(View.GONE);
        }

        holder.zan.setText(home_new.getZan() + "");
        holder.niceDate.setText(home_new.getNiceDate());
        if (!home_new.getEnvelopePic().isEmpty()) {
            Picasso.with(context).load(home_new.getEnvelopePic()).placeholder(R.mipmap.loadding_hor).error(R.mipmap.news_no_img).into(holder.envelopePic);
        }
        holder.news_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToReadMore.toReadMore(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return home_news == null ? 0 : home_news.size();
    }

    public static class HomeNews_RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView author, news_title, superChapter, chapter, zan, niceDate;
        RoundedImageView envelopePic;
        CardView news_item;

        HomeNews_RecyclerViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.author);
            news_title = (TextView) view.findViewById(R.id.news_title);
            superChapter = (TextView) view.findViewById(R.id.superChapter);
            chapter = (TextView) view.findViewById(R.id.chapter);
            zan = (TextView) view.findViewById(R.id.zan);
            niceDate = (TextView) view.findViewById(R.id.niceDate);
            envelopePic = (RoundedImageView) view.findViewById(R.id.envelopePic);
            news_item = (CardView) view.findViewById(R.id.news_item);
        }
    }

    public interface OnClickToReadMore {
        void toReadMore(int position);
    }


    /**
     * 填充数据，刷新
     *
     * @param list
     */
    public void setDataList(List<Home_News> list) {
        this.home_news.clear();
        this.home_news.addAll(list);
        notifyDataSetChanged();
    }
}
