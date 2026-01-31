variable "realm_id" {
  description = "The realm this client belongs to"
  type        = string
}

variable "client_id" {
  description = "The unique ID of the client"
  type        = string
}

variable "client_name" {
  description = "The display name of the client"
  type        = string
}

variable "client_description" {
  description = "The description of the client"
  type        = string
  default     = ""
}

variable "client_enabled" {
  description = "Whether the client is enabled"
  type        = bool
  default     = true
}

variable "client_access_type" {
  description = "The access type of the client (CONFIDENTIAL, PUBLIC, BEARER-ONLY)"
  type        = string
  default     = "CONFIDENTIAL"
  validation {
    condition     = contains(["CONFIDENTIAL", "PUBLIC", "BEARER-ONLY"], var.client_access_type)
    error_message = "client_access_type must be one of CONFIDENTIAL, PUBLIC, or BEARER-ONLY"
  }
}
variable "standard_flow_enabled" {
  description = "Standard flow enabled for the client"
  type        = bool
  default     = true
}

variable "valid_redirect_uris" {
  description = "List of valid redirect URIs for the client (only used when standard_flow_enabled is true)"
  type        = list(string)
  default     = []
}
