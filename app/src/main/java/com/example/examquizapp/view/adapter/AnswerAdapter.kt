package com.example.examquizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.examquizapp.R
import com.example.examquizapp.data.model.QuestionModel
import com.example.examquizapp.databinding.AnswerSampleLayoutBinding
import com.example.examquizapp.viewmodel.QuizViewModel

class AnswerAdapter(var answerList: MutableList<QuestionModel>,val viewModel:QuizViewModel) : Adapter<AnswerAdapter.AnswerViewHolder>() {

    class AnswerViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val binding  = AnswerSampleLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answer_sample_layout,parent,false)
        return AnswerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return answerList.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.binding.setQuizNumber.text = "12"
        holder.binding.setQuestionRv.text = viewModel.quizData.value?.get(position)?.question
        holder.binding.setAnswerRv.text = viewModel.quizData.value?.get(position)?.answer
    }
}