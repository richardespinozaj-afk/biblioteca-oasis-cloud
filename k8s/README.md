# Despliegue en Google Kubernetes Engine (GKE)

## Requisitos previos

- Tener gcloud CLI instalado y autenticado
- Haber creado un proyecto en GCP
- Habilitar APIs: Kubernetes Engine, Container Registry, Cloud SQL

## 1. Configurar variables

```bash
export PROJECT_ID=tu-proyecto-gcp
export REGION=us-central1
export CLUSTER_NAME=biblioteca-oasis-cluster
```

## 2. Crear el cluster GKE

```bash
gcloud container clusters create $CLUSTER_NAME \
  --project=$PROJECT_ID \
  --region=$REGION \
  --num-nodes=2 \
  --machine-type=e2-medium \
  --enable-autoscaling \
  --min-nodes=2 \
  --max-nodes=5
```

## 3. Conectar kubectl al cluster

```bash
gcloud container clusters get-credentials $CLUSTER_NAME --region=$REGION --project=$PROJECT_ID
```

## 4. Construir imágenes Docker y subir a Artifact Registry

```bash
# Backend
cd backend
docker build -t gcr.io/$PROJECT_ID/biblioteca-backend:latest .
docker push gcr.io/$PROJECT_ID/biblioteca-backend:latest

# Frontend
cd ../frontend
docker build -t gcr.io/$PROJECT_ID/biblioteca-frontend:latest .
docker push gcr.io/$PROJECT_ID/biblioteca-frontend:latest
```

## 5. Actualizar manifiestos con tu PROJECT_ID

Edita los archivos `04-backend-deployment.yaml` y `06-frontend-deployment.yaml` y reemplaza `PROJECT_ID` con tu ID real de proyecto GCP.

## 6. Aplicar manifiestos

```bash
cd k8s
kubectl apply -f 01-namespace.yaml
kubectl apply -f 02-secret.yaml
kubectl apply -f 03-configmap.yaml
kubectl apply -f 04-backend-deployment.yaml
kubectl apply -f 05-backend-service.yaml
kubectl apply -f 06-frontend-deployment.yaml
kubectl apply -f 07-frontend-service.yaml
kubectl apply -f 08-ingress.yaml
kubectl apply -f 09-hpa.yaml
```

## 7. Verificar despliegue

```bash
kubectl get all -n biblioteca-oasis
kubectl get ingress -n biblioteca-oasis
```

## 8. Conectar con Cloud SQL (producción)

En producción, usar Cloud SQL en lugar de MySQL local:

1. Crear instancia Cloud SQL (MySQL 8.0)
2. Habilitar Cloud SQL Admin API
3. Crear cuenta de servicio con permiso `Cloud SQL Client`
4. Instalar Cloud SQL Auth Proxy como sidecar o usar IP privada

## Notas importantes

- `PROJECT_ID` debe ser reemplazado en los manifiestos antes de aplicar
- El Ingress usa un certificado SSL gestionado (`oasis-ssl-cert`) que debes crear en GCP
- La IP estática `oasis-static-ip` debe reservarse en GCP Console antes de aplicar el Ingress
