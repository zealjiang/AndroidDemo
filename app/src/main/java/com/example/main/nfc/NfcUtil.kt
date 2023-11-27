package com.example.main.nfc

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.Ndef
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.nio.charset.Charset
import java.util.Locale

class NfcUtil {

    /**
     * NFC_TAG(卡)----->NFC_READER(手机)
     *                  1. NFC_TAG(卡)靠近NFC_READER(手机)
     *                  2、Intent检测（NfcAdapter.ACTION_NDEF_DISCOVERED）
     *                  3、定义TAG对象（intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)）
     *                  4、获取NDEF消息（intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)）
     *                  5、解析NDEF消息（NdefMessage.getRecords()  NdefRecord.getPayload()）
     *                  6、UI呈现等
     *
     * NDEF 消息解析：
     *     1、URI 类型 TNF_ABSOLUTE_URI 和 TNF_WELL_KNOWN RTD_URI
     *     2、TEXT 类型 TNF_WELL_KNOWN RTD_TEXT
     *     3、MIME 类型 TNF_MIME_MEDIA
     *     4、EXTERNAL 类型 TNF_EXTERNAL_TYPE
     *
     *
     */


    val TAG = "NfcUtil"
    var mNfcAdapter: NfcAdapter? = null
    var mIntentFilter: Array<IntentFilter>? = null
    var mPendingIntent: PendingIntent? = null
    var mTechLists: Array<Array<String>>? = null
    val CUSTOM_KEY_A = byteArrayOf(
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0x66.toByte()
    )

    fun nfcCheck(activity: Activity): NfcAdapter? {
        var mNfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (mNfcAdapter == null) {
            // 设备不支持NFC
            Toast.makeText(activity.applicationContext, "设备不支持NFC", Toast.LENGTH_SHORT).show()
            return null
        } else if (!mNfcAdapter!!.isEnabled) {
            // 设备支持NFC但未启用
            var intent = Intent(Settings.ACTION_NFC_SETTINGS)
            activity.startActivity(intent)
        } else {
            // 设备支持NFC且已启用
        }

        return mNfcAdapter
    }

    fun nfcInit(activity: Activity) {
        var flag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        mPendingIntent = PendingIntent.getActivity(
            activity,
            0,
            Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            flag
        )

//        var ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
//        var tagf = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
//        try {
//            ndef.addDataType("*/*")
//        } catch (e: IntentFilter.MalformedMimeTypeException) {
//            e.printStackTrace()
//        }
//        mIntentFilter = arrayOf(ndef, tagf)
//        mTechLists = null//arrayOf(arrayOf<String>(android.nfc.tech.NfcF::class.java.name))

    }

    /**
     * 读取NFC数据
     */
    fun readNFCFromTag(intent: Intent): String? {
        var rawArray: Array<Parcelable>? =
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        Log.d(TAG, "readNFCFromTag = ${rawArray}, intent = ${intent}")
        if (rawArray != null) {
            var ndefMsg: NdefMessage? = rawArray[0] as NdefMessage
            var ndefRecord: NdefRecord? = ndefMsg?.records?.get(0) as NdefRecord
            if (ndefRecord != null) {
                var readResult = String(ndefRecord.payload, Charset.forName("UTF-8"))
                return readResult
            }

        }
        return ""
    }

    fun writeNFCToTag(data: String, intent: Intent) {
        var tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        var ndef: Ndef = Ndef.get(tag)
        ndef.connect()
        var ndefRecord: NdefRecord = NdefRecord.createTextRecord(null, data)
        var ndefMessage = NdefMessage(arrayOf(ndefRecord))
        ndef.writeNdefMessage(ndefMessage)
    }

