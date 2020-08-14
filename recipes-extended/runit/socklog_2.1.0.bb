# Copyright (C) 2018 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Small and secure syslogd replacement for use with runit"
HOMEPAGE = "http://smarden.org/socklog/"
LICENSE = "BSD-3-Clause"
SECTION = "base"

LIC_FILES_CHKSUM = "file://package/COPYING;md5=c7a77593c4b489904800014396f3f742"
SRC_URI = "http://smarden.org/${BPN}/${BP}.tar.gz \
           file://cross.patch \
"
SRC_URI[md5sum] = "5d0e8e28c9329ad3af982c5241df9ff1"
SRC_URI[sha256sum] = "aa869a787ee004da4e5509b5a0031bcc17a4ab4ac650c2ce8d4e488123acb455"

S = "${WORKDIR}/admin/${BPN}-${PV}"

do_compile() {
	cd ${S}/src
	echo "$CC -D_GNU_SOURCE $CFLAGS" >conf-cc
	echo "$CC $LDFLAGS -Wl,-z -Wl,noexecstack" >conf-ld
	echo "int main() { return 0; }" >${S}/src/chkshsgr.c
	oe_runmake
}

do_install() {
	cd ${S}/src
	install -d ${D}${bindir}
	for f in socklog socklog-conf socklog-check uncat tryto; do
		install -m 0755 $f ${D}${bindir}
	done
}
