package sheridan.android.assignment3_fifa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.views.DeleteViewModel
import sheridan.android.assignment3_fifa.views.DisplayViewModel
import sheridan.android.assignment3_fifa.views.EditViewModel

class DisplayAdapter(private val teamList: List<DisplayViewModel>): RecyclerView.Adapter<DisplayAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.display_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisplayAdapter.ViewHolder, position: Int) {
        val DisplayViewModel = teamList[position]

        holder.img.setImageResource(DisplayViewModel.image)
        holder.tn.text = DisplayViewModel.teamName
        holder.cn.text = DisplayViewModel.continentName
        holder.p.text = DisplayViewModel.gamesPlayed.toString()
        holder.w.text = DisplayViewModel.gamesWon.toString()
        holder.l.text = DisplayViewModel.gamesLost.toString()
        holder.d.text = DisplayViewModel.gamesDraw.toString()
        holder.pts.text = DisplayViewModel.totalPoints.toString()
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val img: ImageView = itemView.findViewById(R.id.flagImg)
        val tn: TextView = itemView.findViewById(R.id.team)
        val cn: TextView = itemView.findViewById(R.id.contName)
        val p: TextView = itemView.findViewById(R.id.gamesP)
        val w: TextView = itemView.findViewById(R.id.gamesW)
        val l: TextView = itemView.findViewById(R.id.gamesL)
        val d: TextView = itemView.findViewById(R.id.gamesD)
        val pts: TextView = itemView.findViewById(R.id.points)
    }
}