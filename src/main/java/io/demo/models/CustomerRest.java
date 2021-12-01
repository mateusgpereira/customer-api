package io.demo.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerRest {

  private Long id;

  @NotEmpty
  private String firstName;

  private String middleName;

  @NotEmpty
  private String lastName;

  private String suffix;

  @Email
  private String email;

  private String phone;

}
