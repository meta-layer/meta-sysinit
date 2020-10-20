# Copyright (C) 2020 Jens Rehsack <sno@netbsd.org>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "s6-networking is a suite of small networking utilities for \
Unix systems. It includes command-line client and server management, TCP \
access control, privilege escalation across UNIX domain sockets, IDENT \
protocol management and clock synchronization. Optionally, it also includes \
command-line TLS/SSL tools for secure communications."
HOMEPAGE = "https://skarnet.org/software/s6-networking/"
SECTION = "base"

DEPENDS = "skalibs execline s6 s6-dns"

PACKAGECONFIG ?= ""

#PACKAGECONFIG[libressl] = "--enable-ssl=libressl,,libressl"
PACKAGECONFIG[bearssl] = "--enable-ssl=bearssl,,bearssl"

inherit skaware

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=2875ff3bd035b06bc171bb0e55a5f228"

SRCREV = "1fea1f6ed53cae7f752c9a78271c7c8367b0ad03"
SRC_URI = "git://git.skarnet.org/s6-networking"

EXTRA_OECONF += "\
        --with-sysdeps=${STAGING_DIR_TARGET}${libdir}/skalibs/sysdeps \
	--enable-shared \
	--disable-static \
	--disable-allstatic \
"

S = "${WORKDIR}/git"
