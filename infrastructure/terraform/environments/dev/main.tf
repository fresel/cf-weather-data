terraform {
  required_providers {
    keycloak = {
      source  = "keycloak/keycloak"
      version = ">= 5.6.0"
    }
  }
}

provider "keycloak" {
  client_id     = var.keycloak_client_id
  client_secret = var.keycloak_client_secret
  url           = var.keycloak_url
  realm         = var.keycloak_realm
}

# Test scope module to verify credentials
module "test_scope" {
  source = "../../modules/keycloak-scope"

  realm_id               = var.keycloak_realm
  name                   = "terraform-test-scope"
  description            = "Test scope created by Terraform to verify credentials"
  include_in_token_scope = true
}
