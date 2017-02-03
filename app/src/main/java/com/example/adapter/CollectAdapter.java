package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bean.CollectBean;
import com.example.bean.CurrentNews;
import com.example.weidu.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder>
{
    List<CurrentNews> list;
    Context context;
    OnItemClickListener mOnItemClickListener;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_YES=1;

   /* private final int TYPE_item = 1;
    private final int TYPE_FOOT = 2;
    private View footView;
    private int realListSize;*/

    public CollectAdapter(Context context,List<CurrentNews> list)
    {
        this.context=context;
        this.list=list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        ViewHolder viewHolder;
        if (viewType==VIEW_TYPE_YES)
        {
            view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            viewHolder= new ViewHolder(view);
        }
        else
        {
            view= LayoutInflater.from(context).inflate(R.layout.empty,parent,false);
            viewHolder= new EmptyViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        int viewtype=getItemViewType(position);
        if (viewtype==VIEW_TYPE_YES)
        {
            Glide.with(context).load(list.get(position).getImg()).into(holder.imageView);
            holder.textView.setText(list.get(position).getTitle());
        }


    }

    @Override
    public int getItemCount()
    {
        if (list.size()==0)
        {
            return 1;
        }
        else
        {
            return list.size();
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        if (list.size()==0)
        {
            return VIEW_TYPE_EMPTY;
        }
        else
        {
            return VIEW_TYPE_YES;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView=(ImageView)itemView.findViewById(R.id.newsurl);
            textView=(TextView)itemView.findViewById(R.id.newstitle);
        }


        @Override
        public void onClick(View view)
        {
            if (mOnItemClickListener!=null)
            {
                mOnItemClickListener.onItemClick(view,getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

     class EmptyViewHolder extends ViewHolder
    {
        public TextView emptyview;
        public EmptyViewHolder(View itemView)
        {
            super(itemView);
            emptyview=(TextView)itemView.findViewById(R.id.empty_view);
        }
    }




   /* public void setFootView(View foot)
    {
        this.footView = foot;
        footGone();
    }
    public void footGone()
    {
        if(footView == null){
            return;
        }
        footView.setVisibility(View.GONE);
    }
    public void footVisible()
    {
        if(footView == null){
            return;
        }
        footView.setVisibility(View.VISIBLE);
    }

    public void binddata(List<CollectBean> list)
    {
        this.list=list;
    }*/

  /*  @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        *//*if(viewType == TYPE_FOOT && footView !=null)
        {
            //当viewType是TYPE_FOOT并且footView不为空的时候，就可以显示FootView了。
            return new FootViewHolder(footView);
        }*//*
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        DataViewHolder viewHolder=new DataViewHolder(view);
        return viewHolder;
    }*/



 /*   @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
    *//*    if (holder instanceof  DataViewHolder)
        {
            Glide.with(context).load(list.get(position).getImg()).into(((DataViewHolder)holder).imageView);
            ((DataViewHolder)holder).textView.setText(list.get(position).getTitle());
        }*//*
    }*/

  /*  @Override
    public int getItemViewType(int position)
    {
        //如果是最后一条数据，就返回FootView类型
        if(position+1 == getItemCount())
        {
            return TYPE_FOOT;
        }
        return TYPE_item ;
    }*/

/*    @Override
    public int getItemCount()
    {
       *//* if(list==null)
        {
            return 0;
        }
        //如果footView为空的话，就是获取list.size()个数据。
        realListSize = (footView == null ? list.size() : list.size()+1);
        return realListSize;*//*
        return list.size();
    }*/

   /*  class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.newsurl);
            textView=(TextView)itemView.findViewById(R.id.newstitle);
        }

    }*/


    /*class FootViewHolder extends RecyclerView.ViewHolder
    {
        public FootViewHolder(View itemView)
        {
            super(itemView);
        }
    }
*/
}
