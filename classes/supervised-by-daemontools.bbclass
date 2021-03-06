SERVICE_RUN_SCRIPT_NAME_${PN}   ?= "run"
SERVICE_RUN_SCRIPT_TARGET       ?= "run"
SERVICE_LOG_SCRIPT_NAME_${PN}   ?= "log"
SERVICE_LOG_SCRIPT_TARGET       ?= "log/run"

MULTILOG_TOOL = "${bindir}/multilog"
SVC_STATUS_CMD = "${bindir}/svok"
SVC_START_CMD = "${bindir}/svc -u"
SVC_ONCE_CMD = "${bindir}/svc -o"
SVC_STOP_CMD = "${bindir}/svc -d"
SVC_KILL_CMD = "${bindir}/svc -k"
SVC_RESTART_CMD = "${bindir}/svc -t"
SVC_RELOAD_CMD = "${bindir}/svc -h"
