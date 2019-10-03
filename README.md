# TeamCity Agents in K3S
PoC of TC agents running as Pods in K3S cluster with DooD support

## TeamCity Agent Docker Image
### Build Image
```
docker build -t frundh/tc-agent --build-arg TC_AGENT_ZIP=https://teamcity.frundh.local/update/buildAgent.zip .
```
Based on https://github.com/JetBrains/teamcity-docker-minimal-agent/blob/master/ubuntu/Dockerfile

## K3S Cluster
### Create
```
curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC="--docker"  sh -
```
### Deploy Workload
Update `env:SERVER_URL` and apply
```
kubectl apply -f ./k8s/deployment.yaml
```
Deploys agent pods and two cron-jobs for clean up tasks.

## Drone CI Build
`./drone/` dir contains a simple TeamCity build project using Drone CI