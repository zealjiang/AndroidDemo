package com.example.myapplication.nfc

import android.net.Uri
import android.nfc.NdefRecord
import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableBiMap
import com.google.common.primitives.Bytes
import java.nio.charset.Charset
import java.util.Arrays
import kotlin.experimental.and

object NFCRecordParse {

    fun parseWellKnownUriRecord(record: NdefRecord): String?{
        Preconditions.checkArgument(Arrays.equals(record.type, NdefRecord.RTD_URI))
        val payload = record.payload
        val prefix: String? = URI_PREFIX_MAP[payload[0]] ?: ""
        var fullUriArray = Bytes.concat(prefix!!.toByteArray(Charset.forName("UTF-8")),
            Arrays.copyOfRange(payload, 1, payload.size))
        val uri: Uri = Uri.parse(String(fullUriArray, Charset.forName("UTF-8")))
        val id = record.id
        val type = record.type
        val tnf = record.tnf
        return uri.toString()
    }

    fun parseAbsoluteUriRecord(record: NdefRecord) {
        val payload = record.payload
        val uri: Uri = Uri.parse(String(payload, Charset.forName("UTF-8")))
        val id = record.id
        val type = record.type
        val tnf = record.tnf
    }

    fun parseWellKnownTextRecord(record: NdefRecord) {
        Preconditions.checkArgument(Arrays.equals(record.type, NdefRecord.RTD_TEXT))
        val payload = record.payload
        val textEncoding = if (payload[0] and 0x80.toByte() == 0.toByte()) {
            "UTF-8"
        } else {
            "UTF-16"
        }
        val languageCodeLength = payload[0] and 0x3F.toByte() //这里为什么是0x3f
        val text = String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1,
            Charset.forName(textEncoding))
        val id = record.id
        val type = record.type
        val tnf = record.tnf

        /**
         * 在给定的代码中，0x3F 是一个十六进制数，用于进行位运算。具体来说，这里使用了按位与操作符 and 来提取字节中的特定位。
            在 NFC 技术中，NDEF（NFC Data Exchange Format）记录的有效负载（payload）的第一个字节包含了一些标志位和元数据。其中，低6位表示语言代码长度，而第7位表示文本编码类型。
            以下是对代码的解释：
            val languageCodeLength = payload[0] and 0x3F.toByte()
            在此行代码中，payload[0] 表示有效负载的第一个字节。通过将其与 0x3F 进行按位与操作，可以提取出低6位的值，即语言代码长度。0x3F 的二进制表示为 00111111，它的作用是将高两位清零，只保留低6位。
            这样做是因为语言代码长度的范围是 0-63，而 0x3F 的二进制形式正好满足这个范围。通过提取出的语言代码长度，可以进一步处理有效负载中的其他数据。
            需要注意的是，0x3F 是一个常量，表示为十六进制。在 Kotlin 中，0x 前缀表示十六进制数。
         */
    }

    fun parseMimeRecord(record: NdefRecord) {

    }

    fun parseExternalRecord(record: NdefRecord) {

    }

    val URI_PREFIX_MAP = ImmutableBiMap.builder<Byte, String>()
        .put(0x00, "")
        .put(0x01, "http://www.")
        .put(0x02, "https://www.")
        .put(0x03, "http://")
        .put(0x04, "https://")
        .put(0x05, "tel:")
        .put(0x06, "mailto:")
        .put(0x07, "ftp://anonymous:anonymous@")
        .put(0x08, "ftp://ftp.")
        .put(0x09, "ftps://")
        .put(0x0A, "sftp://")
        .put(0x0B, "smb://")
        .put(0x0C, "nfs://")
        .put(0x0D, "ftp://")
        .put(0x0E, "dav://")
        .put(0x0F, "news:")
        .put(0x10, "telnet://")
        .put(0x11, "imap:")
        .put(0x12, "rtsp://")
        .put(0x13, "urn:")
        .put(0x14, "pop:")
        .put(0x15, "sip:")
        .put(0x16, "sips:")
        .put(0x17, "tftp:")
        .put(0x18, "btspp://")
        .put(0x19, "btl2cap://")
        .put(0x1A, "btgoep://")
        .put(0x1B, "tcpobex://")
        .put(0x1C, "irdaobex://")
        .put(0x1D, "file://")
        .put(0x1E, "urn:epc:id:")
        .put(0x1F, "urn:epc:tag:")
        .put(0x20, "urn:epc:pat:")
        .put(0x21, "urn:epc:raw:")
        .put(0x22, "urn:epc:")
        .put(0x23, "urn:nfc:")
        .build()
}