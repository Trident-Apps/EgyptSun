package com.application.services.partn.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.application.services.partn.R
import com.application.services.partn.databinding.LoadingFragmentBinding
import com.application.services.partn.utils.Checker
import com.application.services.partn.viewmodel.EgyptSunVM
import com.application.services.partn.viewmodel.EgyptSunVMFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {
    lateinit var myVM: EgyptSunVM
    private var _binding: LoadingFragmentBinding? = null
    private val binding get() = _binding!!
    private val checker = Checker()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoadingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myVMFActory = EgyptSunVMFactory(this.requireActivity().application)
        myVM = ViewModelProvider(this, myVMFActory)[EgyptSunVM::class.java]

        when (checker.isDeviceSecured(this@LoadingFragment.requireActivity())) {
            true -> {
                startGame()
            }
            false -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    val dataStore = myVM.checkDatastoreValue(
                        DATASTORE_KEY,
                        requireContext()
                    )

                    when (dataStore) {
                        null -> {
                            myVM.fetchDeepLink(this@LoadingFragment.requireActivity())

                            lifecycleScope.launch(Dispatchers.Main) {
                                myVM.urlLiveData.observe(viewLifecycleOwner) {
                                    startWebView(it)
                                }
                            }
                        }
                        else -> {
                            lifecycleScope.launch(Dispatchers.Main) {
                                startWebView(dataStore.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startGame() {
        findNavController().navigate(R.id.startGameFragment)
    }

    private fun startWebView(url: String) {
        val bundle = bundleOf(ARGUMENTS_NAME to url)
        findNavController().navigate(R.id.webViewFragment, bundle)
    }

    companion object {
        const val ARGUMENTS_NAME = "url"
        const val DATASTORE_KEY = "finalUrl"
    }
}