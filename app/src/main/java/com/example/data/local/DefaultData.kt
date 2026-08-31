package com.example.data.local

import com.example.data.model.InstrumentEntity
import com.example.data.model.TrackEntity

object DefaultData {
    val defaultInstruments = listOf(
        InstrumentEntity(
            id = 1,
            name = "Pepa",
            assameseName = "পেঁপা",
            category = "Wind (সুষিৰ)",
            tagline = "Hornpipe of the Bihu dance, crafted from buffalo horn",
            about = "The Pepa is arguably the most recognizable wind instrument of Assamese folk culture. Traditionally carved from the horn of a domestic Asian water buffalo (ম'হৰ শিং) and fitted with a small bamboo reed (থেকা), it produces a piercing, shrill, and melancholic melody that signals the onset of Rongali Bihu in the spring.",
            materials = "Asiatic water buffalo horn (Mohor Sing), cured bamboo reed (Ghar/Theka), brass/cloth rings.",
            culturalSignificance = "Central to Bihu Husori and Bohag Bihu. Historically played by young herdsmen and Bihu dancers across the Brahmaputra valley to usher in fertility, spring blossoms, and romance.",
            playingTechnique = "Circular breathing with intense embouchure pressure. Fingers modulate the 4 to 6 pitch holes along the attached bamboo tube.",
            imageUrl = "https://images.unsplash.com/photo-1511192336575-5a79af67a629?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Assam+Pepa+folk+instrument+solo",
            synthPreset = "pepa"
        ),
        InstrumentEntity(
            id = 2,
            name = "Bihu Dhol",
            assameseName = "ঢোল",
            category = "Percussion / Drum (আৱনদ্ধ)",
            tagline = "The thunderous heartbeat of Rongali Bihu and Husori",
            about = "The Assamese Dhol is a two-headed barrel drum made of seasoned jackfruit wood (Kothal) or Holong wood. The right head (Daini) is smaller and produces a sharp, crackling rim-shot, while the left head (Bewa) yields a deep, resonant bass thump. Played with a bamboo stick (Mari) in the right hand and bare left hand.",
            materials = "Jackfruit wood body, cured cowhide/goathide membrane, braided leather tension thongs.",
            culturalSignificance = "Considered sacred in Assamese folklore. Believed to have been gifted to mankind from the heavens to awaken dormant earth and drive away negative energy during Bohag Bihu.",
            playingTechnique = "Complex rhythmic patterns (Dholar Malita / Chap) struck at blazing tempos accompanied by vocal Bol syllables (Kheta-kheta-dha).",
            imageUrl = "https://images.unsplash.com/photo-1519892300165-cb5542fb47c7?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Assam+Bihu+Dhol+solo+masterclass",
            synthPreset = "dhol"
        ),
        InstrumentEntity(
            id = 3,
            name = "Gogona",
            assameseName = "গগনা",
            category = "Solid / Metallic (ঘন)",
            tagline = "Bamboo jaw harp played by Assamese Bihu dancers",
            about = "The Gogona is an intimate Assamese idiophone made from a single piece of dried, cured bamboo (Bhaluka or Jati bamboo). When pressed against the teeth and struck with the right index finger, the mouth cavity acts as a variable resonator producing vibrant, twanging overtones.",
            materials = "Aged seasoned bamboo (৩-৪ বছৰ পুৰণি জাতি বাঁহ), shaped with fine carving blades.",
            culturalSignificance = "Worn tucked in the hair knot (Khupa) of female Bihu dancers (Bihuwotis). Comes in two forms: the slender 'Lahori Gogona' and the broader 'Ram Gogona'.",
            playingTechnique = "Held gently between the front teeth; breath and tongue movement manipulate pitch while the flexible tongue strip is tapped rhythmically.",
            imageUrl = "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Lahori+Gogona+Assam+folk+music",
            synthPreset = "gogona"
        ),
        InstrumentEntity(
            id = 4,
            name = "Tokari",
            assameseName = "টোকোৰী",
            category = "String (তত)",
            tagline = "Ancient plucked string lute of mystical Tokari Geet",
            about = "The Tokari is an ancient folk chordophone resembling a sarod or lute. Carved out of a single block of wood (usually Kathal or Gamari), its sound chamber is covered in monitor lizard skin (Gui-sap) or goat parchment, strung with 4 silk or steel strings.",
            materials = "Single piece wood hollow, reptile skin/goathide resonator, brass frets, silk/muga strings.",
            culturalSignificance = "Inseparable from spiritual wandering minstrels and 'Tokari Geet', singing of Vaishnavite philosophy, impermanence of life, and devotion to Krishna.",
            playingTechnique = "Plucked with a triangular wooden plectrum (Kathi) while the left hand presses strings against the smooth fingerboard.",
            imageUrl = "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Tokari+Geet+Assam+folk+instrument",
            synthPreset = "tokari"
        ),
        InstrumentEntity(
            id = 5,
            name = "Sutuli",
            assameseName = "সুতুলি",
            category = "Wind (সুষিৰ)",
            tagline = "Earthen vessel flute evoking the melody of spring cuckoos",
            about = "The Sutuli is an Assamese folk ocarina/whistle made from riverbed clay, fashioned into a crescent or fruit shape and sun-baked before firing. It produces an airy, haunting whistle mimicking the Keteki (Indian cuckoo) and spring breezes.",
            materials = "Brahmaputra alluvial clay, natural terracotta glaze.",
            culturalSignificance = "Played during early Bihu mornings in rural fields and forests to welcome rain and green paddy shoots.",
            playingTechnique = "Blowing gently into the mouthpiece while shifting two to three finger holes on the body.",
            imageUrl = "https://images.unsplash.com/photo-1507838153414-b4b713384a76?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Sutuli+instrument+Assam+Bihu",
            synthPreset = "sutuli"
        ),
        InstrumentEntity(
            id = 6,
            name = "Bhor Taal",
            assameseName = "ভোৰতাল",
            category = "Solid / Metallic (ঘন)",
            tagline = "Resonant heavy bronze cymbals of Srimanta Sankardev",
            about = "The Bhor Taal consists of a pair of large, heavy, bell-metal (Kanh) cymbals weighing up to 2-3 kilograms. When clashed together, they generate a metallic, shimmering roar that resonates for dozens of seconds.",
            materials = "Traditional Assamese bell-metal / Bronze (কাঁহ) alloy from Sarthebari.",
            culturalSignificance = "Pioneered by Mahapurusha Srimanta Sankardev and Madhavdev for Naam-Kirtan and Bhortaal Nritya in Satras (monasteries).",
            playingTechnique = "Clashed at dynamic angles accompanied by acrobatic dance leaps, spins, and rhythmic swinging.",
            imageUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Bhortaal+Nritya+Assam+Sattriya",
            synthPreset = "bhortaal"
        ),
        InstrumentEntity(
            id = 7,
            name = "Khol (মৃদঙ্গ)",
            assameseName = "খোল",
            category = "Percussion / Drum (আৱনদ্ধ)",
            tagline = "Sacred terracotta barrel drum of Sattriya classical heritage",
            about = "The Khol is a classical double-conical earthenware drum perfected in the Vaishnavite Satras of Assam. The right head produces high harmonic ringing pitches while the left head delivers deep bass resonance.",
            materials = "Clay body (আমাটি), braided leather strapping, rice paste & iron oxide loaded tuning black spot (Ghab).",
            culturalSignificance = "The rhythmic foundation of Borgeet, Ankiya Naat (theatre), and Sattriya Nritya (one of India's 8 classical dance forms).",
            playingTechnique = "Intricate finger dexterity, palm slaps, and knuckle strikes matching classical Taals (Ektal, Chutital, Thokoni).",
            imageUrl = "https://images.unsplash.com/photo-1520523839898-5071282543e2?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Assam+Khol+Bayan+performance",
            synthPreset = "dhol"
        ),
        InstrumentEntity(
            id = 8,
            name = "Bahi (বাঁহী)",
            assameseName = "বাঁহী",
            category = "Wind (সুষিৰ)",
            tagline = "Pure bamboo flute whispering melodies of the hills & plains",
            about = "The Assamese Bahi is crafted from high-grade, naturally aged bamboo. It features 6 or 7 finger holes tuned precisely to Assamese pentatonic and hexatonic folk scales.",
            materials = "Selected hill bamboo (Nal / Muli bamboo), natural beeswax sealant.",
            culturalSignificance = "Associated with pastoral shepherds, tea garden melodies (Jhumur), and mystical devotional Borgeet.",
            playingTechnique = "Side-blown embouchure with half-hole microtonal inflections (Meend and Gamak).",
            imageUrl = "https://images.unsplash.com/photo-1541689592655-f5f52825a3b8?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Assamese+Bamboo+Flute+Bahi+melody",
            synthPreset = "bahi"
        ),
        InstrumentEntity(
            id = 9,
            name = "Dotara",
            assameseName = "দোতাৰা",
            category = "String (তত)",
            tagline = "Plucked folk lute of Goalparia & Kamrupi Lokageet",
            about = "The Dotara (meaning 'two strings', though often bearing 4 or 5 strings) has a fretless fingerboard covered in brass, giving it a bright, gliding timbre essential for the soul-stirring ballads of Goalpara.",
            materials = "Neem or jackfruit wood body, goathide soundboard, brass plate, silk/steel strings.",
            culturalSignificance = "Made world-famous by folk legend Pratima Barua Pandey and Bhupen Hazarika in evergreen Assamese melodies.",
            playingTechnique = "Continuous strumming with a wooden plectrum with left hand fingers sliding effortlessly across the fretless brass plate.",
            imageUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Goalparia+Dotara+Assam+folk+songs",
            synthPreset = "tokari"
        )
    )

