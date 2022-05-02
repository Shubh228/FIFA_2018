package sheridan.android.assignment3_fifa.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.adapters.EditAdapter
import sheridan.android.assignment3_fifa.data.TeamDatabase
import sheridan.android.assignment3_fifa.data.TeamEntity
import sheridan.android.assignment3_fifa.views.EditViewModel

class EditDisplayFragment : Fragment() {


    private  var teamList:List<TeamEntity> = listOf()
    private lateinit var tDb: TeamDatabase
    private lateinit var display: RecyclerView
    private lateinit var teamArray:ArrayList<EditViewModel>
    private lateinit var searchText: EditText
    private lateinit var searchButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_edit_display, container, false)


        tDb = TeamDatabase.getInstance(requireContext())
        display = v.findViewById(R.id.deleteView)
        teamArray = ArrayList<EditViewModel>()
        searchText = v.findViewById(R.id.searchName)
        searchText.showSoftInputOnFocus = false;
        searchText.setOnClickListener { v.findViewById<TextView>(R.id.searchName).text = "" }
        searchButton = v.findViewById(R.id.searchButton)

        GlobalScope.launch(Dispatchers.Default) {
            teamList = tDb.teamDao()
                .getAllTeams()
            launch(Dispatchers.Main) {
                displayTeams(teamList)
            }
        }
        searchButton.setOnClickListener{
            var name = searchText.text.toString()
            if (name == "") {
                teamArray.clear()
                GlobalScope.launch(Dispatchers.Default) {
                    teamList = tDb.teamDao()
                        .getAllTeams()
                    launch(Dispatchers.Main) {
                        displayTeams(teamList)
                    }
                }
            }else{
                teamArray.clear()
                GlobalScope.launch(Dispatchers.Default) {
                    teamList = tDb.teamDao().findData(name)
                    launch(Dispatchers.Main) {
                        displayTeams(teamList)
                    }
                }
            }
        }
        setHasOptionsMenu(true)

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.edit_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    fun displayTeams(teamList: List<TeamEntity>){
        var img: Int = 0
        for (t in teamList) {
            when (t.teamName) {
                "Brazil" -> img = R.drawable.brazil
                "Croatia" -> img = R.drawable.croatia
                "England" -> img = R.drawable.england
                "France" -> img = R.drawable.france
                "Russia" -> img = R.drawable.russia
                "Sweden" -> img = R.drawable.sweden
                "Uruguay" -> img = R.drawable.uruguay
                else -> img = R.drawable.cup_img
            }
            val team = EditViewModel(img, t.teamName, "EDIT")
            teamArray.add(team)
        }
        display.adapter = EditAdapter(teamArray)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        display.layoutManager = LinearLayoutManager(activity)
    }

}