package bernard.tatin.ProcFS


object ProcessID {
    private var pid: Long = -1

    fun getPID(): Long {
        if (pid == -1) {
            val processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName()
            pid = Long.parseLong(processName.split("@")[0])
        }
        return pid
    }
}
