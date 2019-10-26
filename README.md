OpenEmbedded/Yocto Project layer for sysinit recipes
====================================================

This layer provides support for several init systems and service management
suites for Open Embedded and Yocto Project build systems.

Contributing:
-------------

You can submit Pull Requests via GitHub.

Please refer to:
https://wiki.yoctoproject.org/wiki/Contribution_Guidelines#General_Information

for some useful guidelines to be followed when submitting patches.

Usage instructions
------------------

### bblayers.conf

At the very moment, the external tooling via Packager::Utils, rely on following:

    BBPATH = "${TOPDIR}"
    BSPDIR := "${@os.path.abspath(os.path.dirname(d.getVar('FILE', True)) + '/../..')}"
    
    BBLAYERS = "...\
    ${BSPDIR}/sources/meta-sysinit \
    "
When any Perl package is put here in a recipe, be aware. In any other case, just add `meta-sysinit` as you like to your `BBLAYERS`.

### local.conf

By default, meta-sysinit configures for daemontools-encore supervision. It is battle-tested on embedded platforms and in several workshops.

The only supported init-system for now in sysvinit. It's insane to combine either of provided supervision suites with systemd.

When you want to change it, e.g. to s6, maybe add some lines to your local.conf like:

    SUPERVISION_TYPE = "s6"
    PREFERRED_PROVIDER_virtual/supervision = "s6"

For `SUPERVISION_TYPE` following values are possible:
* `daemontools`
* `s6`

For `s6` as `SUPERVISION_TYPE` is only one provider available - `s6` itself. For `daemontools` as `SUPERVISION_TYPE` it's possible to choose between `daemontools` and `daemontools-encore` as `PREFERRED_PROVIDER`.
