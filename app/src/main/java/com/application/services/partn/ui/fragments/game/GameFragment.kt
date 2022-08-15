package com.application.services.partn.ui.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.services.partn.R
import com.application.services.partn.databinding.GameFragmentBinding

class GameFragment : Fragment() {
    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameBtn.setOnClickListener {
            spin()
            if (binding.imageView.tag == binding.imageView2.tag &&
                binding.imageView2.tag == binding.imageView3.tag
            ) {
                score++
                binding.score.setText(score)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun spin() {
        val iMList = listOf(
            R.drawable.ic1, R.drawable.ic1, R.drawable.ic1, R.drawable.ic1,
            R.drawable.ic2, R.drawable.ic2, R.drawable.ic2, R.drawable.ic2,
            R.drawable.ic3, R.drawable.ic3, R.drawable.ic3, R.drawable.ic3,
            R.drawable.ic4, R.drawable.ic4, R.drawable.ic4, R.drawable.ic4,
            R.drawable.ic5, R.drawable.ic5, R.drawable.ic5, R.drawable.ic5
        ).shuffled()
        binding.imageView.setImageResource(iMList[0])
        binding.imageView.tag = iMList[0]
        binding.imageView2.setImageResource(iMList[1])
        binding.imageView2.tag = iMList[1]
        binding.imageView3.setImageResource(iMList[2])
        binding.imageView3.tag = iMList[2]
    }
}