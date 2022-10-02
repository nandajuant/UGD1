package com.example.komoke.entity

class ItemOrder(var tujuan: String, var id: String, var event: String, var jumlah: String, var tanggal: String, var detail: String) {

    companion object{
        @JvmField
        var listOfOrder = arrayOf(
            ItemOrder("Hotel", "H001", "Hotel Tentrem", "1 Kamar", "21-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Hotel", "H002", "Hotel Horizon", "1 Kamar", "20-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Event", "E001", "Konser Slank", "1 Tiket", "13-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Event", "E002", "Konser Slank", "3 Tiket", "13-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Pesawat", "P001", "Lion Air", "1 Kursi", "12-11-2022", "E-tiket sudah terbit"),
            ItemOrder("Pesawat", "P002", "Batik Air", "2 Kursi", "13-11-2022", "E-tiket sudah terbit"),
            ItemOrder("Kereta", "K001", "Joglo Semar", "1 Tiket", "13-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Kereta", "K002", "KRL", "3 Tiket", "14-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Bus", "B001", "Sumber Alam", "1 Kursi", "13-12-2022", "E-tiket sudah terbit"),
            ItemOrder("Bus", "B002", "Pahala", "3 Kursi", "13-12-2022", "E-tiket sudah terbit")

        )
    }
}