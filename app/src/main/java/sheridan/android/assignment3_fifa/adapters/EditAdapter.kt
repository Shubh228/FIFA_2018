package sheridan.android.assignment3_fifa.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.RecyclerView
import sheridan.android.assignment3_fifa.R
import sheridan.android.assignment3_fifa.fragments.EditFragment
import sheridan.android.assignment3_fifa.fragments.HomeFragment
import sheridan.android.assignment3_fifa.views.EditViewModel

class EditAdapter(private val mList: List<EditViewModel>): RecyclerView.Adapter<EditAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.edit_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditAdapter.ViewHolder, position: Int) {
        val EditViewModel = mList[position]


        holder.flag.setImageResource(EditViewModel.image)
        holder.team.text = EditViewModel.text
        holder.edit.text = "EDIT"
        holder.edit.setOnClickListener{view: View->
            val args = Bundle()
            args.putString("name", holder.team.text.toString())
            val fragment = EditFragment()
            fragment.arguments = args
            Navigation.findNavController(view).navigate(R.id.action_editDisplayFragment_to_editFragment, args)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val flag: ImageView = itemView.findViewById(R.id.image)
        val team: TextView = itemView.findViewById(R.id.tName)
        val edit: TextView = itemView.findViewById(R.id.edit)

    }
}