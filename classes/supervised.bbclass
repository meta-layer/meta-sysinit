inherit supervised-base supervised-by-${SUPERVISION_TYPE}

SERVICE_NAME_${PN} ?= "${PN}"
SERVICE_DIR_${PN} ?= "${SERVICE_ROOT}/${SERVICE_NAME_${PN}}"

DEPENDS += " virtual/supervision "
SUPERVISED_PACKAGES ?= "${PN}"

def supervised_compile_append_code(d):
    code = []
    workdir = d.getVar('WORKDIR')
    localstatedir = d.getVar('localstatedir')
    pkgs = d.getVar('SUPERVISED_PACKAGES')
    for pkg in pkgs.split():
        service_log = d.getVar('SERVICE_LOG_SCRIPT_NAME_%s' % pkg)
        if service_log:
            service_name = d.getVar('SERVICE_NAME_%s' % pkg)

            service_log_work_fqdn = '/'.join([workdir, service_log])
            target_log_dir = '/'.join([localstatedir, 'log', d.getVar('SUPERVISION_TYPE'), service_name])

            code.append('	test ! -f "%s" && \\' % (service_log_work_fqdn))
            code.append('		cat <<EOF > "%s"' % (service_log_work_fqdn))
            code.append('#!/bin/sh')
            code.append('')
            code.append('test -d "%s" || mkdir -p "%s"' % (target_log_dir, target_log_dir))
            code.append('exec "%s" t "%s"' % (d.getVar('MULTILOG_TOOL'), target_log_dir))
            code.append('EOF')
            code.append('')

    return '\n'.join(code)

do_compile_append() {
	# compile / create supervised default scripts
${@supervised_compile_append_code(d)}
}

def supervised_install_append_code(d):
    code = []
    destdir = d.getVar('D')
    workdir = d.getVar('WORKDIR')
    pkgs = d.getVar('SUPERVISED_PACKAGES')
    for pkg in pkgs.split():
        service_dir = d.getVar('SERVICE_DIR_%s' % pkg)
        if not service_dir:
            service_dir = '/'.join([d.getVar('SERVICE_ROOT'), d.getVar('SERVICE_NAME_%s' % pkg)])
        service_run = d.getVar('SERVICE_RUN_SCRIPT_NAME_%s' % pkg)
        service_down = d.getVar('SERVICE_RUN_SCRIPT_DOWN_%s' % pkg)
        service_log = d.getVar('SERVICE_LOG_SCRIPT_NAME_%s' % pkg)

        service_run_target = d.getVar('SERVICE_RUN_SCRIPT_TARGET_%s' % pkg)
        if not service_run_target:
            service_run_target = d.getVar('SERVICE_RUN_SCRIPT_TARGET')

        service_log_target = d.getVar('SERVICE_LOG_SCRIPT_TARGET_%s' % pkg)
        if not service_log_target:
            service_log_target = d.getVar('SERVICE_LOG_SCRIPT_TARGET')

        code.append('	install -d -m 0755 %s%s' % (destdir, service_dir))
        code.append('	install -m 0755 %s/%s %s%s/%s' % (workdir, service_run, destdir, service_dir, service_run_target))
        code.append('	test "%s" = "down" && \\' % (service_down))
        code.append('		touch "%s%s/down"' % (destdir, service_dir))

        if service_log:
            code.append('')
            code.append('	test $(dirname "%s/%s}") != "%s" && \\' % (service_dir, service_log_target,service_dir))
            code.append('		install -d -m 0755 %s/$(dirname "%s/%s")' % (destdir, service_dir, service_log_target))
            code.append('	install -m 0755 %s/%s %s%s/%s' % (workdir, service_log, destdir, service_dir, service_log_target))

        code.append('')

    return '\n'.join(code)

do_install_append() {
	# install supervised service scripts
${@supervised_install_append_code(d)}
}

PACKAGESPLITFUNCS_prepend = "populate_packages_supervised "
PACKAGESPLITFUNCS_remove_class-nativesdk = "populate_packages_supervised "

populate_packages_supervised[vardepsexclude] += "OVERRIDES"

python populate_packages_supervised () {
    def update_supervised_auto_depend(pkg):
        d.appendVar('RDEPENDS_' + pkg, ' supervision-runtime')

    def update_supervised_package(pkg):
        service_dir = d.getVar('SERVICE_DIR_%s' % pkg)
        bb.debug(1, 'adding SERVICE_DIR_%s(%s) for %s - if any' % (pkg, service_dir, pkg))

        if service_dir:
            d.appendVar('FILES_' + pkg, service_dir)

        update_supervised_auto_depend(pkg)

    # Check that this class isn't being inhibited (generally, by
    # systemd.bbclass) before doing any work.
    pkgs = d.getVar('SUPERVISED_PACKAGES')
    for pkg in pkgs.split():
        update_supervised_package(pkg)
}
