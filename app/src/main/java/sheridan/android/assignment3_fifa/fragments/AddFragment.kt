package sheridan.android.assignment3_fifa.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.room.ColumnInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sheridan.android.assignment3_fifa.MainActivity
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.data.TeamDao
import sheridan.android.assignment3_fifa.data.TeamDatabase
import sheridan.android.assignment3_fifa.data.TeamEntity
import sheridan.android.assignment3_fifa.views.DisplayViewModel
import sheridan.android.assignment3_fifa.views.EditViewModel


class AddFragment : Fragment() {

    private lateinit var tDb:TeamDatabase
    private lateinit var continents: Array<String>
    private lateinit var contSpin:Spinner
    private lateinit var addButton: Button
    private lateinit var teamName: TextView
    private lateinit var played: TextView
    private lateinit var won: TextView
    private lateinit var lost: TextView
    private lateinit var draw: TextView


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_add, container, false)

        v.findViewById<TextView>(R.id.teamName).setOnClickListener {
            v.findViewById<TextView>(R.id.teamName).text = ""
        }
        v.findViewById<TextView>(R.id.gamesPlayed).setOnClickListener {
            v.findViewById<TextView>(R.id.gamesPlayed).text = ""
        }
        v.findViewById<TextView>(R.id.gamesWon).setOnClickListener {
            v.findViewById<TextView>(R.id.gamesWon).text = ""
        }
        v.findViewById<TextView>(R.id.gamesLost).setOnClickListener {
            v.findViewById<TextView>(R.id.gamesLost).text = ""
        }
        v.findViewById<TextView>(R.id.gamesDraw).setOnClickListener {
            v.findViewById<TextView>(R.id.gamesDraw).text = ""
        }
        //val database by lazy { TeamDatabase.getInstance(this.requireContext()) }
        tDb = TeamDatabase.getInstance(requireContext())

        continents = arrayOf("List", "Europe", "S. America", "Asia")
        contSpin = v.findViewById(R.id.continentSpinner)
        addButton = v.findViewById(R.id.addButton)
        contSpin.adapter = ArrayAdapter(requireActivity().applicationContext, android.R.layout.simple_spinner_item, continents)

        addButton.setOnClickListener{
            contSpin = v.findViewById(R.id.continentSpinner)
            teamName = v.findViewById(R.id.teamName)
            played = v.findViewById(R.id.gamesPlayed)
            won= v.findViewById(R.id.gamesWon)
            lost = v.findViewById(R.id.gamesLost)
            draw = v.findViewById(R.id.gamesDraw)

            val team = TeamEntity(
            0,
                teamName = teamName.text.toString(),
                continentName = contSpin.selectedItem.toString(),
                playedGames = Integer.parseInt(played.text.toString()),
                wonGames = Integer.parseInt(won.text.toString()),
                lostGames = Integer.parseInt(lost.text.toString()),
                drawGames = Integer.parseInt(draw.text.toString())
            )
            GlobalScope.launch {
                tDb.teamDao().insert(team)
                activity?.runOnUiThread() {
                    Toast.makeText( activity,"Team added to the database successfully.", Toast.LENGTH_SHORT).show()
                }
            }
            teamName.text = ""
            played.text= ""
            won.text= ""
            lost.text= ""
            draw.text = ""
        }
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}