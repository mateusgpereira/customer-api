package io.demo.resources;

import java.util.Optional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import io.demo.entities.Customer;
import io.demo.models.CustomerRest;
import io.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("/api/v1/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@AllArgsConstructor
public class CustomerResource {

    private CustomerService customerService;

    @GET
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Get all customers",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,
                                    implementation = CustomerRest.class)))
    })
    public Response getCustomer() {
        return Response.ok(customerService.getAll()).build();
    }

    @GET
    @Path("/{customerId}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Get customer by id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = CustomerRest.class))),
            @APIResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON))
    })
    public Response getCustomerById(@PathParam("customerId") Long customerId) {
        Optional<CustomerRest> optional = customerService.getById(customerId);
        if (optional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(optional.get()).build();
    }

    @POST
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Customer Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Customer.class))),
            @APIResponse(responseCode = "400",
                    description = "Customer already exists for customerId",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)),
    })
    public Response createCustomer(@Valid CustomerRest customer) {
        final CustomerRest created = customerService.save(customer);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Customer updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT,
                                            implementation = Customer.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "No Customer found for customerId provided",
                            content = @Content(mediaType = "application/json")),
            })
    public Response updateCustomer(@Valid CustomerRest customer) {
        final CustomerRest updated = customerService.update(customer);
        return Response.ok(updated).build();
    }


}
