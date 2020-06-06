package com.example.medicinediary.illness

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.medicinediary.R
import com.example.medicinediary.database.IllnessDatabase
import com.example.medicinediary.database.IllnessDatebaseDao
import com.example.medicinediary.databinding.FragmentIllnessBinding
import kotlinx.android.synthetic.main.add_illness_box.view.*
import kotlinx.android.synthetic.main.add_illness_box.view.enter_illness

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

        val adapter = IllnessAdapter()
        binding.recyclerViewIllness.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewIllness.adapter = adapter

        illnessViewModel.allIllnesses.observe(viewLifecycleOwner, Observer {
            it?.let{
                Log.d("data","Hello")
                Log.d("data",it.toString())
                adapter.update(it)
                adapter.notifyDataSetChanged()
            }
        })




        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_illness, container, false)

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
                illnessViewModel.enteredIllness = mDialogView.enter_illness.text.toString()
                illnessViewModel.onSubmitEntry()
                //Toast.makeText(context!!.applicationContext, mDialogView.enter_illness.text.toString(), Toast.LENGTH_SHORT).show()
                mAlertDialog.dismiss()

            }

                    mDialogView . cancel_illness_button . setOnClickListener {
                mAlertDialog.dismiss()
               // Toast.makeText(context!!.applicationContext, "Canceled", Toast.LENGTH_SHORT).show()
            }

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