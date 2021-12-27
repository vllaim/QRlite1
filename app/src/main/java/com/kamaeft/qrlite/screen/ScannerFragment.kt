package com.kamaeft.qrlite.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kamaeft.Model.DataModel
import com.kamaeft.qrlite.Const.APP
import com.kamaeft.qrlite.R
import com.kamaeft.qrlite.databinding.FragmentScannerBinding
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerFragment : Fragment(), ZBarScannerView.ResultHandler {
    private val dataModel: DataModel by activityViewModels()
    lateinit var binding: FragmentScannerBinding
    private lateinit var zbScanner : ZBarScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScannerBinding.inflate(layoutInflater, container, false)

        zbScanner = ZBarScannerView(context)

        return zbScanner
    }

    override fun onResume() {
        super.onResume()
        zbScanner.setResultHandler(this)
        zbScanner.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbScanner.stopCamera()
    }

    override fun handleResult(result: Result?) {
        val resultQR = result?.contents
        dataModel.message.value = resultQR
        APP.navController.navigate(R.id.action_scannerFragment_to_mainFragment)
    }


}