    fun readNFCId(intent: Intent): String {
        var tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            return ByteArrayToHexString(tag.id)
        }
        return ""
    }

    fun ByteArrayToHexString(inarray: ByteArray): String {
        var i: Int
        var j: Int
        var hexArray = "0123456789ABCDEF".toCharArray()
        var hex = ""
        i = 0
        while (i < inarray.size) {
            j = inarray[i].toInt() and 0xff
            if (j < 16)
                hex += "0"
            hex += Integer.toHexString(j)
            i++
        }
        return hex.uppercase()
    }


    fun resolveIntent(intent: Intent): Array<NdefMessage>? {
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            var messages: Array<NdefMessage> ?= null
            val tag: Tag ?= intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            var rawMessages: Array<Parcelable>? = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMessages != null) {
                messages = rawMessages.map { it as NdefMessage }.toTypedArray()
            } else {
                // Unknown tag type
                val empty = ByteArray(0)
                var record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty)
                val msg = NdefMessage(arrayOf(record))
                messages = arrayOf(msg)
            }
            return messages
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED == action) {

        } else if (NfcAdapter.ACTION_TAG_DISCOVERED == action) {

        }
        return null
    }

    fun getRecordFromNdefMessages(messages: Array<NdefMessage>?){
        if (messages.isNullOrEmpty()) {
            Toast.makeText(null, "NFC数据为空", Toast.LENGTH_SHORT).show()
            return
        }


        messages.forEach {
            var records: Array<NdefRecord> = it.records
            records.forEach {
                val uri = NFCRecordParse.parseWellKnownUriRecord(it)
                Log.d(TAG, "getRecordFromNdefMessages uri = $uri")
            }
        }
    }


    private fun supportTech(intent: Intent) {
        var isSupport = false
        val action = intent.action
        var parcelable: Parcelable? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (parcelable != null) {
            var tag: Tag = parcelable as Tag
            var techList = tag.techList
            techList.forEach {
                Log.d(TAG, "supportTech = $it")
                if (it.equals("android.nfc.tech.IsoDep")) {

                } else if(it.equals("android.nfc.tech.MifareClassic")) {
                    isSupport = true
                    var mfc = MifareClassic.get(tag)
                    if (mfc != null) {
                        readMifare(mfc)
                    } else {
                        Log.d(TAG, "tag not support MifareClassic")
                    }
                } else if(it.equals("android.nfc.tech.MifareUltralight")) {
                    isSupport = true
                } else if(it.equals("android.nfc.tech.Ndef")) {
                    isSupport = true
                } else if(it.equals("android.nfc.tech.NdefFormatable")) {

                } else if(it.equals("android.nfc.tech.NfcA")) {
                    isSupport = true
                } else if(it.equals("android.nfc.tech.NfcB")) {

                } else if(it.equals("android.nfc.tech.NfcBarcode")) {

                } else if(it.equals("android.nfc.tech.NfcF")) {

                } else if(it.equals("android.nfc.tech.NfcV")) {

                }
            }
        }
    }

    fun readMifare(mfc: MifareClassic) {
        var isAuth = false
        try {
            mfc.connect()
            val secCount = mfc.sectorCount
            Log.d(TAG, "sectorCount = $secCount")
            for (i in 0 until secCount) {
                //Authenticate a sector with key A.
                if (mfc.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT)) {
                    isAuth = true
                } else if(mfc.authenticateSectorWithKeyA(i, CUSTOM_KEY_A)) {
                    isAuth = true
                } else {
                    isAuth = false
                    Log.d(TAG, "authenticateSectorWithKeyA failed")
                }

                if (isAuth) {
                    val bCount = mfc.getBlockCountInSector(i)
                    Log.d(TAG, "bCount = $bCount")
                    //val bIndex = mfc.sectorToBlock(i)
                    for (j in 0 until bCount) {
                        val data = mfc.readBlock(j)
                        Log.d(TAG, "data = ${data}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 共16个扇区， 0-15， 共8kb,即8192字节
     * 第个扇区有4个块， 0-3，最后一个块是控制块，即pos为3的块
     */
    fun writeMifare(mfc: MifareClassic) {
        var isAuth = false
        try {
            mfc.connect()
            //共16个扇区， 0-15， 假如向第2个扇区写入数据
            if (!mfc.authenticateSectorWithKeyA(1, MifareClassic.KEY_DEFAULT)) {
                Log.d(TAG, "authenticateSectorWithKeyA failed")
                if (mfc.authenticateSectorWithKeyB(1, MifareClassic.KEY_DEFAULT)) {
                    Log.d(TAG, "authenticateSectorWithKeyB success")
                    //假设向第2个块写入数据,写入的数据长度必须是16个字节，即16个byte
/*                    mfc.writeBlock(1, byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                        0x07, 0x08, 0x09, 0x10, 0x11, 0x12,
                        0x13, 0x14, 0x15, 0x16))*/
                    // 或
                    mfc.writeBlock(1, "1234567890123456".toByteArray(Charset.forName("UTF-8")))
                } else {
                    Log.d(TAG, "authenticateSectorWithKeyB failed")
                }
            } else {
                isAuth = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun enableNfcForegroundDispatch() {
        /*        val intent = Intent(this, javaClass).apply {
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    //action = NfcAdapter.ACTION_TECH_DISCOVERED or NfcAdapter.ACTION_TAG_DISCOVERED
                    //putExtra(NfcAdapter.EXTRA_TAG, NfcAdapter.EXTRA_TAG)
                }
                val flag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                val pendingIntent = NfcUtil.mPendingIntent//PendingIntent.getActivity(this, 0, intent, flag)
                val techList = arrayOf(arrayOf(IsoDep::class.java.name))
                nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)*/

    }

    fun getNdefMsg_RTD_URI(data: String, identifierCode: Byte, needAAR: Boolean): NdefMessage {

        var uriField = data.toByteArray(Charset.forName("US-ASCII"))
        var payload = ByteArray(uriField.size + 1)
        payload[0] = identifierCode
        System.arraycopy(uriField, 0, payload, 1, uriField.size)
        val record = NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_URI,
            ByteArray(0),
            payload
        )
        if (needAAR) {
            return NdefMessage(arrayOf(record, NdefRecord.createApplicationRecord("com.example.main")))
        }

        //val uri = "http://www.baidu.com"
        //val uriField = uri.toByteArray(Charset.forName("UTF-8"))
        return NdefMessage(arrayOf(record))
    }

    fun getNdefMsg_Absolute_URL(data: String, needAAR: Boolean): NdefMessage {
        var payload = data.toByteArray(Charset.forName("US-ASCII"))
        val record = NdefRecord(
            NdefRecord.TNF_ABSOLUTE_URI,
            ByteArray(0),
            ByteArray(0),
            payload
        )

        if (needAAR) {
            return NdefMessage(arrayOf(record, NdefRecord.createApplicationRecord("com.example.main")))
        }

        return NdefMessage(arrayOf(record))
    }

    fun getNdefMsg_RTD_TEXT(data: String, encodeInUTF8: Boolean, needAAR: Boolean): NdefMessage {

        val local = Locale("en", "US")
        var langBytes = local.language.toByteArray(Charset.forName("US-ASCII"))
        var utfEncoding = if (encodeInUTF8) {
            Charset.forName("UTF-8")
        } else {
            Charset.forName("UTF-16")
        }
        var utfBit = if (encodeInUTF8) {
            0
        } else {
            1 shl 7 // 1000 0000   0x80
        }
        var status = (utfBit + langBytes.size).toByte()
        val textBytes = data.toByteArray(utfEncoding)
        val payload = ByteArray(1 + langBytes.size + textBytes.size)
        payload[0] = status
        System.arraycopy(langBytes, 0, payload, 1, langBytes.size)
        System.arraycopy(textBytes, 0, payload, 1 + langBytes.size, textBytes.size)
        val record = NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_TEXT,
            ByteArray(0),
            payload
        )

        if (needAAR) {
            return NdefMessage(arrayOf(record, NdefRecord.createApplicationRecord("com.example.main")))
        }

        return NdefMessage(arrayOf(record))
    }
}