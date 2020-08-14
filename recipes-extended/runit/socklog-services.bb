# Copyright (C) 2017 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Additional socklog configurations from Void Distro"
HOMEPAGE = "https://github.com/void-linux/void-socklog"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://nanoklogd.c;beginline=3;endline=6;md5=8c10698c7abd64f01ac4245e2b11ab64"
SECTION = "base"

PV = "20150726+git${SRCPV}"

SRCREV = "e6b8b91dcd38ebcfbb0d41753d814edf78b06989"
SRC_URI = "git://github.com/void-linux/socklog-void \
           file://0001-Use-options-to-cp-to-ensure-proper-mods.patch \
"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "PREFIX=${exec_prefix}"

do_install() {
	oe_runmake DESTDIR=${D} install
}
