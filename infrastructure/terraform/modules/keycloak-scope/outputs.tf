output "id" {
  description = "The ID of the client scope"
  value       = keycloak_openid_client_scope.scope.id
}

output "name" {
  description = "The name of the client scope"
  value       = keycloak_openid_client_scope.scope.name
}
