package com.molde.molde.presentation.addresses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.entity.Address
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_address.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AddressAdapter(
    private val communicator: IAddressCommunicator
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    private val addresses: MutableList<Address> = mutableListOf()

    fun setData(addresses: List<Address>) {
        this.addresses.clear()
        this.addresses.addAll(addresses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        )
    }

    override fun getItemCount() = addresses.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) =
        holder.bind(addresses[position])

    inner class AddressViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(address: Address) {
            itemView.tv_address_label.text = address.saveAs
            itemView.tv_address_recipient.text = address.recipient
            itemView.tv_address_street.text = address.street
            itemView.tv_address_city.text = address.city
            itemView.tv_address_province.text = ", ${address.province}"
            itemView.tv_address_phone.text = address.recipientPhone
            itemView.sw_primary_address.isChecked = address.primaryAddress

            if (address.primaryAddress) {
                itemView.sw_primary_address.visibility = View.GONE
                itemView.bt_remove_address.visibility = View.GONE
            }

            itemView.sw_primary_address.onClick {
                communicator.setAsPrimary(address.id)
            }
            itemView.bt_update_address.setOnClickListener {
                communicator.updateAddress(address)
            }
            itemView.bt_remove_address.setOnClickListener {
                communicator.removeAddress(address.id)
            }
        }

    }

    interface IAddressCommunicator {
        fun setAsPrimary(addressId: Int)
        fun updateAddress(address: Address)
        fun removeAddress(addressId: Int)
    }
}