#!/bin/sh
### BEGIN INIT INFO
# Provides:             daemontools
# Required-Start:       
# Required-Stop:      
# Default-Start:
# Default-Stop:
# Short-Description:  runs svscanboot &
### END INIT INFO

case "$1" in
	start)
		@INIT_D_DIR@/svscanboot &
		;;
	stop)
		logger -s "Stopping services"
		svc -d @SERVICE_ROOT@/*
		sleep 2
		svstat @SERVICE_ROOT@/* | logger -s
		svc -dk @SERVICE_ROOT@/*
		sleep 2
		svstat @SERVICE_ROOT@/* | logger -s

		killall supervise
		killall readproctitle
		killall svscan

		;;
	*)
		echo "Usage $0 (start|stop)" >&2
		exit 1
		;;
esac

: exit 0
