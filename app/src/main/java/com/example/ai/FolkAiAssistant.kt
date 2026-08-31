package com.example.ai

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import com.example.data.model.InstrumentEntity
import java.util.Locale

data class AiChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "user", "assistant"
    val message: String,
    val relatedInstrument: InstrumentEntity? = null,
    val googleQuery: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

class FolkAiAssistant(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.ENGLISH)
            isTtsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }

    fun speakAloud(text: String) {
        if (isTtsReady && text.isNotBlank()) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "XurXandhanTTS")
        }
    }

    fun stopSpeaking() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }

    fun answerQuery(query: String, instruments: List<InstrumentEntity>): AiChatMessage {
        val q = query.trim().lowercase()

        // Match specific instrument
        val matchedInstrument = instruments.find { inst ->
            q.contains(inst.name.lowercase()) ||
                    q.contains(inst.assameseName) ||
                    (inst.name.equals("pepa", true) && (q.contains("horn") || q.contains("buffalo"))) ||
                    (inst.name.equals("bihu dhol", true) && (q.contains("dhol") || q.contains("drum"))) ||
                    (inst.name.equals("gogona", true) && (q.contains("jaw harp") || q.contains("lahori"))) ||
                    (inst.name.equals("tokari", true) && (q.contains("tokari geet") || q.contains("lute"))) ||
                    (inst.name.equals("sutuli", true) && (q.contains("clay") || q.contains("cuckoo") || q.contains("ocarina"))) ||
                    (inst.name.equals("bhor taal", true) && (q.contains("cymbal") || q.contains("taal") || q.contains("sattriya"))) ||
                    (inst.name.equals("khol", true) && (q.contains("mridanga") || q.contains("borgeet"))) ||
                    (inst.name.equals("bahi", true) && (q.contains("flute") || q.contains("bamboo"))) ||
                    (inst.name.equals("dotara", true) && (q.contains("goalparia") || q.contains("pratima")))
        }

        if (matchedInstrument != null) {
            val response = "Here is what I found about ${matchedInstrument.name} (${matchedInstrument.assameseName}):\n\n" +
                    "• Category: ${matchedInstrument.category}\n" +
                    "• Crafting Materials: ${matchedInstrument.materials}\n" +
                    "• Cultural Role: ${matchedInstrument.culturalSignificance}\n\n" +
                    "${matchedInstrument.about}\n\n" +
                    "Would you like to listen to its synthesized acoustic sound or explore linked Google research?"

            return AiChatMessage(
                sender = "assistant",
                message = response,
                relatedInstrument = matchedInstrument,
                googleQuery = "${matchedInstrument.name} Assam folk musical instrument history and audio"
            )
        }

        // General questions
        val genericResponse = when {
            q.contains("bihu") -> {
                "In Assamese Rongali Bihu celebrations, the primary folk ensemble consists of the Pepa (buffalo hornpipe), Bihu Dhol (double-headed drum), Gogona (bamboo jaw-harp), Sutuli (clay whistle), and Taal (cymbals). Together they create the energetic Husori rhythms that welcome the Assamese New Year!"
            }
            q.contains("sankardev") || q.contains("sattriya") || q.contains("monastery") -> {
                "Mahapurusha Srimanta Sankardev (15th-16th century) integrated the sacred earthenware Khol drum and heavy bronze Bhor Taal into Sattriya classical dance, Ankiya Naat theatre, and Naam-Kirtan prayer services in Majuli and Satras across Assam."
            }
            q.contains("category") || q.contains("types") -> {
                "Assamese traditional musical instruments are broadly classified into 4 classical Indian musicology categories:\n" +
                        "1. Susira (Wind/Aerophones): Pepa, Bahi, Sutuli, Kali, Singha\n" +
                        "2. Avanaddha (Percussion/Membranophones): Bihu Dhol, Khol, Mridanga, Nagara, Doba\n" +
                        "3. Tata (Strings/Chordophones): Tokari, Dotara, Bena, Ektara\n" +
                        "4. Ghana (Solid/Idiophones): Bhor Taal, Gogona, Khutitaal, Toka"
            }
            q.contains("craft") || q.contains("material") || q.contains("made of") -> {
                "Assamese folk instruments are crafted using indigenous bio-materials: seasoned Asian buffalo horns, 3-year-old cured Jati/Bhaluka bamboo, Brahmaputra alluvial clay, jackfruit wood, and artisanal Sarthebari bell metal (Kanh)."
            }
            else -> {
                "I am your Assamese Folk Heritage AI Assistant for Xur-Xandhan. You can ask me about any traditional instrument (Pepa, Dhol, Gogona, Tokari, Sutuli, Bhor Taal, Khol, Bahi, Dotara), their crafting techniques, Bihu folklore, or tap below to search Google!"
            }
        }

        return AiChatMessage(
            sender = "assistant",
            message = genericResponse,
            googleQuery = "$query Assamese folk music instruments Assam"
        )
    }

    companion object {
        fun openGoogleSearch(context: Context, query: String) {
            try {
                val escapedQuery = Uri.encode(query)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=$escapedQuery"))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } catch (_: Exception) {}
        }

        fun openYoutube(context: Context, urlOrQuery: String) {
            try {
                val uri = if (urlOrQuery.startsWith("http")) {
                    Uri.parse(urlOrQuery)
                } else {
                    Uri.parse("https://www.youtube.com/results?search_query=${Uri.encode(urlOrQuery)}")
                }
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } catch (_: Exception) {}
        }
    }
}
