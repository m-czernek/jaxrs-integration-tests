package com.redhat.validation.resource;

import javax.validation.constraints.Size;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("")
@ValidateOnExecution(type = {ExecutableType.NONE})
public class TestValidateOnExecutionErrorOneLevel_Class extends TestValidateOnExecutionResource {
    @POST
    @Path("override")
    @Size(min = 1)
    @Override
    @ValidateOnExecution(type = {ExecutableType.IMPLICIT})
    public String override(@Size(max = 1) String s) {
        return s;
    }
}
