DESCRIPTION = "daemontools-encore is a collection of tools for managing UNIX services. \
It is derived from the public-domain release of daemontools by D. J. \
Bernstein.  It adds numerous enhancements above what daemontools could do \
while maintaining backwards compatibility with daemontools."

HOMEPAGE = "http://untroubled.org/daemontools-encore/"

LICENSE = "PD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/PD;md5=b3597d12946881e13cb3b548d1173851"

inherit djbware supervision

S = "${WORKDIR}/git"
DJB_CONFIG_DIR = "${S}"

SRCREV = "b40600d9ee0aa6025f33f2644207e069315ca64c"
SRC_URI = "git://github.com/bruceg/daemontools-encore.git \
	   file://init-daemontools-encore.sh \
	   file://sv-enc-via-ctrl-grp.sudoers \
	   file://do-no-run-crosscompiled.patch \
	   file://svscanboot-target-fs-adoptions.patch \
          "

DEPENDS += " ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'update-rc.d-native', '', d)}"

INIT_D_DIR = "${sysconfdir}/init.d"

do_configure:append () {
	(cd ${S} && ./makemake)
}

do_compile () {
	oe_runmake || die "make failed"
	sed -i  -e "s,@bindir[@],${bindir},g" -e "s,@SERVICE_ROOT[@],${SERVICE_ROOT},g" \
		-e "s,@SERVICE_CTRL_GRP[@],${SERVICE_CTRL_GRP},g" -e "s,@INIT_D_DIR[@],${INIT_D_DIR},g" \
		${S}/svscanboot ${WORKDIR}/sv-enc-via-ctrl-grp.sudoers ${WORKDIR}/init-daemontools-encore.sh
}

do_install () {
	install -d ${D}/${bindir}
	for i in `awk -F: '{print $6}' <BIN`
	do
		install -m 0755 ${S}/$i ${D}/${bindir}/
	done

	install -d ${D}/${mandir}/man8/
	for i in `awk -F: '{print $6}' <MAN`
	do
		install -m 0644 ${S}/$i ${D}/${mandir}/man8/
	done

	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}
	then
		install -d ${D}${INIT_D_DIR}

		# already installed by makefile - move it to right(tm) place
		mv ${D}${bindir}/svscanboot ${D}${INIT_D_DIR}

		install -m 0755 ${WORKDIR}/init-daemontools-encore.sh ${D}${INIT_D_DIR}/init-daemontools-encore
		update-rc.d -r ${D} init-daemontools-encore start 30 3 5 . stop 20 0 1 6 .
	fi

	# prepare for installing base-dir for services
	install -d 0755 ${D}${SERVICE_ROOT}

	# allow %svcctrl to call svc
	install -d ${D}${sysconfdir}/sudoers.d
	install -m 600 ${WORKDIR}/sv-enc-via-ctrl-grp.sudoers ${D}${sysconfdir}/sudoers.d/sv-enc-via-ctrl-grp
}
