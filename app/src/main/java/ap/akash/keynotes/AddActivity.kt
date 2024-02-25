package ap.akash.keynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ap.akash.keynotes.database.DatabaseHelper
import ap.akash.keynotes.databinding.ActivityAddBinding
import ap.akash.keynotes.model.note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddActivity : AppCompatActivity() {
    private val binding : ActivityAddBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }
    private lateinit var db : DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.backbtn.setOnClickListener {
            finish()
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.dateView.setText(sdf).toString()
        db = DatabaseHelper(this)
        binding.savebtn.setOnClickListener {
            val title = binding.titleText.text.toString()
            val description = binding.descriptionText.text.toString()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this@AddActivity, "please enter title and description", Toast.LENGTH_SHORT).show()
            } else {
                var date = binding.dateView.text.toString()
                date = sdf
                val note = note(0, title, description, date)

                db.insertnote(note)
                finish()
                Toast.makeText(this@AddActivity, "Note Saved Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}