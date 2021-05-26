package com.abdullayev.onlinemagazin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.abdullayev.onlinemagazin.screen.cart.CartFragment
import com.abdullayev.onlinemagazin.screen.favorite.FavoriteFragment
import com.abdullayev.onlinemagazin.screen.home.HomeFragment
import com.abdullayev.onlinemagazin.screen.profil.ProfilFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val homeFragment = HomeFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val profileFragment = ProfilFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //

        viewModel = MainViewModel()
        viewModel.productData.observe(this, Observer {
            viewModel.insertAllProducts2DB(it)
        })
        viewModel.categoryData.observe(this, Observer {
            viewModel.insertAllCategory2DB(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        supportFragmentManager.beginTransaction().add(R.id.main_frame, homeFragment,homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frame, cartFragment,cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frame, favoriteFragment,favoriteFragment.tag).hide(favoriteFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_frame, profileFragment,profileFragment.tag).hide(profileFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.actionHome){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.actionCart){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment).commit()
                activeFragment = cartFragment
            } else if (it.itemId == R.id.actionFavorite){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            } else if (it.itemId == R.id.actionProfile){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                activeFragment = profileFragment
            }
            return@setOnNavigationItemSelectedListener true
        }

        loadData()
    }

    fun loadData(){
        viewModel.getProducts()
        viewModel.getCategories()
    }
}