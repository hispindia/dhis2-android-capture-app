package org.dhis2.usescases.report

import android.annotation.SuppressLint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.dhis2.R
import org.dhis2.commons.Constants
import org.dhis2.databinding.ActivityReportBinding
import org.hisp.dhis.android.core.D2Manager
import org.hisp.dhis.android.core.event.Event
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttributeValue
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teiUid = intent.getStringExtra(Constants.TEI_UID)
        val programUid = intent.getStringExtra(Constants.PROGRAM_UID)
        val enrollmentUid = intent.getStringExtra(Constants.ENROLLMENT_UID)

        if (teiUid != null && programUid != null && enrollmentUid != null) {
            if (programUid == "ICxr5ByOXkV" || programUid == "qlHnTeSDOfP") {

                fetchAndDisplayReport(teiUid, enrollmentUid)
            } else {

                displayErrorMessage("Program UID not supported!")
            }

            binding.backButton.setOnClickListener {
                finish()
            }


            binding.printButton.setOnClickListener {
                printToPdf()
            }
        }
    }
    private fun printToPdf() {

        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(binding.root.width, binding.root.height, 1).create()
        val page = document.startPage(pageInfo)


        binding.root.draw(page.canvas)
        document.finishPage(page)


        try {
            val filePath = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Report.pdf")
            val outputStream = FileOutputStream(filePath)
            document.writeTo(outputStream)
            document.close()
            outputStream.close()

            Toast.makeText(this, "PDF saved to ${filePath.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchAndDisplayReport(teiUid: String, enrollmentUid: String) {
        val enrollmentDate = D2Manager.getD2().enrollmentModule().enrollments()
                .byUid()
                .eq(enrollmentUid)
                .blockingGet()
                .firstOrNull()
                ?.enrollmentDate()


        val formattedDate = enrollmentDate?.let {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.format(it)
        } ?: "Unknown"

        binding.registrationDate.text = "Registration Date: $formattedDate"

        val attributes = D2Manager.getD2().trackedEntityModule()
                .trackedEntityAttributeValues()
                .byTrackedEntityInstance()
                .eq(teiUid)
                .blockingGet()


        displayAttributes(attributes)


        val events = D2Manager.getD2().eventModule()
                .events()
                .byEnrollmentUid()
                .eq(enrollmentUid)
                .blockingGet()


        if (events.isNotEmpty()) {

            val eventIds = events.map { it.uid() }
            val dataValues = D2Manager.getD2().trackedEntityModule()
                    .trackedEntityDataValues()
                    .byEvent()
                    .`in`(eventIds)
                    .blockingGet()


            val eventDataMap = dataValues.groupBy { it.event() }


            binding.dynamicEventContainer.removeAllViews()
            events.forEach { event ->
                val eventId = event.uid()
                val eventData = eventDataMap[eventId]

                val boxLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(16, 16, 16, 16)
                    setBackgroundResource(R.drawable.rounded_border)
                    layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(8, 8, 8, 8)
                    }
                }
                // Add event-specific data to the box
                eventData?.forEach { dataValue ->
                    val labelText = dataValue.dataElement()?.let { getLabelForDataElement(it) }
                    if (labelText != null) {
                        val textView = TextView(this).apply {
                            text = "$labelText: ${dataValue.value()}"
                            textSize = 14f
                        }
                        boxLayout.addView(textView)
                    }
                }

                binding.dynamicEventContainer.addView(boxLayout)
            }
        } else {
            displayErrorMessage("No events found for the given enrollment.")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayAttributes(attributes: List<TrackedEntityAttributeValue>) {
        val attributeMap = attributes.associateBy { it.trackedEntityAttribute() }
        binding.crNumber.text = "Medical Record Number: ${attributeMap["uo3FuH69WXH"]?.value() ?: "N/A"}"

        binding.patientName.text = "Name: ${
            listOfNotNull(
                    attributeMap["fDlJhn3yD3d"]?.value(),
                    attributeMap["u3zzlEKjw9l"]?.value(),
                    attributeMap["x8FG7jaEGdz"]?.value()
            ).joinToString(" ")
        }"
        binding.gender.text = "Gender: ${attributeMap["R7d96ZGjJS8"]?.value() ?: "N/A"}"
        binding.age.text = "DOB: ${attributeMap["aY7sH2CC9V8"]?.value() ?: "N/A"}"
    }

    private fun getLabelForDataElement(dataElementUid: String): String? {
        return when (dataElementUid) {
            "Gkmu7ySPxjb" -> "Hospital Department"
            "q7U3sRRnFg5" -> "Location"
            "GqP6sLQ1Wt3" -> "Sample Type"
            "si9RY754UNU" -> "Lab ID"
            "VsNSbOlwed9" -> "Culture result"
            "JxRbAbJ0rmU" -> "Amikacin_Result"
            "z9EgEcEFPkA" -> "Amoxicillin/Clavulanic acid_Result"
            "OGDc22lgUiT" -> "Amoxicillin_Result"
            "zIYr6aShKtY" -> "Amphotericin B_Result"
            "cUfjGEigw8g" -> "Ampicillin_Result"
            "zYqn6vYRvdn" -> "Ampicillin/Sulbactam_Result"
            "KMlAfRtpUrv" -> "Anidulafungin_Result"
            "xI5Fh7tTTVr" -> "Azithromycin_Result"
            "ycdFeO0znYg" -> "Cefepime_Result"
            "OwSUkx5i36n" -> "Cefixime_Result"
            "syTqVqAxGDM" -> "Cefotaxime_Result"
            "vsEIR8zMPj1" -> "Cefotetan_Result"
            "kk15uVyCVnc" -> "Cefoxitin_Result"
            "eDMv1MLmC53" -> "Ceftazidime_Result"
            "fmSfNS8SlEb" -> "Ceftriaxone_Result"
            "kj5yLSWBxzw" -> "Cefuroxime_Result"
            "eRdS7IDn1jk" -> "Chloramphenicol_Result"
            "DcpZWFI5zXt" -> "Ciprofloxacin_Result"
            "yOH9HC5gPot" -> "Clarithromycin_Result"
            "gMBmEElKjbl" -> "Clavulanic acid_Result"
            "ZJ8lWzsjsgy" -> "Clindamycin_Result"
            "jTtLlXtOCR9" -> "Colistin_Result"
            "d7eVMWSqpfb" -> "Doxycycline_Result"
            "G07CaMJ6glh" -> "Erythromycin_Result"
            "DOj9HjzYpe2" -> "Fluconazole_Result"
            "XEtjhmXOwte" -> "Gentamicin HL_Result"
            "XWIlcbB1ubS" -> "Gentamicin_Result"
            "rM6Rg4q865h" -> "Imipenem_Result"
            "lYfGvp3FjiI" -> "Isavuconazole_Result"
            "uCHeY9f7ycQ" -> "Itraconazole_Result"
            "AKhfG4UdTvl" -> "Kanamycin_Result"
            "xIU9ryhKWZn" -> "Levofloxacin_Result"
            "mH6xNIxmXE6" -> "Meropenem_Result"
            "ZUXa76bCXpP" -> "Metronidazole_Result"
            "o7ko97ifOUQ" -> "Micafungin_Result"
            "yTnKBaFuKSf" -> "Nalidixic acid_Result"
            "iacYPlIIIhV" -> "Nitrofurantoin_Result"
            "iRe50Pfz7am" -> "Novobiocin_Result"
            "UAOg7f0EVd4" -> "Optochin_Result"
            "QXiF9Sl72iy" -> "Oxacillin_Result"
            "W13Kshwn3j4" -> "Penicillin_Result"
            "lG3vF2YI4Yf" -> "Piperacillin_Result"
            "MwzwH7aUsbZ" -> "Piperacillin/Sulbactam_Result"
            "RlmcWjz2w6o" -> "Piperacillin-tazobactam_Result"
            "U1UJqmnzZa6" -> "Posaconazole_Result"
            "t762aNcQH03" -> "Sulfamethoxazole_Result"
            "RcEz5Ni2VIy" -> "Sulfonamides_Result"
            "kguWskGWWQ3" -> "Tazobactam_Result"
            "gzTyTYMQuQA" -> "Tetracycline_Result"
            "RJh7lKWC7Qf" -> "Tigecycline_Result"
            "ExPXJzzJJxY" -> "Time of Result report"
            "H43KnPm9xPb" -> "Tobramycin_Result"
            "K91OC69r7US" -> "Trimethoprim-sulfamethoxazole_Result"
            "BpMcy7erHkf" -> "Vancomycin_Result"
            "UhfSmUFqI3C" -> "Voriconazole_Result"


            else -> null
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun displayErrorMessage(message: String) {
        val errorBox = TextView(this).apply {
            text = message
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
        }
        binding.dynamicEventContainer.addView(errorBox)
    }
}
