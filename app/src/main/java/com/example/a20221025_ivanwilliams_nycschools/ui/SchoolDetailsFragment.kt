package com.example.a20221025_ivanwilliams_nycschools.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a20221025_ivanwilliams_nycschools.R
import com.example.a20221025_ivanwilliams_nycschools.data.model.NYCSATResponse
import com.example.a20221025_ivanwilliams_nycschools.data.model.NYCSchoolResponse
import com.example.a20221025_ivanwilliams_nycschools.databinding.SchoolDetailsFragmentBinding
import com.example.a20221025_ivanwilliams_nycschools.repository.state.UIState
import com.example.a20221025_ivanwilliams_nycschools.ui.viewmodel.SchoolViewModel
import com.example.a20221025_ivanwilliams_nycschools.ui.viewmodel.SchoolViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SchoolDetailsFragment : Fragment() {

    private lateinit var binding: SchoolDetailsFragmentBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SchoolViewModel::class.java]
    }

    private var schoolNameStr: String? = null
    private var schoolLocStr: String? = null
    private var schoolEmailStr: String? = null
    private var schoolPhoneStr: String? = null
    private var schoolOverview: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SchoolDetailsFragmentBinding.inflate(inflater, container, false)

        initObservables()

        arguments?.let {
            val school = it.getSerializable(KEY_DBN) as NYCSchoolResponse

            viewModel.getSatDetails(school.dbn)

            schoolNameStr = school.school_name
            schoolEmailStr = "Email: ${school.school_email}"
            schoolLocStr = "Address: ${school.location}"
            schoolPhoneStr = "Phone Number: ${school.phone_number}"
            schoolOverview = school.overviewDetails

            binding.schoolName.text = schoolNameStr
            binding.schoolLocation.text = String.format("Address: ${school.location}")
            binding.schoolEmail.text = String.format("Email: ${school.school_email}")
            binding.schoolPhone.text = String.format("Phone Number: ${school.phone_number}")
            binding.overviewSchool.text = schoolOverview
        }

        return binding.root
    }

    private fun initObservables() {

        viewModel.schoolSATState.observe(viewLifecycleOwner) { uiState: UIState ->
            when(uiState) {
                is UIState.SATResponse -> { updateView(uiState.data) }
                is UIState.Error -> {
                    showError(uiState.error.localizedMessage)
                }
                is UIState.SchoolResponse -> {
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

    private fun updateView(data: NYCSATResponse) {
        val takers = "${getString(R.string.sat_takers)}: ${data.num_of_sat_test_takers}"
        val math = "${getString(R.string.sat_math)}: ${data.sat_math_avg_score}"
        val read = "${getString(R.string.sat_read)}: ${data.sat_critical_reading_avg_score}"
        val write = "${getString(R.string.sat_write)}: ${data.sat_writing_avg_score}"

        if (data.dbn == null ||
            data.school_name == null ||
            data.num_of_sat_test_takers == null ||
            data.sat_math_avg_score == null ||
            data.sat_writing_avg_score == null ||
            data.sat_critical_reading_avg_score == null) {
            binding.satDetails.text = getString(R.string.sat_na)
        } else {
            binding.satDetails.text = getString(R.string.sat_details)
            binding.schoolDetailsSatTakers.text = takers
            binding.schoolDetailsSatReading.text = read
            binding.schoolDetailsSatWriting.text = write
            binding.schoolDetailsSatMath.text = math
        }
    }

    companion object {
        private const val KEY_DBN = "KEY_SCHOOL"

        fun getInstance(school: NYCSchoolResponse): Fragment = SchoolDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_DBN, school)
                }
            }
    }

}