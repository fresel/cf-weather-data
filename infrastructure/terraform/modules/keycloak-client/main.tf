terraform {
  required_providers {
    keycloak = {
      source  = "keycloak/keycloak"
      version = ">= 5.6.0"
    }
  }
}

resource "keycloak_openid_client" "openid_client" {
  realm_id              = var.realm_id
  client_id             = var.client_id
  name                  = var.client_name
  description           = var.client_description
  enabled               = var.client_enabled
  access_type           = var.client_access_type
  standard_flow_enabled = var.standard_flow_enabled
  valid_redirect_uris   = var.standard_flow_enabled ? var.valid_redirect_uris : []
}
