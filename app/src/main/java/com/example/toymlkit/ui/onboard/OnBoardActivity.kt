package com.example.toymlkit.ui.onboard

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.toymlkit.R
import com.example.toymlkit.base.BaseActivity
import com.example.toymlkit.databinding.ActivityOnboardBinding
import com.example.toymlkit.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardActivity : BaseActivity<ActivityOnboardBinding>(R.layout.activity_onboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {

        val fragmentList = listOf(Content1Fragment(), Content2Fragment())

        val pagerAdapter = FragmentPagerAdapter(fragmentList, this)

        with(binding) {
            viewpager.adapter = pagerAdapter
            viewpager.offscreenPageLimit = fragmentList.size
            viewpager.setPadding(150, 0, 150, 0)
            viewpager.registerOnPageChangeCallback(onPageChangeCallbackListener)

            indicator.setViewPager(viewpager)
            indicator.createIndicators(fragmentList.size, INDICATOR_INIT_POSITION)
        }
    }


    private val onPageChangeCallbackListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.indicator.animatePageSelected(position)
            if(position != INDICATOR_INIT_POSITION){
                binding.routeMain.visibility = View.VISIBLE
            }else{
                binding.routeMain.visibility = View.INVISIBLE
            }
        }
    }

    fun routeHome(view : View){
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }


    companion object {
        private const val INDICATOR_INIT_POSITION = 0
    }

}

class FragmentPagerAdapter(
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}