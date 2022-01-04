package com.leandro.mvvmtraining

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leandro.mvvmtraining.adapters.MainAdapter
import com.leandro.mvvmtraining.databinding.ActivityMainBinding
import com.leandro.mvvmtraining.repository.MainRepository
import com.leandro.mvvmtraining.rest.RetrofitService
import com.leandro.mvvmtraining.viewmodel.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Factory do ViewModel "LifeCycle"
    lateinit var viewModel : MainViewModel

    //Instacia  do retrofit unica
    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter{
        openLink(it.link)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //a MainViewModelFactory espera qual o reposotorio no construtor
        // a MainRepositoru espera no construtor quem vai realmente puxa os dados no caso o retrofit
        viewModel = ViewModelProvider(this,MainViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        )

        binding.recycleview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        //toda que que inicar a activir o OBSERVER VAI INICIAR A LISTA NOVA se conseguir
        viewModel.liveList.observe(this, Observer { lives ->
            Log.i("Kaique", "OnStart")
            adapter.setLiveList(lives)
        })
        //Se não mostra o erro
        viewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this,message, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        //chama os dados pelo retrofit
        viewModel.getAllLives()
    }

    private fun openLink(link: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
}