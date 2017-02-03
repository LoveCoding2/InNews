package com.example.weidu;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CollectAdapter;
import com.example.bean.CollectBean;
import com.example.bean.CurrentNews;
import com.example.util.AdvanceDecoration;
import com.example.util.ConfigManage;
import com.example.util.DefaultItemTouchHelpCallback;
import com.example.util.RealmHelper;


import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RealmHelper realmHelper;
    ImageView back;
    List<CurrentNews> list=new ArrayList<>();
    CollectAdapter collectAdapter;
    private int page = 1;
    private Handler handler;
    private Runnable runnable;
    //SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    LinearLayoutManager manager;
    private int lastItem;
    private int pageNum=1;
    private View footView;
    private boolean mIsRefreshing=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initdata();
        initevent();
    }

    public void initdata()
    {
        //swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        realmHelper=new RealmHelper(this);
        back=(ImageView)findViewById(R.id.collect_back);
       /* swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));*/


     /*  list.clear();
        list.addAll(realmHelper.queryAllCollect());*/
        recyclerView=(RecyclerView)findViewById(R.id.recycleview);
        manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分隔线
        recyclerView.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL));
        //getList(1);
        list=realmHelper.queryAllCollect();
        collectAdapter=new CollectAdapter(CollectActivity.this,list);
        recyclerView.setAdapter(collectAdapter);
        collectAdapter.notifyDataSetChanged();



   /*   footView = LayoutInflater.from(this).inflate(R.layout.recycler_foot,recyclerView,false);
        collectAdapter.setFootView(footView);*/
        //recycle事件
        //initRecycleEvent();
        //滑动删除数据
        setSwipeDelete();
    }

    public void initevent()
    {
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        collectAdapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent intent=new Intent(CollectActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("mynews", list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }



   /* protected void loadMore()
    {
        page++;
        setList();

    }*/

/*

    private void setList()
    {
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                int start = 10 * (page - 1);
                CollectBean collectBean=realmHelper.queryCollectById(ConfigManage.getInstance().getString("myid"));
                for (int i = start; i < page * 10; i++)
                {
                    list.add(collectBean);
                }
                collectAdapter.notifyDataSetChanged();
                recyclerView.setPullLoadMoreCompleted();
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 500);


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (handler != null)
        {
            handler.removeCallbacks(runnable);
        }

    }
*/



    private void setSwipeDelete()
    {
        DefaultItemTouchHelpCallback mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition)
            {
                //ConfigManage.getInstance().setBool("collectmode",realmHelper.isCollectExist(list.get(adapterPosition).getId()));
                //删除数据库数据
                realmHelper.deleteCollect(list.get(adapterPosition).getId());
                //滑动删除
                list.remove(adapterPosition);
                collectAdapter.notifyItemRemoved(adapterPosition);

            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition)
            {
                return false;
            }
        });
        mCallback.setDragEnable(false);
        mCallback.setSwipeEnable(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

   /* public void initRecycleEvent()
    {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mIsRefreshing)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                list.clear();
                getList(1);
                *//*new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //list=realmHelper.queryAllCollect();

                       list.addAll(realmHelper.queryAllCollect());
                        collectAdapter.notifyDataSetChanged();
                        //collectAdapter.addMoreItem(list);
                       swipeRefreshLayout.setRefreshing(false);

                    }
                }, 1000);*//*
            }
        });

        //RecyclerView滑动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if(lastItem+1 == collectAdapter.getItemCount() && newState == RecyclerView.SCROLL_STATE_IDLE)
                {

                    //最后一个item并且还有下滑的趋势
                    if(lastItem+1 <= list.size())
                    {
                        new Handler( ).postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                getList(pageNum);
                                //显示底部加载信息
                                collectAdapter.footVisible();
                            }
                        },1000);
                       *//* getList(pageNum);
                        //显示底部加载信息
                        collectAdapter.footVisible();*//*
                    }
                    else
                    {
                        TextView loadMoreTv = (TextView) footView.findViewById(R.id.loadMore_tv);
                        ProgressBar loadMorePb = (ProgressBar) footView.findViewById(R.id.loadMore_pb);
                        loadMorePb.setVisibility(View.GONE);
                        loadMoreTv.setText("没有更多数据");
                    }
                }

           *//* if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==collectAdapter.getItemCount())
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            for (int i = 0; i < 5; i++)
                            {
                              // list=realmHelper.queryAllCollect();
                                collectAdapter.addMoreItem(list);
                                list.clear();
                                list.addAll(realmHelper.queryAllCollect());
                                collectAdapter.notifyDataSetChanged();
                            }

                        }
                    },1000);
                }*//*
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                //lastVisibleItem =manager.findLastVisibleItemPosition();
                lastItem =manager.findLastVisibleItemPosition();
            }
        });
    }*/

/*
    public void getList(int pageNo)
    {
        pageNum = pageNo;
        int pageCount=10;   //每页显示10条数据。
        for(int i=(pageNo-1)*pageCount;i<pageCount * pageNo;i++)
        {
            if(i ==realmHelper.queryAllCollect().size())
            {
                return;
            }
            list.clear();
           list.addAll(realmHelper.queryAllCollect());
           // collectAdapter.notifyDataSetChanged();
        }
        pageNum++;
        //collectAdapter.binddata(list);
       // collectAdapter.notifyDataSetChanged();
        //数据更新以后，刷新条消失
        swipeRefreshLayout.setRefreshing(false);
    }*/


}
