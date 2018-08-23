#!/usr/bin/***REMOVED*** bash
kubectl --kubeconfig=../../../.kube/config.***REMOVED***-***REMOVED*** delete configmap security-manager-conf
kubectl --kubeconfig=../../../.kube/config.***REMOVED***-***REMOVED*** create configmap security-manager-conf --from-file=../conf/test/prodBase.conf
