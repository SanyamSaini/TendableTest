package com.example.tendable.test.ui

import CategoryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tendable.test.R
import com.example.tendable.test.databinding.ActivityMainBinding
import com.example.tendable.test.interfaces.AnswerClickListener
import com.example.tendable.test.model.AnswerChoiceModel
import com.example.tendable.test.model.InspectionResponse
import com.example.tendable.test.viewmodel.MainVM
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), View.OnClickListener, AnswerClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter

    private val mainVM: MainVM by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainVM.Factory(activity.application))[MainVM::class.java]
    }

    private var inspectionResponse: InspectionResponse? = null

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        categoryAdapter = CategoryAdapter(this)

        initRecyclerView()
        clickListener()
        initObserver()
        try {
            mainVM.getSavedInspection()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecyclerView() {
        binding.rvInspection.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = categoryAdapter
        }
    }

    private fun clickListener() {
        binding.btnStartInspection.setOnClickListener(this)
        binding.btnSaveDraft.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun initObserver() {
        mainVM.savedInspection.observe(this) {
            if(it != null) {
                inspectionResponse = null
                inspectionResponse = it
                inspectionDataBinding()
            }
        }

        mainVM.saveDraftInspection.observe(this) {
            if(it) {
                Toast.makeText(this, R.string.inspection_saved, Toast.LENGTH_LONG).show()
            }
        }


        mainVM.startInspection.observe(this) {
            if(it != null) {
                inspectionResponse = null
                inspectionResponse = it
                inspectionDataBinding()
            }
        }

        mainVM.submitInspection.observe(this) {
            if(it) {
                binding.grpInspection.visibility = View.GONE
                inspectionResponse = null
                binding.btnStartInspection.visibility = View.VISIBLE
                Toast.makeText(this, getString(R.string.inspection_submitted_successfully), Toast.LENGTH_LONG).show()
            } else {
                Log.d(TAG, "submit inspection failed")
            }
        }
    }

    private fun inspectionDataBinding() {
        binding.grpInspection.visibility = View.VISIBLE
        binding.btnStartInspection.visibility = View.GONE

        val gson = Gson()
        val json = gson.toJson(inspectionResponse)
        println(json)

        Log.d(TAG, "inspectionResponse : $json")
        val categoryItems = inspectionResponse?.inspectionModel?.survey?.categories?.flatMap { category ->
            listOf(CategoryItem.CategoryHeader(category)) + category.questions!!.map { CategoryItem.QuestionItem(it) }
        }

        categoryAdapter.submitList(categoryItems)
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.btnStartInspection -> {
                mainVM.callStartInspection()
            }

            binding.btnSaveDraft -> {
                mainVM.saveDraftInspection(inspectionResponse?.inspectionModel!!)
            }

            binding.btnSubmit -> {
                Log.d(TAG, "Submit button called")
                mainVM.callSubmitInspection(inspectionResponse!!)
            }
        }
    }

    override fun answerClick(answerChoiceModel: AnswerChoiceModel, questionId : Int) {
        for(i in 0 until inspectionResponse?.inspectionModel?.survey?.categories?.size!!) {
            val category = inspectionResponse?.inspectionModel?.survey?.categories!![i]
            for(j in 0 until category.questions?.size!!) {
                val question = category.questions!![j]
                if(question.id == questionId) {
                    inspectionResponse?.inspectionModel?.survey?.categories!![i].questions!![j].selectedAnswerChoiceId = answerChoiceModel.id
                    break
                }
            }
        }
    }
}
