#!/bin/sh
### BEGIN INIT INFO
# Provides:             daemontools
# Required-Start:       
# Required-Stop:      
# Default-Start:
# Default-Stop:
# Short-Description:  runs svscanboot &
### END INIT INFO

PATH=/sbin:/bin:/usr/sbin:/usr/bin

case "$1" in
	start)
		@INIT_D_DIR@/s6-svscanboot &
		;;
	stop)
		logger -s "Stopping services"
		@bindir@/s6-svc -d @SERVICE_ROOT@/*
		sleep 2
		@bindir@/s6-svstat @SERVICE_ROOT@/* | logger -s
		@bindir@/s6-svc -dk @SERVICE_ROOT@/*
		sleep 2
		@bindir@/s6-svstat @SERVICE_ROOT@/* | logger -s

		killall s6-supervise
		killall s6-readproctitle
		killall s6-svscan

		sleep 2

		killall -9 s6-supervise
		killall -9 s6-readproctitle
		killall -9 s6-svscan

		;;
	*)
		echo "Usage $0 (start|stop)" >&2
		exit 1
		;;
esac

: exit 0
