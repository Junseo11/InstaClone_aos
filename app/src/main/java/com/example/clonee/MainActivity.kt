package com.example.clonee

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.clonee.databinding.ActivityMainBinding
import com.example.clonee.navigation.GridFrag
import com.example.clonee.navigation.alarmFrag
import com.example.clonee.navigation.datailViewFragment
import com.example.clonee.navigation.userfrag
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener


class MainActivity : AppCompatActivity(){


    var viewList = ArrayList<View>()
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewList.add(layoutInflater.inflate(R.layout.alarm,null))
        viewList.add(layoutInflater.inflate(R.layout.detailview,null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_detail,null))
        viewList.add(layoutInflater.inflate(R.layout.grid,null))
        viewList.add(layoutInflater.inflate(R.layout.user,null))

        binding.viewpager.adapter = pagerAdapter()

        binding.viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when(position){
                    0 -> binding.bottomNavigation.selectedItemId = R.id.action_home
                    1->  binding.bottomNavigation.selectedItemId = R.id.action_fav
                    2-> binding.bottomNavigation.selectedItemId = R.id.action_account

                }
            }


        })






    }

    inner class pagerAdapter :PagerAdapter(){
        override fun getCount() = viewList.size

        override fun isViewFromObject(view: View, `object`: Any)=view == `object`

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var curview = viewList[position]
            binding.viewpager.addView(curview)
            return curview
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            binding.viewpager.removeView(`object` as View)
        }

    }



}
