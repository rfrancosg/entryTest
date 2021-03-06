package com.example.entrytest.View.Fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.entrytest.Model.User
import com.example.myapplication.View.Activity.MapsActivity
import com.example.myapplication.R
import com.example.myapplication.View.MyAdapter

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_entry_test_list.*
import kotlinx.android.synthetic.main.user_list_item.view.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class EntryTestListFragment : Fragment() {

    private val TAG = "EntryTestListFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val users = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var inflate = inflater.inflate(R.layout.fragment_entry_test_list, container, false)
        getUsers()
        viewManager = LinearLayoutManager(context)

        return inflate
        }


    fun getUsers(){
        val url = "https://reqres.in/api/users/"
        Thread.sleep(2000)
        val que = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d(TAG, response.toString())
                val data = response.optJSONArray("data")
                    ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
                    ?.map { Foo(it.toString()) }
                if (data != null) {
                    for (foo in data) {
                        users.add(
                            User(
                                foo.id,
                                foo.email,
                                foo.first_name,
                                foo.last_name,
                                foo.avatar
                            )
                        )
                    }
                    var imagesArray = mutableListOf<String>()
                    for (item in data){
                        imagesArray.add(item.avatar)
                    }

                    entryTestMapsImage.setOnClickListener {
                        val intent = Intent(activity, MapsActivity::class.java)
                        intent.putExtra("Users", ArrayList(imagesArray))
                        startActivity(intent)
                    }
                    entryTestRefreshImage.setOnClickListener {
                        users.clear()
                        getUsers()
                        Toast.makeText(context, "Usuarios actualizados", Toast.LENGTH_SHORT).show()
                    }
                    fillData()
                }
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.toString())
            }
        )
        que.add(jsonObjectRequest)
    }

    private fun fillData(){
        viewAdapter = MyAdapter(users)


        recyclerView = entryTestRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }

    class Foo(json: String) : JSONObject(json) {
        val id = this.optInt("id")
        val email: String = this.optString("email")
        val first_name: String = this.optString("first_name")
        val last_name: String = this.optString("last_name")
        val avatar: String = this.optString("avatar")
    }

}
