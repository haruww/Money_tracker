package com.example.moneytracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moneytracker.AddTransactionActivity
import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {

    // Daftar transaksi sementara (in-memory).
    private val daftarTransaksi = mutableListOf<Transaction>()

    // Adapter yang menjembatani list ke RecyclerView.
    private lateinit var adapter: TransactionAdapter

    // Penghitung id sederhana untuk memberi id unik tiap transaksi.
    private var idBerikutnya = 1

    // Launcher untuk membuka AddTransactionActivity & menerima hasilnya.
    // Ini pengganti modern dari startActivityForResult yang lama.
    private val addLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Hanya proses jika form ditekan Simpan (RESULT_OK).
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult

            // Ambil data yang dikirim balik dari form.
            val judul = data.getStringExtra(AddTransactionActivity.EXTRA_JUDUL) ?: "-"
            val nominal = data.getLongExtra(AddTransactionActivity.EXTRA_NOMINAL, 0)
            val tipe = data.getStringExtra(AddTransactionActivity.EXTRA_TIPE) ?: "Pengeluaran"
            val kategori = data.getStringExtra(AddTransactionActivity.EXTRA_KATEGORI) ?: "Lainnya"
            // Tanggal/jam yang dipilih user di form (default: sekarang).
            val tanggal = data.getLongExtra(
                AddTransactionActivity.EXTRA_TANGGAL, System.currentTimeMillis()
            )

            // Buat objek transaksi baru, masukkan ke awal list.
            val transaksi = Transaction(
                id = idBerikutnya++,
                title = judul,
                amount = nominal,
                type = tipe,
                category = kategori,
                date = tanggal
            )
            daftarTransaksi.add(0, transaksi)

            // Beri tahu adapter agar RecyclerView memperbarui tampilan.
            adapter.notifyItemInserted(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Siapkan RecyclerView: susun item vertikal + pasang adapter.
        val recycler = findViewById<RecyclerView>(R.id.recyclerTransaksi)
        adapter = TransactionAdapter(daftarTransaksi)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // Tombol Tambah -> buka form AddTransactionActivity.
        findViewById<Button>(R.id.buttonTambah).setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            addLauncher.launch(intent)
        }
    }

    private fun pindahScreen() {
        findViewById<Button>(R.id.buttonTambah).setOnClickListener {
            val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }
}