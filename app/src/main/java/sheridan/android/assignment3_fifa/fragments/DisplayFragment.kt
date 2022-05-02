package sheridan.android.assignment3_fifa.fragments

import android.os.Bundle
import android.view.*
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.adapters.DisplayAdapter
import sheridan.android.assignment3_fifa.data.TeamDatabase
import sheridan.android.assignment3_fifa.data.TeamEntity
import sheridan.android.assignment3_fifa.views.DisplayViewModel
//import org.jetbrains.anko.doAsync
//import org.jetbrains.anko.uiThread

class DisplayFragment : Fragment() {

    private  var teamList:List<TeamEntity> = listOf()
    private lateinit var tDb: TeamDatabase
    private lateinit var display: RecyclerView
    private lateinit var teamArray:ArrayList<DisplayViewModel>
    private lateinit var radio: RadioGroup
    lateinit var imageId: Array<Int>
    var points: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_display, container, false)
        radio = v.findViewById(R.id.radioGroup)
        tDb = TeamDatabase.getInstance(requireContext())
        imageId = arrayOf(R.drawable.brazil)
        display = v.findViewById(R.id.displayView)
        teamArray = ArrayList<DisplayViewModel>()

        radio.setOnCheckedChangeListener{radio, i ->
            when(i){
                R.id.radioName->{
                    teamArray.clear()
                    GlobalScope.launch(Dispatchers.Default) {
                        teamList = tDb.teamDao()
                            .getNameData()
                        launch(Dispatchers.Main) {
                            getDisplayData(teamList)
                        }
                    }
                }
                R.id.radioContinent->{
                    teamArray.clear()
                    GlobalScope.launch(Dispatchers.Default) {
                        teamList = tDb.teamDao()
                            .getContinentData()
                        launch(Dispatchers.Main) {
                            getDisplayData(teamList)
                        }
                    }
                }
                R.id.radioPoints->{
                    teamArray.clear()
                    GlobalScope.launch(Dispatchers.Default) {
                        teamList = tDb.teamDao()
                            .getPoints()
                        launch(Dispatchers.Main) {
                            getDisplayData(teamList)
                        }
                    }
                }
            }
        }
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.home_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    fun getDisplayData(teamList: List<TeamEntity>){
        var img: Int = 0
        for (t in teamList) {
            when(t.teamName){
                "Brazil"-> img = R.drawable.brazil
                "Croatia"->img = R.drawable.croatia
                "England"->img = R.drawable.england
                "France"->img = R.drawable.france
                "Russia"->img = R.drawable.russia
                "Sweden"-> img =R.drawable.sweden
                "Uruguay"->img = R.drawable.uruguay
                else -> img = R.drawable.cup_img
            }
            val team = DisplayViewModel(img, t.teamName, t.continentName, t.playedGames, t.wonGames, t.lostGames, t.drawGames, (t.wonGames * 3)+ t.drawGames)
            teamArray.add(team)
        }
        Toast.makeText(
            activity,
            "${teamArray.size} teams found.",
            Toast.LENGTH_SHORT
        ).show()
        display.adapter = DisplayAdapter(teamArray)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        display.layoutManager = LinearLayoutManager(activity)
    }
}