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

module "keycloak_client" {
  source                = "../../modules/keycloak-client"
  realm_id              = var.keycloak_realm
  client_id             = "cf-weather-api"
  client_name           = "CF Weather API"
  client_description    = "API client for CF Weather application"
  client_enabled        = true
  client_access_type    = "BEARER-ONLY"
  standard_flow_enabled = false
}
