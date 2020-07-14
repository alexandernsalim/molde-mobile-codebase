package com.molde.molde.presentation.change_address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molde.molde.R
import com.molde.molde.model.entity.Address
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_address.view.tv_address_city
import kotlinx.android.synthetic.main.item_address.view.tv_address_label
import kotlinx.android.synthetic.main.item_address.view.tv_address_phone
import kotlinx.android.synthetic.main.item_address.view.tv_address_province
import kotlinx.android.synthetic.main.item_address.view.tv_address_recipient
import kotlinx.android.synthetic.main.item_address.view.tv_address_street
import kotlinx.android.synthetic.main.item_change_address.view.*

class ChangeAddressAdapter(
    private val addressId: Int,
    private val addresses: List<Address>,
    private val communicator: IChangeAddressCommunicator
) : RecyclerView.Adapter<ChangeAddressAdapter.ChangeAddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeAddressViewHolder {
        return ChangeAddressViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_change_address, parent, false)
        )
    }

    override fun getItemCount() = addresses.size

    override fun onBindViewHolder(holder: ChangeAddressViewHolder, position: Int) =
        holder.bind(addresses[position])

    inner class ChangeAddressViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(address: Address) {
            itemView.tv_address_label.text = address.saveAs
            itemView.tv_address_recipient.text = address.recipient
            itemView.tv_address_street.text = address.street
            itemView.tv_address_city.text = address.city
            itemView.tv_address_province.text = ", ${address.province}"
            itemView.tv_address_phone.text = address.recipientPhone

            if (address.id == addressId) {
                itemView.bt_choose_address.visibility = View.GONE
            }

            itemView.bt_choose_address.setOnClickListener {
                communicator.chooseAddress(address)
            }

        }

    }

    interface IChangeAddressCommunicator {
        fun chooseAddress(address: Address)
    }

}