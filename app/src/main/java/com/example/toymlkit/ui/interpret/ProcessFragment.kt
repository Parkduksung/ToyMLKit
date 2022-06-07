package com.example.toymlkit.ui.interpret

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.toymlkit.R
import com.example.toymlkit.base.BaseFragment
import com.example.toymlkit.databinding.FragmentInterpretBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProcessFragment : BaseFragment<FragmentInterpretBinding>(R.layout.fragment_interpret) {


    private val interpretViewModel by viewModels<InterpretViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        binding.progress.bringToFront()
        val getStringUri = requireActivity().intent.getStringExtra(KEY_URI)
        if (getStringUri != null) {
            val toUri = Uri.parse(getStringUri)
            binding.image.setImageURI(toUri)
            interpretViewModel.load(toUri)
        }
    }


    private fun initViewModel() {
        interpretViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? InterpretViewState)?.let {
                onChangedInterpretViewState(it)
            }
        }
    }

    private fun onChangedInterpretViewState(viewState: InterpretViewState) {
        when (viewState) {
            is InterpretViewState.GetResult -> {
                with(binding) {

                    requireActivity().supportFragmentManager.beginTransaction().replace(
                        R.id.container_interpret,
                        ResultFragment.newInstance(
                            viewState.faceType,
                            viewState.eyeType,
                            viewState.eyeLength,
                            viewState.eyeDirection,
                            viewState.noseLength,
                            viewState.noseArea,
                            viewState.mouthLength,
                            viewState.mouthThickness
                        )
                    ).commit()
                }
            }
        }
    }

    companion object {
        const val KEY_URI = "key_uri"
    }
}