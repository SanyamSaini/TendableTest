import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tendable.test.databinding.ItemCategoryBinding
import com.example.tendable.test.databinding.ItemQuestionBinding
import com.example.tendable.test.interfaces.AnswerClickListener
import com.example.tendable.test.model.CategoryModel
import com.example.tendable.test.model.QuestionModel

sealed class CategoryItem {
    data class CategoryHeader(val category: CategoryModel) : CategoryItem()
    data class QuestionItem(val question: QuestionModel) : CategoryItem()
}

class CategoryAdapter(private val answerClickListener: AnswerClickListener) : ListAdapter<CategoryItem, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 1
        private const val VIEW_TYPE_QUESTION = 2
    }

    class DiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CategoryItem.CategoryHeader -> VIEW_TYPE_CATEGORY
            is CategoryItem.QuestionItem -> VIEW_TYPE_QUESTION
            else -> {VIEW_TYPE_CATEGORY}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> {
                val binding = ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CategoryViewHolder(binding)
            }
            VIEW_TYPE_QUESTION -> {
                val binding = ItemQuestionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                QuestionViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if(item is CategoryItem.CategoryHeader){
            (holder as CategoryViewHolder).bind(item.category)
        } else if (item is CategoryItem.QuestionItem) {
            (holder as QuestionViewHolder).bind(item.question)
        }
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryModel) {
            binding.tvCategoryName.text = category.name
        }
    }

    inner class QuestionViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: QuestionModel) {
            binding.tvQuestionName.text = question.name
            Log.d("CategoryAdapter", "question.answerChoices : ${question.answerChoices}")
            val answerAdapter = AnswerAdapter(question.answerChoices!!, answerClickListener, question.id!!, question.selectedAnswerChoiceId ?: -1)
            binding.rvAnswerChoices.layoutManager = GridLayoutManager(binding.root.context, 2)
            binding.rvAnswerChoices.adapter = answerAdapter
        }
    }
}
