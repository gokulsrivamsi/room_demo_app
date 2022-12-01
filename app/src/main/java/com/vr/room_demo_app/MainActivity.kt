package com.vr.room_demo_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vr.room_demo_app.databinding.ActivityMainBinding
import com.vr.room_demo_app.viewModel.OrganizationVm
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binder: ActivityMainBinding
    private lateinit var searchAdapter: SearchAdapter

    private var organizationList: MutableList<OrganizationData> = mutableListOf()
    private var searchList: MutableList<OrganizationData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@MainActivity)[OrganizationVm::class.java]
            lifecycleOwner = this@MainActivity
        }
        setContentView(binder.root)

        searchAdapter = SearchAdapter(this@MainActivity, organizationList)


        binder.swipeRefresh.setOnRefreshListener { binder.viewModel?.fetchSearchAPIData() }

        binder.viewModel?.organizationData?.observe(this) {
            binder.swipeRefresh.isRefreshing = false
            it?.let {
                organizationList.clear()
                organizationList.addAll(it)
                binder.searchViewList.apply {
                    adapter = searchAdapter

                    /** Listener Used for
                     * Swipe to delete item from Right to Left
                     * Drag to change the item Pos
                     */
                    ItemTouchHelper(object :
                        ItemTouchHelper.SimpleCallback(
                            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                            ItemTouchHelper.LEFT
                        ) {
                        override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                        ): Boolean {
                            val from_pos = viewHolder.adapterPosition
                            val target_pos = target.adapterPosition

                            Collections.swap(organizationList, from_pos, target_pos)
                            searchAdapter.notifyItemMoved(from_pos, target_pos)
                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val position = viewHolder.adapterPosition

                            searchAdapter.removeItem(position)
                        }

                    }).attachToRecyclerView(this)

                }
            }
        }


        binder.viewModel?.getSearchKeyDbObserver()?.observe(this) {
            filter(it ?: "")
        }
    }

    override fun onResume() {
        super.onResume()
        binder.viewModel?.checkOrganizationData()
    }

    private fun filter(text: String) {
        searchList = mutableListOf()
        for (item in organizationList) {
            if (item.name?.lowercase(Locale.getDefault())
                    ?.contains(text.lowercase(Locale.getDefault())) == true || item.city?.lowercase(
                    Locale.getDefault()
                )?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(item)
            }
        }

        searchAdapter.filterList(searchList)
    }

}