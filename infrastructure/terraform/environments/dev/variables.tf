variable "keycloak_realm" {
  description = "The Keycloak realm to connect to"
  type        = string
  default     = "cf-weather"
}

variable "keycloak_url" {
  description = "The URL of the Keycloak server"
  type        = string
}

variable "keycloak_client_id" {
  description = "The Keycloak client ID"
  type        = string
  default     = "cf-weather-admin"
}

variable "keycloak_client_secret" {
  description = "The Keycloak client secret"
  type        = string
  sensitive   = true
}
