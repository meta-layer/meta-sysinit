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

# inherit useradd
inherit skaware

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=a215fc090f57e8f048095cc6a7856cac"

SRCREV = "2be881c031225e846814de91b20e5af7dfff71f5"
SRC_URI = "git://git.skarnet.org/s6"

EXTRA_OECONF += "\
        --with-sysdeps=${STAGING_DIR_TARGET}${libdir}/skalibs/sysdeps \
	--enable-shared \
	--disable-static \
	--disable-allstatic \
"

S = "${WORKDIR}/git"
