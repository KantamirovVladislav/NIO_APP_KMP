package nio.notebook.app.domain.model

import android.webkit.MimeTypeMap
import androidx.documentfile.provider.DocumentFile
import io.github.vinceglb.filekit.AndroidFile
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.context

actual fun PlatformFile.child(name: String): PlatformFile {
    return when (val af = this.androidFile) {
        is AndroidFile.FileWrapper -> PlatformFile(this, name) // обычный путь (не SAF)
        is AndroidFile.UriWrapper -> {
            val ext = name.substringAfterLast('.', "")
            val mime = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(ext.lowercase())
                ?: "application/octet-stream"


            val dirDoc = DocumentFile.fromTreeUri(FileKit.context, af.uri)
                ?: error("Invalid tree uri: ${af.uri}")

            val doc = dirDoc.findFile(name) ?: dirDoc.createFile(mime, name)
            ?: error("Can't create '$name' in $dirDoc")

            PlatformFile(doc.uri)
        }
    }
}