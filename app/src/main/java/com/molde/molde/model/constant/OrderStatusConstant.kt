package com.molde.molde.model.constant

object OrderStatusConstant {
    const val WAITING_FOR_PAYMENT = "WAITING_FOR_PAYMENT"
    const val WAITING_FOR_PAYMENT_RESP = "Menunggu pembayaran"
    const val WAITING_FOR_PAYMENT_CONFIRMATION = "WAITING_FOR_PAYMENT_CONFIRMATION"
    const val WAITING_FOR_PAYMENT_CONFIRMATION_RESP = "Menunggu konfirmasi admin"
    const val PAYMENT_ACCEPTED = "PAYMENT_ACCEPTED"
    const val PAYMENT_ACCEPTED_RESP = "Pesanan sedang diproses"
    const val PAYMENT_REJECTED = "PAYMENT_REJECTED"
    const val PAYMENT_REJECTED_RESP = "Pembayaran ditolak, mohon periksa kembali"
    const val SHIPMENT_IN_PROGRESS = "SHIPMENT_IN_PROGRESS"
    const val SHIPMENT_IN_PROGRESS_RESP = "Pesanan anda sedang dalam pengiriman"
    const val ORDER_COMPLETE = "ORDER_COMPLETE"
    const val ORDER_COMPLETE_RESP = "Pesanan telah berhasil diterima"
    const val CANCELLED = "CANCELLED"
    const val CANCELLED_RESP = "Pesanan dibatalkan"
}