# Copyright (C) 2019 Jens Rehsack <sno@netbsd.org>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "skalibs is a package centralizing the free software / open \
source C development files used for building all software at skarnet.org: \
it contains essentially general-purpose libraries. You will need to install \
skalibs if you plan to build skarnet.org software. The point is that you \
won't have to download and compile big libraries, and care about portability \
issues, everytime you need to build a package: do it only once. \
\
skalibs can also be used as a sound basic start for C development. There are \
a lot of general-purpose libraries out there; but if your main goal is to \
produce small and secure C code with a focus on system programming, skalibs \
might be for you."
HOMEPAGE = "https://skarnet.org/software/skalibs/"
SECTION = "base"

inherit skaware

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=2875ff3bd035b06bc171bb0e55a5f228"

SRCREV = "15ec28669f6bd4cf4c958f84c0c7dbe58628b375"
SRC_URI = "\
	git://git.skarnet.org/skalibs \
"

EXTRA_OECONF += "\
	--enable-shared \
	--disable-static \
	--with-sysdep-devurandom=yes \
"

S = "${WORKDIR}/git"
