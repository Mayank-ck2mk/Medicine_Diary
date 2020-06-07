package com.example.medicinediary.illness

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.medicinediary.R
import com.example.medicinediary.database.IllnessDatabase
import com.example.medicinediary.database.IllnessDatebaseDao
import com.example.medicinediary.databinding.FragmentIllnessBinding
import kotlinx.android.synthetic.main.add_illness_box.view.*
import kotlinx.android.synthetic.main.add_illness_box.view.enter_illness
import kotlinx.android.synthetic.main.fragment_illness.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IllnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IllnessFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var binding : FragmentIllnessBinding
    lateinit var illnessViewModel : IllnessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentIllnessBinding>(inflater,R.layout.fragment_illness,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = IllnessDatabase.getInstance(application).illnessDatabaseDao
        val viewModelFactory = IllnessViewModelFactory(dataSource, application)

        illnessViewModel = ViewModelProviders.of(this, viewModelFactory).get(IllnessViewModel::class.java)


        binding.setLifecycleOwner(this)
        binding.illnessViewModel = illnessViewModel

        binding.recyclerViewIllness.adapter = illnessViewModel.adapter

        illnessViewModel.navigateToMedicineFragment.observe(viewLifecycleOwner, Observer {
            it?.let {
                NavHostFragment.findNavController(this).navigate(IllnessFragmentDirections.actionIllnessFragmentToMedicineFragment(it))
                illnessViewModel.onDoneNavigating()
            }
        })

        illnessViewModel.allIllnesses.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(!it.isEmpty()){
                    binding.tapGuide.visibility = GONE
                }
                else{
                    binding.tapGuide.visibility = VISIBLE
                }
                Log.d("Database",it.toString())
            }
        })


        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.illness_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!!.itemId ==  R.id.add_illness_icon) {

                val mDialogView = LayoutInflater.from(context).inflate(R.layout.add_illness_box, null)
                mDialogView.requestFocus()
                val mBuilder = AlertDialog.Builder(context)
                        .setView(mDialogView)

                val mAlertDialog = mBuilder.show()

            mDialogView.submit_illness_button.setOnClickListener {

                if(mDialogView.enter_illness.text.toString() == ""){
                    Toast.makeText(context!!.applicationContext, "Please enter illness!", Toast.LENGTH_SHORT).show()
                }
                else {
                    illnessViewModel.enteredIllness = mDialogView.enter_illness.text.toString()
                    illnessViewModel.onSubmitEntry()
                    mAlertDialog.dismiss()
                }

             //   (illnessViewModel.allIllnesses.value!!.size + 1).toString()


            }

                    mDialogView . cancel_illness_button . setOnClickListener {
                mAlertDialog.dismiss()
               // Toast.makeText(context!!.applicationContext, "Canceled", Toast.LENGTH_SHORT).show()
            }

        }

        if(item!!.itemId ==  R.id.delete_all){

            val mBuilder = AlertDialog.Builder(context)

            mBuilder.setTitle("This will ERASE everything!")
            mBuilder.setMessage("\nAre you sure to DELETE everything ?")
            mBuilder.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int ->
                illnessViewModel.deleteEverything()
            })

            mBuilder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
            mBuilder.show()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IllnessFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IllnessFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}