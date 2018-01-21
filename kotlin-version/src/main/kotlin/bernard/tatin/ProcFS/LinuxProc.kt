package bernard.tatin.ProcFS

import java.nio.file.Path
import java.nio.file.Paths

internal class LinuxProc private constructor() {
    private val pid = ProcessID.getPID()

    private fun innerProcPathName(path: String): Path {
        return Paths.get("/proc/" + String.valueOf(pid) + "/" + path)
    }

    companion object {
        private val ourInstance = LinuxProc()

        fun procPathName(path: String): Path {
            return ourInstance.innerProcPathName(path)
        }
    }
}
