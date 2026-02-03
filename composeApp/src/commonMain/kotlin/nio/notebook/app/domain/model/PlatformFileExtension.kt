package nio.notebook.app.domain.model

import io.github.vinceglb.filekit.PlatformFile

expect fun PlatformFile.child(name: String): PlatformFile

operator fun PlatformFile.div(name: String): PlatformFile = child(name)