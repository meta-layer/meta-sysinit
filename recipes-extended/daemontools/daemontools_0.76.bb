DESCRIPTION = "daemontools is a collection of tools for managing UNIX services"
HOMEPAGE = "http://cr.yp.to/daemontools.html"

LICENSE = "PD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/PD;md5=b3597d12946881e13cb3b548d1173851"

inherit djbware supervision

FILESEXTRAPATHS_prepend := "${THISDIR}/${PV}-${PV}:"

S = "${WORKDIR}/admin/${PN}-${PV}"

SRC_URI = "http://cr.yp.to/daemontools/daemontools-0.76.tar.gz \
           file://error.h-use-errno.h.patch \
           file://Makefile-no-run-crosscompiled.patch \
           file://svscanboot-target-fs-adoptions.patch \
	   file://init-daemontools.sh \
	   file://sv-via-ctrl-grp.sudoers \
          "

SRC_URI[md5sum] = "1871af2453d6e464034968a0fbcb2bfc"
SRC_URI[sha256sum] = "a55535012b2be7a52dcd9eccabb9a198b13be50d0384143bd3b32b8710df4c1f"

DEPENDS += "${@bb.utils.contains("VIRTUAL-RUNTIME_initscripts", "initscripts", "update-rc.d-native", "", d)}"

INIT_D_DIR = "${sysconfdir}/init.d"

do_compile_append () {
	sed -i  -e "s,@bindir[@],${bindir},g" -e "s,@SERVICE_ROOT[@],${SERVICE_ROOT},g" \
		-e "s,@SERVICE_CTRL_GRP[@],${SERVICE_CTRL_GRP},g" -e "s,@INIT_D_DIR[@],${INIT_D_DIR},g" \
		${S}/command/svscanboot ${WORKDIR}/sv-via-ctrl-grp.sudoers ${WORKDIR}/init-daemontools.sh
}

do_install_append () {
	if test "${VIRTUAL-RUNTIME_initscripts}" = "initscripts"
	then
		install -d ${D}${INIT_D_DIR}

		# already installed by makefile - move it to right(tm) place
		mv ${D}${bindir}/svscanboot ${D}${INIT_D_DIR}

		install -m 0755 ${WORKDIR}/init-daemontools.sh ${D}${INIT_D_DIR}/init-daemontools
		update-rc.d -r ${D} init-daemontools start 30 3 5 . stop 20 0 1 6 .
	fi

	# prepare for installing base-dir for services
	install -d 0755 ${D}${SERVICE_ROOT}

	# allow %svcctrl to call svc
	install -d ${D}${sysconfdir}/sudoers.d
	install -m 600 ${WORKDIR}/sv-via-ctrl-grp.sudoers ${D}${sysconfdir}/sudoers.d/sv-via-ctrl-grp
}
