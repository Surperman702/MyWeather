package com.sunnyweather.android.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cxz.kotlin.baselibs.base.BaseMvpFragment
import com.sunnyweather.android.MainActivity
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.FragmentPlaceBinding
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.mvp.contract.PlaceContract
import com.sunnyweather.android.mvp.presenter.PlacePresenter
import com.sunnyweather.android.ui.weather.WeatherActivity
import com.dylanc.viewbinding.binding
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.PlaceResponse

/**
 * <pre>
 *     @author : lvkuiliang
 *     @e-mail : 1943641461@qq.com
 *     @desc   : 实现Fragment
 *     @created: 2022/12/28 15:29
 * </pre>
 */
class PlaceFragment :
    BaseMvpFragment<PlaceContract.View, PlaceContract.Presenter>(R.layout.fragment_place),
    PlaceContract.View {

    private val binding: FragmentPlaceBinding by binding()

    // 使用了lazy函数这种懒加载技术来获取PlaceViewModel的实例，
    // 这是一种非常棒的写法，允许我们在整个类中随时使用viewModel这个变量，而完全不用关心它何时初始化、 是否为空等前提条件。
//    val viewModel by lazy { ViewModelProvider(this)[PlaceViewModel::class.java] }

//    private lateinit var adapter: PlaceAdapter

    private var adapter: PlaceAdapter? = null

    private val placeList = ArrayList<Place>()

    override fun createPresenter(): PlaceContract.Presenter = PlacePresenter()

    override fun lazyLoad() {}

    override fun initView(view: View) {
        super.initView(view)
        initViews()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViews() {
        // 当PlaceFragment被嵌入MainActivity中，并且之前已经存在选中的城市
        if (activity is MainActivity && PlaceDao.isPlaceSaved()) {
            val place = PlaceDao.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
//        adapter = PlaceAdapter(this, viewModel.placeList)
        adapter = PlaceAdapter()
        adapter?.setOnItemClickListener(itemClickListener)
        binding.recyclerView.adapter = adapter
        binding.searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                mPresenter?.searchPlaces(content)
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
//                viewModel.placeList.clear()
                placeList.clear()
                adapter?.notifyDataSetChanged()
            }
        }
//        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
//            val places = result.getOrNull()
//            if (places != null) {
//                binding.recyclerView.visibility = View.VISIBLE
//                binding.bgImageView.visibility = View.GONE
//                viewModel.placeList.clear()
//                viewModel.placeList.addAll(places as ArrayList<Place>)
//                adapter.notifyDataSetChanged()
//            } else {
//                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
//                result.exceptionOrNull()?.printStackTrace()
//            }
//        })
    }

    override fun onSearchPlacesResult(data: PlaceResponse?) {
        if (data != null) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.bgImageView.visibility = View.GONE
//            viewModel.placeList.clear()
            placeList.clear()
//            viewModel.placeList.addAll(data as ArrayList<Place>)
            placeList.addAll(data.places)
            adapter?.setList(placeList)
//            adapter?.notifyDataSetChanged()
        } else {
            showMsg("未能查询到任何地点")
        }
    }

    override fun onSearchPlacesError(msg: String) {
        showMsg(msg)
    }

    private val itemClickListener =
        OnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            val place = adapter.getItem(position) as Place
            val activity = this.activity
            if (activity is WeatherActivity) {
                val drawerLayout: DrawerLayout = activity.findViewById(R.id.drawerLayout)
                drawerLayout.close()
                activity.locationLng = place.location.lng
                activity.locationLat = place.location.lat
                activity.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(this.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                activity?.startActivity(intent)
                activity?.finish()
            }
            // 存缓存
//            mPresenter?.savePlace(place)
            PlaceDao.savePlace(place)
        }

    // 加载了前面编写的fragment_place布局
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_place, container, false)
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        // 当PlaceFragment被嵌入MainActivity中，并且之前已经存在选中的城市
//        if (activity is MainActivity && viewModel.isPlaceSaved()) {
//            val place = viewModel.getSavePlace()
//            val intent = Intent(context, WeatherActivity::class.java).apply {
//                putExtra("location_lng", place.location.lng)
//                putExtra("location_lat", place.location.lat)
//                putExtra("place_name", place.name)
//            }
//            startActivity(intent)
//            activity?.finish()
//            return
//        }
//        val layoutManager = LinearLayoutManager(activity)
//        recyclerView.layoutManager = layoutManager
//        adapter = PlaceAdapter(this, viewModel.placeList)
//        recyclerView.adapter = adapter
//        searchPlaceEdit.addTextChangedListener { editable ->
//            val content = editable.toString()
//            if (content.isNotEmpty()) {
//                viewModel.searchPlaces(content)
//            } else {
//                recyclerView.visibility = View.GONE
//                bgImageView.visibility = View.VISIBLE
//                viewModel.placeList.clear()
//                adapter.notifyDataSetChanged()
//            }
//        }
//        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
//            val places = result.getOrNull()
//            if (places != null) {
//                recyclerView.visibility = View.VISIBLE
//                bgImageView.visibility = View.GONE
//                viewModel.placeList.clear()
//                viewModel.placeList.addAll(places as ArrayList<Place>)
//                adapter.notifyDataSetChanged()
//            } else {
//                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
//                result.exceptionOrNull()?.printStackTrace()
//            }
//        })
//    }

}