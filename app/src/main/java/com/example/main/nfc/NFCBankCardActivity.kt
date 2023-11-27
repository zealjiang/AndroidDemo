package com.example.main.nfc

import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.cardemulation.HostApduService
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.main.R


class NFCBankCardActivity : AppCompatActivity() {

    private val TAG = "NFCBankCardActivity"
    private var tvRead: androidx.appcompat.widget.AppCompatTextView? = null
    private var tvWrite: androidx.appcompat.widget.AppCompatTextView? = null
    private var textView: androidx.appcompat.widget.AppCompatTextView? = null
    private var nfcUtil: NfcUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_bank_card)
        tvRead = findViewById(R.id.tv_read)
        tvWrite = findViewById(R.id.tv_write)
        textView = findViewById(R.id.textview)

        var hasNfc = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
        Log.d(TAG, "hasNfc = $hasNfc")

        //nfcAdapter?.setNdefPushMessageCallback(this, this)

        nfcUtil = NfcUtil()
        val adapter = nfcUtil!!.nfcCheck(this)
        if (adapter == null) {
            //Toast.makeText(this, "设备不支持NFC", Toast.LENGTH_SHORT).show()
            finish()
        }

        nfcUtil!!.nfcInit(this)
        tvRead?.setOnClickListener {

        }

        tvWrite?.setOnClickListener {
            //nfcUtil?.writeNFCToTag("1234567890")
        }
    }

    override fun onResume() {
        super.onResume()
        nfcUtil?.mNfcAdapter?.enableForegroundDispatch(this, nfcUtil?.mPendingIntent, nfcUtil?.mIntentFilter, nfcUtil?.mTechLists)
        nfcUtil?.resolveIntent(intent)
    }

    override fun onPause() {
        super.onPause()
        nfcUtil?.mNfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "intent = ${intent}, action = ${intent.action}, nfcUtil = $nfcUtil")
        if (nfcUtil == null) {
            nfcUtil = NfcUtil()
        }

        val messages = nfcUtil?.resolveIntent(intent)
        processNDEMsg(messages)
    }

    private fun handleNfcTag(tag: Tag) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()

        // 在这里执行读取银行卡信息的操作
        // 可以使用isoDep.transceive()方法与标签进行通信

        isoDep.close()
    }

    /**
     * 处理Intent携带的数据
     */
    private fun processIntent(intent: Intent) {
        // 处理北京公交卡的数据
        var extras: Bundle? = intent.extras ?: return
        Log.d("NFCActivity", "extras = $extras")
        //var content = bytesToHex((tag!!.get("android.nfc.extra.TAG") as Tag).id)
        //textView!!.text = content
        //Toast.makeText(this, "获取北京地铁卡数据:" + content, Toast.LENGTH_LONG).show()


        // IsoDep卡片通信的工具类，Tag就是卡
        val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as Tag?
        if (tag == null) {
            val info = "读取卡信息失败"
            textView?.text = info
            return
        } else {
            val cardId = bytesToHex(tag.id)
            textView?.text = cardId
        }
/*        val isoDep = IsoDep.get()
        if (isoDep == null) {
            val info = "读取卡信息失败"
            return
        }*/

/*        if (extras != null) {
            val parcelledData = extras.getParcelable<Parcel>(NfcAdapter.EXTRA_TAG)
            if (parcelledData != null) {
                parcelledData.setDataPosition(0)
                val tag = Tag.CREATOR.createFromParcel(parcelledData)

                // 在这里处理Tag对象，提取所需的信息
                // 例如，你可以获取标签ID、技术列表等

                // 示例：获取标签ID
                val tagId = bytesToHex(tag.id)
                Log.d(TAG, "Tag ID: $tagId")
            }
        }*/
    }

/*    override fun createNdefMessage(event: NfcEvent?): NdefMessage {
        val text = "Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis()
        return NdefMessage(
            arrayOf(
                createMime("application/vnd.com.example.android.beam", text.toByteArray())
            )
        )
    }*/

    fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val value = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = HEX_ARRAY[value ushr 4]
            hexChars[i * 2 + 1] = HEX_ARRAY[value and 0x0F]
        }
        return String(hexChars)
    }

    private val HEX_ARRAY = "0123456789ABCDEF".toCharArray()

    class MyHostApduService : HostApduService() {

        override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {
            return byteArrayOf(0x00.toByte())
        }

        override fun onDeactivated(reason: Int) {

        }
    }

    private fun processNDEMsg(messages: Array<NdefMessage>?) {

    }

}