package bernard.tatin.ProcFS.processid


object ProcessID {
    private var pid: Long = badPID()

    fun badPID() : Long {
        return -1
    }
    fun getPID(): Long {
        if (pid == badPID()) {
            val processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName()
            pid = processName.split("@")[0].toLong()
        }
        return pid
    }
}
