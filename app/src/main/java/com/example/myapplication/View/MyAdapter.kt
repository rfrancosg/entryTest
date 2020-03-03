package com.example.myapplication.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.entrytest.Model.User
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_list_item.view.*

class MyAdapter(private val myDataset: MutableList<User>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val textView: ConstraintLayout) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        val ConstraintLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list_item, parent, false) as ConstraintLayout
        return MyViewHolder(ConstraintLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.textView.entryTestListItemName.text = myDataset.get(position).first_name + " "+ myDataset.get(position).last_name
        holder.textView.entryTestListItemEmail.text = myDataset.get(position).email
        Picasso.get().load(myDataset.get(position).url).into(holder.textView.entryTestListItemImage)
        holder.textView.setOnClickListener {
            if (it.entryTestListItemEmail.visibility == View.GONE){
                it.entryTestListItemEmail.visibility = View.VISIBLE
            }
            else{
                it.entryTestListItemEmail.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = myDataset.size
}
