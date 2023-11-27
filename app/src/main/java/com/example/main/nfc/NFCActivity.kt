package com.example.main.nfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NdefRecord.TNF_ABSOLUTE_URI
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.nfc.tech.NfcF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.main.R
import java.nio.charset.Charset
import java.util.Locale


class NFCActivity : AppCompatActivity() {

    private var intentFiltersArray: Array<IntentFilter>? = null
    private var adapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private var techListsArray: Array<Array<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")    /* Handles all MIME based dispatches.
                                     You should specify only the ones that you need. */
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        intentFiltersArray = arrayOf(ndef)
        var techListsArray = arrayOf(arrayOf<String>(NfcF::class.java.name))

        NfcAdapter.getDefaultAdapter(this)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                // Process the messages array.
            }

            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            var tagFromIntent: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            MifareUltralight.get(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG))
            //do something with tagFromIntent

        }
    }

    public override fun onPause() {
        super.onPause()
        adapter?.disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        adapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    private fun createTNFUriRecord() {
        val uriRecord = ByteArray(0).let { emptyByteArray ->
            NdefRecord(
                TNF_ABSOLUTE_URI,
                "https://developer.android.com/index.html".toByteArray(Charset.forName("US-ASCII")),
                emptyByteArray,
                emptyByteArray
            )
        }
    }

    private fun createMime() {
        try {
            val mimeRecord = NdefRecord.createMime(
                "application/vnd.com.example.android.beam",
                "Beam me up, Android".toByteArray(Charset.forName("US-ASCII"))
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //或
/*        val mimeRecord = Charset.forName("US-ASCII").let { usAscii ->
            NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "application/vnd.com.example.android.beam".toByteArray(usAscii),
                ByteArray(0),
                "Beam me up, Android!".toByteArray(usAscii)
            )
        }*/
    }

    //创建一条 TNF_WELL_KNOWN NDEF 记录
    private fun createTextRecord(payload: String, locale: Locale, encodeInUtf8: Boolean): NdefRecord {
        val langBytes = locale.language.toByteArray(Charset.forName("US-ASCII"))
        val utfEncoding = if (encodeInUtf8) Charset.forName("UTF-8") else Charset.forName("UTF-16")
        val textBytes = payload.toByteArray(utfEncoding)
        val utfBit: Int = if (encodeInUtf8) 0 else 1 shl 7
        val status = (utfBit + langBytes.size).toChar()
        val data = ByteArray(1 + langBytes.size + textBytes.size)
        data[0] = status.toByte()
        System.arraycopy(langBytes, 0, data, 1, langBytes.size)
        System.arraycopy(textBytes, 0, data, 1 + langBytes.size, textBytes.size)
        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), data)
    }

    private fun NdefRecord() {
        val rtdUriRecord1 = NdefRecord.createUri("http://example.com")

        //或
        val rtdUriRecord2 = Uri.parse("http://example.com").let { uri ->
            NdefRecord.createUri(uri)
        }

        val uriField = "example.com".toByteArray(Charset.forName("US-ASCII"))
        val payload = ByteArray(uriField.size + 1)                   //add 1 for the URI Prefix
        payload [0] = 0x01                                           //prefixes http://www. to the URI
        System.arraycopy(uriField, 0, payload, 1, uriField.size)     //appends URI to payload
        val rtdUriRecord = NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, ByteArray(0), payload)

    }

    private fun createExternal() {
        var payload: ByteArray = "this is text msg".toByteArray() //assign to your data
        val domain = "com.example" //usually your app's package name
        val type = "externalType"
        val extRecord = NdefRecord.createExternal(domain, type, payload)

        //或

/*        extRecord = NdefRecord(
            NdefRecord.TNF_EXTERNAL_TYPE,
            "com.example:externalType".toByteArray(Charset.forName("US-ASCII")),
            ByteArray(0),
            payload
        )*/
    }
}