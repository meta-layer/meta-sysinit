inherit supervised-base useradd

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "-r ${SERVICE_CTRL_GRP}"

PROVIDES += "virtual/supervision"
RPROVIDES_${PN} = "supervision-runtime"
