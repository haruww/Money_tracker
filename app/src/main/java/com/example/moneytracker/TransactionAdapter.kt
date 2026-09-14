package com.example.moneytracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter (
    private val daftar: List<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textJudul: TextView = view.findViewById(R.id.textJudul)
        val textKategori: TextView = view.findViewById(R.id.textKategori)
        val textTanggal: TextView = view.findViewById(R.id.textTanggal)
        val textNominal: TextView = view.findViewById(R.id.textNominal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val transaksi = daftar[position]

        holder.textJudul.text = transaksi.title
        holder.textKategori.text = transaksi.category

        holder.textTanggal.text = formatTanggal(transaksi.date)

        val tanda = if (transaksi.type == "Pemasukan") "+" else "-"
        holder.textNominal.text = "$tanda Rp ${transaksi.amount}"

        val warna = if (transaksi.type == "Pemasukan") {
            R.color.income_green
        } else {
            R.color.expense_red
        }
        holder.textNominal.setTextColor(
            holder.itemView.context.getColor(warna)
        )
    }

    override fun getItemCount(): Int = daftar.size

}

