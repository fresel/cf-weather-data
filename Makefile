.PHONY: help
help: # Display help information
	@echo "Makefile commands:"
	@echo "  dev-up        - Start development environment"
	@echo "  dev-down      - Stop development environment"
	@echo "  dev-restart   - Restart development environment"
	@echo "  dev-logs      - View logs of development environment"
	@echo "  dev-terraform-init  - Initialize Terraform in the development environment"
	@echo "  dev-terraform-apply - Apply Terraform configuration in the development environment"
	@echo "  dev-terraform-plan  - Plan Terraform changes in the development environment"
	@echo "  prod-terraform-init  - Initialize Terraform in the production environment"
	@echo "  prod-terraform-apply - Apply Terraform configuration in the production environment"
	@echo "  prod-terraform-plan  - Plan Terraform changes in the production environment"

# Local development environment using Docker Compose
.PHONY: dev-up
dev-up: # Start development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev up -d

.PHONY: dev-down
dev-down: # Stop development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev down

.PHONY: dev-restart
dev-restart: # Restart development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev restart

.PHONY: dev-logs
dev-logs: # View logs of development environment
	docker compose -f docker-compose.dev.yml --env-file .env.dev logs -f

.PHONY: dev-terraform-init
dev-terraform-init: # Initialize Terraform in the development environment
	set -a && source .env.dev && set +a && \
	cd infrastructure/terraform/environments/dev && terraform init

.PHONY: dev-terraform-apply
dev-terraform-apply: # Apply Terraform configuration in the development environment
	set -a && source .env.dev && set +a && \
	cd infrastructure/terraform/environments/dev && terraform apply

.PHONY: dev-terraform-plan
dev-terraform-plan: # Plan Terraform changes in the development environment
	set -a && source .env.dev && set +a && \
	cd infrastructure/terraform/environments/dev && terraform plan

# Production environment
.PHONY: prod-terraform-init
prod-terraform-init: # Initialize Terraform in the production environment
	set -a && source .env.prod && set +a && \
	cd infrastructure/terraform/environments/prod && terraform init

.PHONY: prod-terraform-apply
prod-terraform-apply: # Apply Terraform configuration in the production environment
	set -a && source .env.prod && set +a && \
	cd infrastructure/terraform/environments/prod && terraform apply

.PHONY: prod-terraform-plan
prod-terraform-plan: # Plan Terraform changes in the production environment
	set -a && source .env.prod && set +a && \
	cd infrastructure/terraform/environments/prod && terraform plan