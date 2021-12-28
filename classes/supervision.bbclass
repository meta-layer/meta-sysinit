inherit supervised-base useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "-r ${SERVICE_CTRL_GRP}"

PROVIDES += "virtual/supervision"
RPROVIDES:${PN} = "supervision-runtime"
