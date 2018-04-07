package csne.speechwithinreach

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.TextView

/**
 * Created by cesch on 4/7/2018.
 */
class SpeechListener(private val context: Context,
                     private val callback: RecordSpeech,
                     private val returnedText: TextView) : RecognitionListener {

    private lateinit var sRecognizer : SpeechRecognizer
    private lateinit var rIntent : Intent
    private var isListening = false

    fun initialize() {
        rIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        rIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        rIntent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true)
        rIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            sRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            sRecognizer.setRecognitionListener(this)
        }
    }

    fun startListening() {
        if (!isListening) {
            isListening = true
            sRecognizer.startListening(rIntent)
        }
    }

    fun stopRecognition() {
        sRecognizer.stopListening()
    }

    private fun onResults(results: List<String>, scores: FloatArray?) {
        val text = results.joinToString(separator = "\n")
        returnedText.text = text
    }

    override fun onResults(results: Bundle) {
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
        if (matches != null) {
            onResults(matches, scores)
        }
        isListening = false
    }

    override fun onReadyForSpeech(params: Bundle) {
        // why
    }

    override fun onRmsChanged(p0: Float) {
        // why
    }

    override fun onBufferReceived(p0: ByteArray?) {
        // why
    }

    override fun onPartialResults(p0: Bundle?) {
        // why
    }

    override fun onError(p0: Int) {
        // why
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        // why
    }

    override fun onBeginningOfSpeech() {
        // why
    }

    override fun onEndOfSpeech() {
        // why
    }
}