    val defaultTracks = listOf(
        TrackEntity(
            id = 1,
            title = "Rongali Bihu Pepa & Dhol Symphony",
            artist = "Bihu Husori Ensembles of Sibsagar",
            instrumentName = "Pepa & Bihu Dhol",
            category = "Wind & Percussion",
            durationSeconds = 195,
            synthPreset = "pepa",
            coverImageUrl = "https://images.unsplash.com/photo-1511192336575-5a79af67a629?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Rongali+Bihu+Pepa+Dhol+Assam"
        ),
        TrackEntity(
            id = 2,
            title = "Whispering Spring: Sutuli & Gogona Duet",
            artist = "Majuli Folk Masters",
            instrumentName = "Sutuli & Gogona",
            category = "Acoustic Folk",
            durationSeconds = 160,
            synthPreset = "gogona",
            coverImageUrl = "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Gogona+Sutuli+duet+Assam"
        ),
        TrackEntity(
            id = 3,
            title = "Sattriya Naam-Kirtan & Bhortaal Echoes",
            artist = "Kamalabari Satra Monks",
            instrumentName = "Bhor Taal & Khol",
            category = "Devotional Classic",
            durationSeconds = 240,
            synthPreset = "bhortaal",
            coverImageUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Sattriya+Bhortaal+Nritya+Majuli"
        ),
        TrackEntity(
            id = 4,
            title = "Mystic Tokari Geet on River Brahmaputra",
            artist = "Oja-Pali & Tokari Minstrels",
            instrumentName = "Tokari",
            category = "Spiritual Folk",
            durationSeconds = 210,
            synthPreset = "tokari",
            coverImageUrl = "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Mystic+Tokari+Geet+Assam"
        ),
        TrackEntity(
            id = 5,
            title = "Serenade of the Bamboo Flute (Bahi)",
            artist = "Kaziranga Pastoral Collective",
            instrumentName = "Bahi",
            category = "Melodic Wind",
            durationSeconds = 180,
            synthPreset = "bahi",
            coverImageUrl = "https://images.unsplash.com/photo-1541689592655-f5f52825a3b8?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Assam+Flute+Bahi+pastoral+rhythm"
        ),
        TrackEntity(
            id = 6,
            title = "Goalparia Dotara Ballads of Pratima Barua",
            artist = "Dhubri Heritage Folk Troupe",
            instrumentName = "Dotara",
            category = "Strings & Lore",
            durationSeconds = 225,
            synthPreset = "tokari",
            coverImageUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=800&auto=format&fit=crop&q=80",
            youtubeUrl = "https://www.youtube.com/results?search_query=Goalparia+Dotara+Lokageet"
        )
    )
}
