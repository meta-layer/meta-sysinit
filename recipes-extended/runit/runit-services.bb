# Copyright (C) 2017 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Additional runit scripts for OE based systems"
HOMEPAGE = "https://github.com/YoeDistro/oe-runit"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README.md;beginline=41;endline=48;md5=f2f8535b84b11359cc7757b009cfd646"
SECTION = "base"

PV = "20180623+git${SRCPV}"

SRCREV = "8d5db5c26670e49524f33800aaf0337466495c84"
SRC_URI = "git://github.com/YoeDistro/oe-runit;branch=oe/master \
"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "PREFIX=${exec_prefix}"

do_install() {
	oe_runmake DESTDIR=${D} install
	install -d ${D}${base_bindir} ${D}${sysconfdir}/runit/runsvdir
	for f in shutdown halt reboot poweroff
	do
		ln -sf ${bindir}/$f ${D}${base_bindir}/$f
	done
}

pkg_postinst_ontarget_${PN} () {
        # Enable default services:
        #       - agetty-tty[1-4] (default)
        #       - udevd (default)
        #       - sulogin (single)
        mkdir -p $D/etc/runit/runsvdir/single
        ln -sf /etc/sv/sulogin $D/etc/runit/runsvdir/single

        mkdir -p $D/etc/runit/runsvdir/default
        if [ ! -e $D/etc/runit/runsvdir/current ]; then
		ln -sf default $D/etc/runit/runsvdir/current
	fi
        if [ -e $D/etc/sv/udevd/run ]; then
        	ln -sf /etc/sv/udevd $D/etc/runit/runsvdir/default
	fi
}

RDEPENDS_${PN} = "runit findutils util-linux-fsck coreutils"

PACKAGES =+ "${PN}-dracut"

FILES_${PN}-dracut = "${nonarch_libdir}/dracut"
