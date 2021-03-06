# Copyright (C) 2020 Jens Rehsack <sno@netbsd.org>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "s6-dns is a suite of DNS client programs and libraries for Unix \
systems, as an alternative to the BIND, djbdns or other DNS clients."
HOMEPAGE = "https://skarnet.org/software/s6-dns/"
SECTION = "base"
DEPENDS = "skalibs"

inherit skaware

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=2875ff3bd035b06bc171bb0e55a5f228"

SRCREV = "7ed3fb46886941a50e85e093699e084b9c9fa875"
SRC_URI = "git://git.skarnet.org/s6-dns"

EXTRA_OECONF += "\
        --with-sysdeps=${STAGING_DIR_TARGET}${libdir}/skalibs/sysdeps \
	--enable-shared \
	--disable-static \
	--disable-allstatic \
"

S = "${WORKDIR}/git"
