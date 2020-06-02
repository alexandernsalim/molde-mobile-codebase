package com.molde.molde.presentation.order_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.rajaongkir.RajaOngkirWaybillManifestItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_manifest.view.*

class ManifestsAdapter : RecyclerView.Adapter<ManifestsAdapter.ManifestViewHolder>() {
    private val manifests: MutableList<RajaOngkirWaybillManifestItem> = mutableListOf()

    fun setData(manifests: List<RajaOngkirWaybillManifestItem>) {
        this.manifests.clear()
        this.manifests.addAll(manifests)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ManifestViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_manifest, parent, false)
    )

    override fun getItemCount() = manifests.size

    override fun onBindViewHolder(holder: ManifestViewHolder, position: Int) =
        holder.bind(manifests[position])

    inner class ManifestViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(manifest: RajaOngkirWaybillManifestItem) {
            with(itemView) {
                tv_manifest_description.text = manifest.manifestDescription
                tv_manifest_date.text = manifest.manifestDate
                tv_manifest_time.text = manifest.manifestTime
            }
        }
    }
}