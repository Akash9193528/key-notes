package ap.akash.keynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ap.akash.keynotes.adapter.NotesAdapter
import ap.akash.keynotes.database.DatabaseHelper
import ap.akash.keynotes.databinding.ActivityMainBinding
import ap.akash.keynotes.model.note

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var noteadapter : NotesAdapter
    private lateinit var db : DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.add.setOnClickListener {
          var intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        db = DatabaseHelper(this)
        noteadapter = NotesAdapter(db.getallnote(), this)
         binding.noteRecycler.layoutManager = LinearLayoutManager(this)
        binding.noteRecycler.adapter = noteadapter



    }

    override fun onResume() {
        super.onResume()
        noteadapter.refreshrate(db.getallnote())
    }


}