#!/usr/bin/***REMOVED*** bash

kinit -V -l 20 -r 30 -c /tmp/$1 $1@DAF.GOV.IT
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

export KRB5CCNAME="/tmp/"$1
curl -i --insecure --negotiate -u : $2"/webhdfs/v1/***REMOVED***?op=GETFILESTATUS" | grep Set-Cookie | grep kerberos