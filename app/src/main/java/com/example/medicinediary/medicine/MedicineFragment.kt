package com.example.medicinediary.medicine

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicinediary.R
import com.example.medicinediary.database.IllnessDatabase
import com.example.medicinediary.databinding.FragmentMedicineBinding
import kotlinx.android.synthetic.main.add_illness_box.view.*
import kotlinx.android.synthetic.main.add_illness_box.view.cancel_illness_button
import kotlinx.android.synthetic.main.add_medicine_box.view.*
import kotlinx.android.synthetic.main.fragment_medicine.view.*


class MedicineFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentMedicineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicineBinding.inflate(inflater)

        val args = MedicineFragmentArgs.fromBundle(requireArguments())
        val dataSource = IllnessDatabase.getInstance(context!!.applicationContext).illnessDatabaseDao
        val viewModelFactory = MedicineViewModelFactory(args.illnessMedicine,dataSource)
        val medicineViewModel = ViewModelProvider(this, viewModelFactory).get(MedicineViewModel::class.java)

        binding.viewModel = medicineViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        medicineViewModel.medicineNameEntered.observe(viewLifecycleOwner, Observer { entered ->
            if(entered){
                binding.medicineTextView.text = medicineViewModel.illnessMedicine.medicine
                binding.expiryTextView.text = medicineViewModel.illnessMedicine.expiryDate
                binding.medicineCard.visibility = VISIBLE
                binding.tapGuideMedicine.visibility = GONE
            }
        })

        if(medicineViewModel.illnessMedicine.medicine == ""){
            binding.medicineCard.visibility = GONE
            binding.tapGuideMedicine.visibility = VISIBLE
        }else{
            binding.medicineTextView.text = medicineViewModel.illnessMedicine.medicine
            binding.expiryTextView.text = medicineViewModel.illnessMedicine.expiryDate
            binding.medicineCard.visibility = VISIBLE
        }


        medicineViewModel.openMedicineDialogBox.observe(viewLifecycleOwner, Observer { opened ->
            if(opened){
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.add_medicine_box, null)
                mDialogView.requestFocus()
                val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)

                val mAlertDialog = mBuilder.show()

                mDialogView.submit_medicine_button.setOnClickListener {

                    if(mDialogView.enter_medicine.text.toString() == ""){
                        Toast.makeText(context!!.applicationContext, "Please enter medicine name!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        medicineViewModel.medicineName = mDialogView.enter_medicine.text.toString()
                        medicineViewModel.expiryDate = mDialogView.enter_expiry_date.text.toString()
                        medicineViewModel.onSubmitEntry()
                        medicineViewModel.onMedicineNameEntered()
                        mAlertDialog.dismiss()
                    }

                    //   (illnessViewModel.allIllnesses.value!!.size + 1).toString()
                    medicineViewModel.onDialogBoxOpened()

                }

                mDialogView . cancel_illness_button . setOnClickListener {
                    mAlertDialog.dismiss()
                    // Toast.makeText(context!!.applicationContext, "Canceled", Toast.LENGTH_SHORT).show()
                }
            }
        })




        // Inflate the layout for this fragment
        return binding.root
    }


}