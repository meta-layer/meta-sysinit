SERVICE_ROOT     ?= "${sysconfdir}/${SUPERVISION_TYPE}/service"
SERVICE_CTRL_GRP ?= "svcctrl"

SUPERVISION_TYPE ??= "daemontools"
PREFERRED_PROVIDER_virtual/supervision ??= "daemontools-encore"
