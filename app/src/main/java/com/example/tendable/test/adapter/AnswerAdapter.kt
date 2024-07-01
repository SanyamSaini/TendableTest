import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tendable.test.databinding.ItemAnswerChoiceBinding
import com.example.tendable.test.interfaces.AnswerClickListener
import com.example.tendable.test.model.AnswerChoiceModel

class AnswerAdapter(
    private val answerChoices: ArrayList<AnswerChoiceModel>,
    private val answerClickListener: AnswerClickListener,
    private val questionId : Int,
    private val selectedAnswer : Int
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    private var selectedPosition: Int = -10

    inner class AnswerViewHolder(private val binding: ItemAnswerChoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answerChoice: AnswerChoiceModel, position: Int, answerClickListener: AnswerClickListener) {
            binding.rbAnswerChoice.text = answerChoice.name
            binding.rbAnswerChoice.isChecked = (position == selectedPosition)

            if(selectedAnswer != -10 && answerChoice.id == selectedAnswer){
                binding.rbAnswerChoice.isChecked = true
            }

            binding.rbAnswerChoice.setOnClickListener{
                answerClickListener.answerClick(answerChoice, questionId)
                this@AnswerAdapter.selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerChoiceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = answerChoices[holder.adapterPosition]
        holder.bind(answer, holder.adapterPosition, answerClickListener)
    }

    override fun getItemCount(): Int = answerChoices.size
}
