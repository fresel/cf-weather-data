# Terraform Infrastructure

## Admin client setup for local development
To manage Keycloak resources using Terraform, an admin client must be set up in Keycloak. Follow these steps to create the admin client:
1. Start the Keycloak server using Docker Compose:
   ```bash
   docker-compose -f infrastructure/docker/dev/keycloak/docker-compose.yml up -d
   ```
2. Access the Keycloak admin console at `http://localhost:9180` using the default credentials:
   - Username: `admin`
   - Password: `admin`
3. Create a new client with the following settings:
   - Client ID: `cf-weather-admin`
4. In the "Credentials" tab of the client, note down the generated client secret. This will be used in the Terraform configuration.
5. Update the `.env.dev` file with the client ID, client secret, realm, and URL:
   ```env
   TF_VAR_keycloak_client_id=cf-weather-admin
   TF_VAR_keycloak_client_secret=<your-client-secret>
   TF_VAR_keycloak_realm=cf-weather
   TF_VAR_keycloak_url=http://localhost:9180
   ```
6. In the affected realm, select the management client `realm-management`, and, in the Roles tab, create a new role called view-system. See https://www.keycloak.org/docs/latest/upgrading/index.html#the-serverinfo-endpoint-only-returns-the-system-info-for-administrators-in-the-administrator-realm for more information about the view-system role.

7. In `cf-weather-admin` client, in the Service Account Roles mapping tab, assign the just created view-system client role to the client.

8. Also assign the `realm-admin` role from the realm-management client to the `cf-weather-admin` client in the Service Account Roles mapping tab. This will allow the admin client to manage Keycloak resources.

8. You need to create a role for the realm management client to allow the admin client to manage Keycloak resources. Assign the `realm-admin` role to the `cf-weather-admin` client.