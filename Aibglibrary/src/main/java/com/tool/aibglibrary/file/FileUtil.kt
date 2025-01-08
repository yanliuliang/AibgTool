//package com.nongchanpin.aibglibrary.file
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.media.MediaScannerConnection
//import android.net.Uri
//import android.util.Log
//import java.io.File
//import java.io.FileOutputStream
//import java.io.InputStream
//import java.nio.ByteBuffer
//
//
///**
// * @Description:文件处理
// * @Author: dick
// * @CreateDate: 2023/10/18
// * @Version:
// */
//object FileUtil {
//
//    /**
//     * 手机沙盒路径
//     */
//    val absolutePath: String
//        get() = App.instance.filesDir.absolutePath + "/"
//
//    /**
//     * 手机字体写入沙盒
//     * @param context
//     * @param id
//     * @param fileName
//     */
//    private fun copyFilesFromRaw(context: Context, id: Int, fileName: String) {
//        val inputStream: InputStream = context.resources.openRawResource(id)
//        val storagePath = absolutePath + File.separator + fileName
//        val file = File(storagePath)
//        try {
//            if (!file.exists()) {
//                // 1.建立通道对象
//                val fos = FileOutputStream(file)
//                // 2.定义存储空间
//                val buffer = ByteArray(inputStream.available())
//                // 3.开始读文件
//                var lenght = 0
//                while (inputStream.read(buffer).also { lenght = it } != -1) { // 循环从输入流读取buffer字节
//                    // 将Buffer中的数据写到outputStream对象中
//                    fos.write(buffer, 0, lenght)
//                }
//                fos.flush() // 刷新缓冲区
//                // 4.关闭流
//                fos.close()
//                inputStream.close()
//                Log.d("打印yll", "copyFilesFromRaw() +$fileName 字体下载成功 ")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    /**
//     * 手动刷新媒体库
//     */
//    fun refreshMediaLibrary(context: Context, path: String, block: (String, Uri) -> Unit) {
//        MediaScannerConnection.scanFile(
//            context, arrayOf(path), null
//        ) { path, uri ->
//            Log.i("TAG", "Scanned $path:")
//            Log.i("TAG", "-> uri=$uri")
//            block.invoke(path, uri)
//        }
//    }
//
//    /**
//     * 资源写入手机
//     */
//    fun fontWriteToSd(context: Context, resId: Int, resName: String) {
//        val filesDir: String = absolutePath
//        val fontPath = File(filesDir, "arial.ttf")
//        //判断该文件存不存在
//        if (!fontPath.exists()) {
//            //如果不存在，开始写入文件
//            copyFilesFromRaw(context, resId, resName)
//        }
//    }
//
//    /**
//     * bitmap转bytes
//     */
//    private fun bitmap2Bytes(image: Bitmap?): ByteArray? {
//        val bytes = image?.byteCount
//        val buffer = bytes?.let { ByteBuffer.allocate(it) }
//        image?.copyPixelsToBuffer(buffer)
//        return buffer?.array()
//    }
//
//}