package nio.notebook.app.domain.model

import io.github.vinceglb.filekit.PlatformFile

actual fun PlatformFile.child(name: String): PlatformFile =
    PlatformFile(this, name)