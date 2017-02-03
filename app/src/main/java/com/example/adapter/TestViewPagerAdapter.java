package com.example.adapter;

import java.util.List;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

public class TestViewPagerAdapter extends FragmentPagerAdapter {  
	  private List<Fragment> fragments;
	  private FragmentManager fm;

	
   public TestViewPagerAdapter(FragmentManager fm,List<Fragment> myfragments)
    {  
        super(fm);  
       
        this.fm=fm;
        this.fragments=myfragments; 
    }
  
    

   
    
    /*
    public TestViewPagerAdapter(FragmentManager fm,List<String> list)
    {  
        super(fm);  
      this.list=list;
       
        
    } */
  
    @Override  
    public Fragment getItem(int position) 
    {  
    	
    	return fragments.get(position);
     }  
    
    
 
    
    
  
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return fragments.size();  
    }  
    
    
    
    
    
    
  /* @Override
    public int getItemPosition(Object object)
    {
    	return POSITION_NONE;
    }
    
    public void setFragments(List<Fragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }
	
	
    
    
	public void appendList(List<Fragment> fragment) {
        fragments.clear();
        if (!fragments.containsAll(fragment) && fragment.size() > 0) {
            fragments.addAll(fragment);
        }
        notifyDataSetChanged();
    }*/
   
	
/*	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    
        super.destroyItem(container, position, object);
    }*/

   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
        
        Object obj = super.instantiateItem(container, position);
        return obj;
    }*/
  
  
   
    
}  

