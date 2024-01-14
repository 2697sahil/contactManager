# ContactManager

The ContactManager project is a robust API for managing contacts, built with OpenAPI and utilizing the power of code generation with SQLLite as the underlying database.

## Features

- **Create Contact**: Easily create a new contact by providing the required details using a simple POST request.

- **Search Contacts**: Efficiently search for contacts based on first name, last name, or any other parameter of your choice using a GET request.

- **Update Contact Details**: Seamlessly update contact information by sending a PUT request with the contact ID and the updated details.

- **Delete Contact**: Remove a contact from the database with a DELETE request using the contact ID.

## OpenAPI Specification

The API follows the OpenAPI 3.0.0 specification and can be easily integrated into your projects. The API definition is clean and concise, making it straightforward to understand and work with.

```yaml
openapi: 3.0.0
info:
  title: ContactManager API
  version: 1.0.0

servers:
  - url: http://localhost:8080/

# ... (paths, components, etc.)
```

## Code Generation

The project showcases the use of OpenAPI code generation to streamline the development process. The API definition serves as a single source of truth, allowing automatic generation of server-side and client-side code, reducing development time and potential errors.

## Database: SQLLite

ContactManager utilizes SQLLite as its database, providing a lightweight, embedded relational database solution. SQLLite is known for its simplicity and efficiency, making it a perfect fit for projects with moderate data storage needs.

## Getting Started

1. Clone the repository.
2. Configure your SQLLite database.
3. Run the server.
4. Start managing your contacts with ease.

Feel free to explore the OpenAPI definition in the `openapi.yaml` file and leverage the power of code generation for a smooth development experience. If you encounter any issues or have suggestions, please open an issue or submit a pull request. Happy coding!