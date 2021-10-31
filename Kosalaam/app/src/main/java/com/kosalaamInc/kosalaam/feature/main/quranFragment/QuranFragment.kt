package com.kosalaamInc.kosalaam.feature.main.quranFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosalaamInc.kosalaam.databinding.FragmentQuranBinding
import com.kosalaamInc.kosalaam.util.SocketApplication
import io.socket.client.Socket

class QuranFragment : Fragment(){
    private var mBinding : FragmentQuranBinding? = null
    lateinit var mSocket: Socket

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val binding = FragmentQuranBinding.inflate(inflater,container,false)
        mBinding = binding
        mSocket = SocketApplication.get()
        mSocket.connect()
        mSocket.emit("message", "hello")
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding= null
        super.onDestroyView()
    }
}