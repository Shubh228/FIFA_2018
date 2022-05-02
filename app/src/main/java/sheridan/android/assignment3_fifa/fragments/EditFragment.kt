package sheridan.android.assignment3_fifa.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.adapters.EditAdapter
import sheridan.android.assignment3_fifa.data.TeamDatabase
import sheridan.android.assignment3_fifa.data.TeamEntity

class EditFragment : Fragment() {

    private lateinit var b: Bundle
    private lateinit var tDb: TeamDatabase
    private lateinit var editButton: Button
    private lateinit var teamName: TextView
    private lateinit var played: TextView
    private lateinit var won: TextView
    private lateinit var lost: TextView
    private lateinit var draw: TextView
    private lateinit var name: String
    private lateinit var continents: Array<String>
    private lateinit var contSpin:Spinner

   /* override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)


        name = bundle.getString("name").toString()
        //display.layoutManager = LinearLayoutManager(activity)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_edit, container, false)
        name = arguments?.getString("name").toString()
        tDb = TeamDatabase.getInstance(requireContext())
        continents = arrayOf("List", "Europe", "S. America", "Asia")
        contSpin = v.findViewById(R.id.continentSpinner)
        contSpin.adapter = ArrayAdapter(requireActivity().applicationContext, android.R.layout.simple_spinner_item, continents)
        teamName = v.findViewById(R.id.teamName)
        played = v.findViewById(R.id.gamesPlayed)
        won= v.findViewById(R.id.gamesWon)
        lost = v.findViewById(R.id.gamesLost)
        draw = v.findViewById(R.id.gamesDraw)
        editButton = v.findViewById(R.id.editButton)

        GlobalScope.launch(Dispatchers.Default) {
            if(name != null) {
                val list = tDb.teamDao().findData(name)
                launch(Dispatchers.Main) {
                    when (list[0].continentName) {
                        "Europe" -> contSpin.setSelection(1)
                        "S. America" -> contSpin.setSelection(2)
                        "Asia" -> contSpin.setSelection(3)
                    }
                    teamName.text = list[0].teamName
                    played.text = list[0].playedGames.toString()
                    won.text = list[0].wonGames.toString()
                    lost.text = list[0].lostGames.toString()
                    draw.text = list[0]?.drawGames.toString()
                }
            }
        }

        editButton.setOnClickListener {
            contSpin = v.findViewById(R.id.continentSpinner)
            teamName = v.findViewById(R.id.teamName)
            played = v.findViewById(R.id.gamesPlayed)
            won = v.findViewById(R.id.gamesWon)
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
            GlobalScope.launch(Dispatchers.Default) {
                tDb.teamDao().delete(name)
                tDb.teamDao().insert(team)
                activity?.runOnUiThread() {
                    Toast.makeText(
                        activity,
                        "Team updated in the database successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            teamName.text = ""
            played.text = ""
            won.text = ""
            lost.text = ""
            draw.text = ""

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



}