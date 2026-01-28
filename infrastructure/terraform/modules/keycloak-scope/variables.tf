variable "realm_id" {
  description = "The realm this client scope belongs to"
  type        = string
}

variable "name" {
  description = "The name of the client scope"
  type        = string
}

variable "description" {
  description = "The description of the client scope"
  type        = string
  default     = ""
}

variable "include_in_token_scope" {
  description = "Include this scope in the token scope"
  type        = bool
  default     = true
}
