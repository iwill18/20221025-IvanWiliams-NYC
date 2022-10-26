package com.example.a20221025_ivanwilliams_nycschools.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a20221025_ivanwilliams_nycschools.R
import com.example.a20221025_ivanwilliams_nycschools.data.model.NYCSchoolResponse
import com.example.a20221025_ivanwilliams_nycschools.databinding.SchoolDisplayFragmentBinding
import com.example.a20221025_ivanwilliams_nycschools.repository.state.UIState
import com.example.a20221025_ivanwilliams_nycschools.ui.adapter.SchoolAdapter
import com.example.a20221025_ivanwilliams_nycschools.ui.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolDisplayFragment: Fragment() {

    private lateinit var binding: SchoolDisplayFragmentBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SchoolViewModel::class.java]
    }

    private val schoolAdapter by lazy {
        SchoolAdapter {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    SchoolDetailsFragment.getInstance(it)
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SchoolDisplayFragmentBinding.inflate(inflater,container,false)

        binding.schoolList.apply {
            layoutManager = GridLayoutManager(
                context,
                3,
                RecyclerView.VERTICAL,
                false
            )

            adapter = schoolAdapter
        }

        initObservables()

        return binding.root
    }

    private fun initObservables() {
        viewModel.schoolState.observe(viewLifecycleOwner) { uiState: UIState ->
            when(uiState) {
                is UIState.SchoolResponse -> { updateAdapter(uiState.data) }
                is UIState.Error -> { showError(uiState.error.localizedMessage) }
                is UIState.SATResponse -> {
                    // no-op

                }
            }
        }
    }

    private fun showError(errorMessage: String) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Error occurred")
            .setMessage(errorMessage)
            .setNegativeButton("DISSMIS") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun updateAdapter(data: List<NYCSchoolResponse>) {
        schoolAdapter.updateSchools(data)
    }
}