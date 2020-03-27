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
LIC_FILES_CHKSUM = "file://COPYING;md5=2875ff3bd035b06bc171bb0e55a5f228"

SRCREV = "e574acf58f57d30f02389fc4ddd36072a68ae4a2"
SRC_URI = "git://git.skarnet.org/execline"

EXTRA_OECONF += "\
        --with-sysdeps=${STAGING_DIR_TARGET}${libdir}/skalibs/sysdeps \
	--enable-shared \
	--disable-static \
	--disable-allstatic \
"

S = "${WORKDIR}/git"
