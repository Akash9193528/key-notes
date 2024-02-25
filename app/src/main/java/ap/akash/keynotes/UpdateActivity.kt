package ap.akash.keynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ap.akash.keynotes.adapter.NotesAdapter
import ap.akash.keynotes.database.DatabaseHelper
import ap.akash.keynotes.databinding.ActivityUpdateBinding
import ap.akash.keynotes.model.note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateActivity : AppCompatActivity() {
    private val binding : ActivityUpdateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }
    private lateinit var db :DatabaseHelper

    private var noteid : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = DatabaseHelper(this)

     noteid = intent.getIntExtra("noteId", -1)
   if (noteid == -1){
       finish()
       return
   }
    var note = db.getnotebyId(noteid)
        binding.updatetitile.setText(note.title)
        binding.updatedescription.setText(note.content)
        binding.updatedate.setText(note.date)


        binding.updatebtn.setOnClickListener {
            val title = binding.updatetitile.text.toString()
            val content = binding.updatedescription.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
             var date = binding.updatedate.text.toString()
            date = sdf
            val update = note(note.id, title, content, date)

           db.updatenote(update)
            finish()
            Toast.makeText(this@UpdateActivity, "updated successfully", Toast.LENGTH_SHORT).show()

        }


        binding.deletebtn.setOnClickListener {

            var alertDialogBuilder = AlertDialog.Builder(binding.root.context)
            alertDialogBuilder.setTitle("Delete Note")
            alertDialogBuilder.setMessage("Are you sure you want to delete this note?")

            alertDialogBuilder.setPositiveButton("Delete") { dialog, which ->
                db.deletenote(noteid)
                finish()
                dialog.dismiss()
            }

            alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->

                dialog.dismiss()
            }


             alertDialogBuilder.create()
            alertDialogBuilder.show()
        }


        }
    }




