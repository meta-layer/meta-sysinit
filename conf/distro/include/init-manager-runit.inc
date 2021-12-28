# Use runit for system initialization
DISTRO_FEATURES_BACKFILL_CONSIDERED:append = " sysvinit systemd"
VIRTUAL-RUNTIME_init_manager ??= "runit"
VIRTUAL-RUNTIME_initscripts ??= "runit-service-scripts"
VIRTUAL-RUNTIME_login_manager ??= "shadow-base"
