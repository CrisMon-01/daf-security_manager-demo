#!/usr/bin/***REMOVED*** bash
kubectl delete configmap security-manager-conf
kubectl create configmap security-manager-conf --from-file=../conf/test/prodBase.conf