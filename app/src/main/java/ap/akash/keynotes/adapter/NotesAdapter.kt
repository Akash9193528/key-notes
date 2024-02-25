package ap.akash.keynotes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import ap.akash.keynotes.UpdateActivity
import ap.akash.keynotes.databinding.RecyclerViewItemBinding
import ap.akash.keynotes.model.note



class NotesAdapter(private var items: List<note>, context : Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    inner class ViewHolder(private  var binding : RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: note) {
            binding.textId.text = note.title
            binding.description.text = note.content
            binding.currentDate.text = note.date

            binding.description.setOnClickListener {
                val intent = Intent(binding.root.context, UpdateActivity::class.java)
                intent.putExtra("noteId", note.id) // Assuming you have a unique ID for each note
                binding.root.context.startActivity(intent)}


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])

        
    }

    override fun getItemCount(): Int {
       return items.size
    }

    fun refreshrate(newnotes : List<note>){
        items = newnotes
        notifyDataSetChanged()
    }
}