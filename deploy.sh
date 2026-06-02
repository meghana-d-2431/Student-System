#!/bin/bash

# Kubernetes deployment script for Student System
# Usage: ./deploy.sh [build|deploy|clean|status]

set -e

NAMESPACE="student-system"
BACKEND_IMAGE="student-system-backend:latest"
FRONTEND_IMAGE="student-system-frontend:latest"

echo "Student System - Kubernetes Deployment Script"
echo "=============================================="

# Function to build Docker images
build_images() {
    echo ""
    echo "Building Docker images..."
    
    echo "Building backend image..."
    docker build -t $BACKEND_IMAGE ./studentsystem
    
    echo "Building frontend image..."
    docker build -t $FRONTEND_IMAGE ./Student-System-Frontend
    
    echo "✓ Docker images built successfully"
}

# Function to deploy to Kubernetes
deploy() {
    echo ""
    echo "Deploying to Kubernetes..."
    
    echo "Creating namespace and resources..."
    kubectl apply -f k8s/namespace.yaml
    kubectl apply -f k8s/secret.yaml
    kubectl apply -f k8s/configmap.yaml
    kubectl apply -f k8s/mysql-pvc.yaml
    kubectl apply -f k8s/mysql-deployment.yaml
    
    echo "Waiting for MySQL to be ready..."
    kubectl wait --for=condition=ready pod -l app=mysql -n $NAMESPACE --timeout=300s 2>/dev/null || true
    sleep 10
    
    echo "Deploying backend..."
    kubectl apply -f k8s/backend-deployment.yaml
    
    echo "Deploying frontend..."
    kubectl apply -f k8s/frontend-deployment.yaml
    
    echo "Deploying ingress..."
    kubectl apply -f k8s/ingress.yaml
    
    echo "✓ Deployment completed"
    echo ""
    echo "Checking deployment status..."
    kubectl get pods -n $NAMESPACE
}

# Function to check status
status() {
    echo ""
    echo "Deployment Status"
    echo "================="
    
    echo ""
    echo "Pods:"
    kubectl get pods -n $NAMESPACE -o wide
    
    echo ""
    echo "Services:"
    kubectl get svc -n $NAMESPACE
    
    echo ""
    echo "Ingress:"
    kubectl get ingress -n $NAMESPACE
}

# Function to clean up
clean() {
    echo ""
    echo "Cleaning up Kubernetes resources..."
    kubectl delete namespace $NAMESPACE --ignore-not-found
    echo "✓ Cleanup completed"
}

# Function to show port forwarding commands
port_forward() {
    echo ""
    echo "Port Forwarding Commands"
    echo "======================="
    echo ""
    echo "Frontend:"
    echo "  kubectl port-forward -n $NAMESPACE svc/frontend-service 5173:5173"
    echo ""
    echo "Backend:"
    echo "  kubectl port-forward -n $NAMESPACE svc/backend-service 9090:9090"
    echo ""
    echo "MySQL:"
    echo "  kubectl port-forward -n $NAMESPACE svc/mysql-service 3306:3306"
    echo ""
    echo "After port forwarding, access:"
    echo "  Frontend: http://localhost:5173"
    echo "  Backend: http://localhost:9090"
}

# Main script logic
case "${1:-help}" in
    build)
        build_images
        ;;
    deploy)
        build_images
        deploy
        port_forward
        ;;
    status)
        status
        ;;
    clean)
        clean
        ;;
    forward)
        port_forward
        ;;
    *)
        echo "Usage: $0 {build|deploy|clean|status|forward}"
        echo ""
        echo "Commands:"
        echo "  build   - Build Docker images"
        echo "  deploy  - Build images and deploy to Kubernetes"
        echo "  status  - Check deployment status"
        echo "  clean   - Remove all Kubernetes resources"
        echo "  forward - Show port forwarding commands"
        echo ""
        echo "Quick start:"
        echo "  1. chmod +x deploy.sh"
        echo "  2. ./deploy.sh deploy"
        echo "  3. Run the port forwarding commands in another terminal"
        echo "  4. Access http://localhost:5173"
        exit 1
        ;;
esac
