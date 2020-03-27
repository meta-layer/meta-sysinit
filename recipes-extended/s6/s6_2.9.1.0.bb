# Copyright (C) 2019 Jens Rehsack <sno@netbsd.org>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "s6 is a small suite of programs for UNIX, designed to allow \
process supervision (a.k.a service supervision), in the line of daemontools \
and runit, as well as various operations on processes and daemons. It \
is meant to be a toolbox for low-level process and service administration, \
providing different sets of independent tools that can be used within or \
without the framework, and that can be assembled together to achieve powerful \
functionality with a very small amount of code."
HOMEPAGE = "https://www.skarnet.org/software/s6/"
SECTION = "base"
DEPENDS = "skalibs execline"

inherit skaware supervision

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=2875ff3bd035b06bc171bb0e55a5f228"

SRCREV = "bfdb80fe56bb4e6ffd14d4754b4693863e1cddfa"
SRC_URI = "\
	git://git.skarnet.org/s6 \
	file://0001-Treat-execline-as-an-extra_lib-instead-of-an-in-pack.patch \
	file://sysv-init-s6.sh \
	file://sv-enc-via-ctrl-grp.sudoers \
	"

EXTRA_OECONF += "\
        --with-sysdeps=${STAGING_DIR_TARGET}${libdir}/skalibs/sysdeps \
	--enable-shared \
	--disable-static \
	--disable-allstatic \
"

DEPENDS += "${@bb.utils.contains("DISTRO_FEATURES", "sysvinit", "update-rc.d-native", "", d)}"

do_compile_sysvinit() {
	mkdir -p ${B}/${sysconfdir}/init.d/
	sed -i	-e "s,/command,${bindir},g" -e "s,/\<service\>,${SERVICE_ROOT},g" \
		-e "s,@bindir[@],${bindir},g" -e "s,@SERVICE_ROOT[@],${SERVICE_ROOT},g" \
		${S}/examples/s6-svscanboot ${WORKDIR}/sysv-init-s6.sh ${WORKDIR}/sv-enc-via-ctrl-grp.sudoers
}

do_compile_append() {
	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}
	then
		do_compile_sysvinit
	fi
}

do_install_append() {
	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}
	then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${S}/examples/s6-svscanboot ${D}${sysconfdir}/init.d/
		install -m 0755 ${WORKDIR}/sysv-init-s6.sh ${D}${sysconfdir}/init.d/init-s6
		update-rc.d -r ${D} init-s6 start 30 3 5 . stop 20 0 1 6 .

		install -d ${D}${SERVICE_ROOT}/s6-svscan-log
	fi

	# allow %svcctrl to call svc
	install -d ${D}${sysconfdir}/sudoers.d
	install -m 600 ${WORKDIR}/sv-enc-via-ctrl-grp.sudoers ${D}${sysconfdir}/sudoers.d/sv-enc-via-ctrl-grp
}

S = "${WORKDIR}/git"
