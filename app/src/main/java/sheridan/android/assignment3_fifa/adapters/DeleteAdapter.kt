package sheridan.android.assignment3_fifa.adapters

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.data.TeamDao
import sheridan.android.assignment3_fifa.data.TeamDatabase
import sheridan.android.assignment3_fifa.fragments.DeleteFragment
import sheridan.android.assignment3_fifa.views.DeleteViewModel
import sheridan.android.assignment3_fifa.views.EditViewModel

class DeleteAdapter(private val mList: List<DeleteViewModel>): RecyclerView.Adapter<DeleteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.delete_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val EditViewModel = mList[position]

        holder.flag.setImageResource(EditViewModel.image)
        holder.team.text = EditViewModel.text
        holder.del.text = "DELETE"

        holder.del.setOnClickListener{view: View->
            val dialogBuilder = AlertDialog.Builder(view.context)
            dialogBuilder.setMessage("You want to delete the record?")

                .setCancelable(false)
                .setPositiveButton("Delete",DialogInterface.OnClickListener {
                        dialog, id ->
                    GlobalScope.launch(Dispatchers.Default) {
                        val db:TeamDao = TeamDatabase.getInstance(view.context).teamDao()
                        db.delete(holder.team.text.toString())

                    }
                    })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Team?")
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val flag: ImageView = itemView.findViewById(R.id.image)
        val team: TextView = itemView.findViewById(R.id.tName)
        val del: TextView = itemView.findViewById(R.id.delete)
    }
}