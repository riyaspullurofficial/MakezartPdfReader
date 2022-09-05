package app.riyaspullur.makezartpdfreader

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import app.riyaspullur.makezartpdfreader.databinding.ActivityViewPdfBinding
import com.github.barteksc.pdfviewer.PDFView

class ViewPdfActivity : AppCompatActivity() {
    lateinit var bindings: ActivityViewPdfBinding
    var PDF_SELECTION_CODE = 101
    var uriSample: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityViewPdfBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        supportActionBar?.hide()

        bindings.selectPdfFileID.setOnClickListener {
            bindings.mainImageID.visibility = View.GONE
            bindings.pdfView.visibility = View.VISIBLE
            selectPDFfromStorage()
        }
        bindings.closeAppID.setOnClickListener {
            if (uriSample != null) {
                showPDFFromURINumberBasedLoading(uriSample!!,bindings.pageNumberIDEdt.text.toString().toInt())
            } else {
                Toast.makeText(this, "Please Check if file Exist", Toast.LENGTH_SHORT).show()
            }
            /*    finishAffinity()*/
        }
    }

    private fun selectPDFfromStorage() {
        Toast.makeText(this, "Select Pdf file", Toast.LENGTH_SHORT).show()
        val browsStorage = Intent(Intent.ACTION_GET_CONTENT)
        browsStorage.type = "application/pdf"
        browsStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(browsStorage, "Select pdf"), PDF_SELECTION_CODE)
    }

    fun showPDFFromURI(uri: Uri) {
        bindings.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdf = data.data
            uriSample = selectedPdf
            showPDFFromURI(uriSample!!)
        }
    }

    fun showPDFFromURINumberBasedLoading(uri: Uri,pageNumber:Int) {
        bindings.pdfView.fromUri(uri)
            .defaultPage(pageNumber)
            .spacing(10)
            .load()
    }
}