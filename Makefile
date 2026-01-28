.PHONY: help dev-up dev-down dev-restart dev-logs

help: # Display help information
	@echo "Makefile commands:"
	@echo "  dev-up        - Start development environment"
	@echo "  dev-down      - Stop development environment"
	@echo "  dev-restart   - Restart development environment"
	@echo "  dev-logs      - View logs of development environment"

# Local development environment using Docker Compose
dev-up: # Start development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev up -d

dev-down: # Stop development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev down

dev-restart: # Restart development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev restart

dev-logs: # View logs of development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev logs -f

dev-terraform-init: # Initialize Terraform in the development environment
	set -a && source .env.dev && set +a && \
	cd infrastructure/terraform/environments/dev && terraform init

dev-terraform-apply: # Apply Terraform configuration in the development environment
	set -a && source .env.dev && set +a && \
	cd infrastructure/terraform/environments/dev && terraform apply

dev-terraform-plan: # Plan Terraform changes in the development environment
	set -a && source .env.dev && set +a && \
	cd infrastructure/terraform/environments/dev && terraform plan