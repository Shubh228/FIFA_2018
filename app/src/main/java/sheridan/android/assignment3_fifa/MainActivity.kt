package sheridan.android.assignment3_fifa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sheridan.android.assignment3_fifa.adapters.EditAdapter
import sheridan.android.assignment3_fifa.fragments.HomeFragment
import sheridan.android.assignment3_fifa.views.EditViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val home = HomeFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.homeFragment, home)
            commit()
        }*/

        val navController = this.findNavController(R.id.NavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)


    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.NavHostFragment)
        return navController.navigateUp()
    }
}