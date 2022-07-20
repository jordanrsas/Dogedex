package com.hackaprende.dogedex.machinelearning

import android.annotation.SuppressLint
import android.graphics.*
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

interface ClassifierTasks {
    suspend fun recogniezeImage(imageProxy: ImageProxy): DogRecognition
}

class ClassifierRepository @Inject constructor(private val classifier: Classifier) :
    ClassifierTasks {

    override suspend fun recogniezeImage(imageProxy: ImageProxy): DogRecognition =
        withContext(Dispatchers.IO) {
            convertImageProxyToBitmap(imageProxy)?.let {
                classifier.recognizeImage(it).first()
            } ?: DogRecognition("", 0f)
        }

    @SuppressLint("UnsafeOptInUsageError")
    private fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
        val image = imageProxy.image ?: return null

        val yBuffer = image.planes[0].buffer // Y
        val uBuffer = image.planes[1].buffer // U
        val vBuffer = image.planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0, 0, yuvImage.width, yuvImage.height), 100,
            out
        )
        val imageBytes = out.toByteArray()

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}