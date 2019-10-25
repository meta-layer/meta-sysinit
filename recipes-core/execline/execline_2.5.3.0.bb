# Copyright (C) 2019 Jens Rehsack <sno@netbsd.org>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "execline is a (non-interactive) scripting language, like \
sh ; but its syntax is quite different from a traditional shell syntax. \
The execlineb program is meant to be used as an interpreter for a text \
file; the other commands are essentially useful inside an execlineb script."
HOMEPAGE = "https://skarnet.org/software/execline/"
SECTION = "base"
DEPENDS = "skalibs"

inherit skaware

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=a215fc090f57e8f048095cc6a7856cac"

SRCREV = "3856ce50bfc3fc23d8b819f2a3970cf2af66882b"
SRC_URI = "git://git.skarnet.org/execline"

EXTRA_OECONF += "\
        --with-sysdeps=${STAGING_DIR_TARGET}${libdir}/skalibs/sysdeps \
	--enable-shared \
	--disable-static \
	--disable-allstatic \
"

S = "${WORKDIR}/git"
