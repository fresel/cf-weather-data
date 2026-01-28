terraform {
  required_providers {
    keycloak = {
      source  = "keycloak/keycloak"
      version = ">= 5.6.0"
    }
  }
}

resource "keycloak_openid_client_scope" "scope" {
  realm_id               = var.realm_id
  name                   = var.name
  description            = var.description
  include_in_token_scope = var.include_in_token_scope
}

