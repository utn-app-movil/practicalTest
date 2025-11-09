package Util

import Entity.Question
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R
import kotlin.toString

class QuestionAdapter(
    private var questions: List<Question>,
    private val onAnswerChanged: (Long, String) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val etAnswer: EditText = itemView.findViewById(R.id.etAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.tvQuestion.text = question.QuestionText

        if (question.Type == "rating") {
            holder.ratingBar.visibility = View.VISIBLE
            holder.etAnswer.visibility = View.GONE
            holder.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                onAnswerChanged(question.ID, rating.toString())
            }
        } else if (question.Type == "text") {
            holder.etAnswer.visibility = View.VISIBLE
            holder.ratingBar.visibility = View.GONE


            holder.etAnswer.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    onAnswerChanged(question.ID, s.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            })
        }
    }

    override fun getItemCount() = questions.size

    fun updateQuestions(newQuestions: List<Question>) {
        questions = newQuestions
        notifyDataSetChanged()
    }

}