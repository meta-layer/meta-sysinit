inherit supervised-base supervised-by-${SUPERVISION_TYPE}

SERVICE_NAME ?= "${PN}"
SERVICE_DIR ?= "${SERVICE_ROOT}/${SERVICE_NAME}"

DEPENDS += " virtual/supervision "
RDEPENDS_${PN} = " supervision-runtime "

do_compile_append() {
	if test "${SERVICE_LOG_SCRIPT_NAME}" != "" -a ! -f ${WORKDIR}/${SERVICE_LOG_SCRIPT_NAME}
	then
	cat <<EOF >${WORKDIR}/${SERVICE_LOG_SCRIPT_NAME}
#!/bin/sh

test -d ${localstatedir}/log/${SUPERVISION_TYPE}/${SERVICE_NAME} || mkdir -p ${localstatedir}/log/${SUPERVISION_TYPE}/${SERVICE_NAME}
exec ${MULTILOG_TOOL} t ${localstatedir}/log/${SUPERVISION_TYPE}/${SERVICE_NAME}
EOF
	fi
}

do_install_append() {
	install -d -m 0755 ${D}/${SERVICE_DIR}
	install -m 0755 ${WORKDIR}/${SERVICE_RUN_SCRIPT_NAME} ${D}${SERVICE_DIR}/${SERVICE_RUN_SCRIPT_TARGET}

	if test "${SERVICE_LOG_SCRIPT_NAME}" != "" -a $(dirname "${SERVICE_DIR}/${SERVICE_LOG_SCRIPT_TARGET}") != "${SERVICE_DIR}"
	then
		install -d -m 0755 ${D}/$(dirname "${SERVICE_DIR}/${SERVICE_LOG_SCRIPT_TARGET}")
		install -m 0755 ${WORKDIR}/${SERVICE_LOG_SCRIPT_NAME} ${D}${SERVICE_DIR}/${SERVICE_LOG_SCRIPT_TARGET}
	fi
}

FILES_${PN} += "${SERVICE_DIR}"
