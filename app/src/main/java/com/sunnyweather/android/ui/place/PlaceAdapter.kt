package com.sunnyweather.android.ui.place

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place

/**
 * <pre>
 *     @author : lvkuiliang
 *     @e-mail : 1943641461@qq.com
 *     @desc   : 适配器
 *     @created: 2022/12/28 15:27
 * </pre>
 */
class PlaceAdapter : BaseQuickAdapter<Place, BaseViewHolder> {

    constructor() : super(R.layout.place_item)


    override fun convert(holder: BaseViewHolder, item: Place) {
        holder.setText(R.id.placeName, item.name)
        holder.setText(R.id.placeAddress, item.address)
    }

//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val placeName: TextView = view.placeName
//        val placeAddress: TextView = view.placeAddress
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
//        val holder = ViewHolder(view)
//        holder.itemView.setOnClickListener {
//            val position = holder.adapterPosition
//            val place = placeList[position]
//            val activity = fragment.activity
//            if (activity is WeatherActivity) {
//                activity.drawerLayout.closeDrawers()
//                activity.viewModel.locationLng = place.location.lng
//                activity.viewModel.locationLat = place.location.lat
//                activity.viewModel.placeName = place.name
//                activity.refreshWeather()
//            } else {
//                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
//                    putExtra("location_lng", place.location.lng)
//                    putExtra("location_lat", place.location.lat)
//                    putExtra("place_name", place.name)
//                }
//                fragment.startActivity(intent)
//                fragment.activity?.finish()
//            }
//            // 存缓存
//            fragment.viewModel.savePlace(place)
//        }
//        return holder
//    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val place = placeList[position]
//        holder.placeName.text = place.name
//        holder.placeAddress.text = place.address
//    }

//    override fun getItemCount() = placeList.size

}