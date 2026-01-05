package nio.notebook.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform