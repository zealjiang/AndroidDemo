package com.example.main.nfc

import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import java.io.IOException
import java.nio.charset.Charset
class MifareUltralightTagTester {

    fun writeTag(tag: Tag, tagText: String) {
        MifareUltralight.get(tag)?.use { ultralight ->
            ultralight.connect()
            Charset.forName("US-ASCII").also { usAscii ->
                ultralight.writePage(4, "abcd".toByteArray(usAscii))
                ultralight.writePage(5, "efgh".toByteArray(usAscii))
                ultralight.writePage(6, "ijkl".toByteArray(usAscii))
                ultralight.writePage(7, "mnop".toByteArray(usAscii))
            }
        }

        fun readTag(tag: Tag): String? {
            return MifareUltralight.get(tag)?.use { mifare ->
                mifare.connect()
                val payload = mifare.readPages(4)
                String(payload, Charset.forName("US-ASCII"))
            }
        }
    }
}