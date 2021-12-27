package com.kamaeft.qrlite.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.kamaeft.Model.DataModel
import com.kamaeft.qrlite.Const.APP
import com.kamaeft.qrlite.R
import com.kamaeft.qrlite.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private val dataModel : DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btScanner.setOnClickListener {
            chekCameraPermission()
        }
        binding.btClear.setOnClickListener {

            binding.tvQr.text = ""
            //binding.imQr.visibility = View.INVISIBLE
            binding.etQr.text.clear()
        }


        dataModel.message.observe(APP, {
            binding.tvQr.text = it
        })

        binding.btGenerator.setOnClickListener {
            val data = binding.etQr.text.toString()

            if (data.isEmpty()){
                Toast.makeText(context, "Вы не ввели текст", Toast.LENGTH_SHORT).show()
            }else{
                val generatorQR = QRGEncoder(data, null, QRGContents.Type.TEXT, 500)
                val bitMap = generatorQR.encodeAsBitmap()
                binding.imQr.setImageBitmap(bitMap)
            }

        }





    }

    private fun chekCameraPermission(){
        if(ContextCompat.checkSelfPermission(APP, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(APP, arrayOf(Manifest.permission.CAMERA), 101)
        }else{
            APP.navController.navigate(R.id.action_mainFragment_to_scannerFragment)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 101){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                APP.navController.navigate(R.id.action_mainFragment_to_scannerFragment)
        }

    }


}