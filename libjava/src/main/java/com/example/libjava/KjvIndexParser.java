package com.example.libjava;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class KjvIndexParser {
    private static final String INDEX_FILE = "D:\\me\\app\\KJV Bible Now_ Verse+Audio_1.2.0.1020_Apkpure - 副本\\assets\\dl\\local\\en\\index.z";
    private static final String TEXT_FILE = "D:\\me\\app\\KJV Bible Now_ Verse+Audio_1.2.0.1020_Apkpure - 副本\\assets\\dl\\local\\en\\kjv_k01.txt";
    private static final int CHAPTER_SIZE = 8;

    public static void main2(String[] args) {
        try {
            // 读取 index.z 文件到内存中
            byte[] compressedData = readBytesFromFile(INDEX_FILE);
            byte[] decompressedData = inflate(compressedData);

            // 解析每个章节的起始和结束位置
            List<Integer[]> chapterOffsets = new ArrayList<>();
            for (int i = 0; i < decompressedData.length; i += CHAPTER_SIZE) {
                ByteBuffer buf = ByteBuffer.wrap(decompressedData, i, CHAPTER_SIZE);
                buf.order(ByteOrder.LITTLE_ENDIAN);
                int startOffset = buf.getInt();
                int endOffset = buf.getInt();
                chapterOffsets.add(new Integer[]{startOffset, endOffset});
            }

            // 显示第1-5章的内容
            String text = new String(readBytesFromFile(TEXT_FILE), "UTF-8");
            for (int i = 0; i < 5; i++) {
                Integer[] offsets = chapterOffsets.get(i);
                System.out.println(text.substring(offsets[0], offsets[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readBytesFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        byte[] buffer = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(buffer);
        }
        return buffer;
    }

    private static byte[] inflate(byte[] input) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        byte[] output = new byte[1024];
        int len = inflater.inflate(output);
        inflater.end();
        return output;
    }


    //======================================================================================
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            // 读取 index.z 文件到内存中
            InputStream inputStream = KjvIndexParser.class.getResourceAsStream(INDEX_FILE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] compressedData = outputStream.toByteArray();

            // 解压缩数据
            Inflater inflater = new Inflater();
            inflater.setInput(compressedData);
            ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream();
            byte[] decompressedBuffer = new byte[BUFFER_SIZE];
            while (!inflater.finished()) {
                int count = inflater.inflate(decompressedBuffer);
                decompressedStream.write(decompressedBuffer, 0, count);
            }
            inflater.end();
            byte[] decompressedData = decompressedStream.toByteArray();

            // 解析每个章节的起始和结束位置
            ByteBuffer buf = ByteBuffer.wrap(decompressedData);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            int chapterCount = buf.getInt();
            for (int i = 0; i < chapterCount; i++) {
                int startOffset = buf.getInt();
                int endOffset = buf.getInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
