package in.nucleusteq.plasma.handler;

import feign.Response;
import feign.codec.ErrorDecoder;
import in.nucleusteq.plasma.exception.ResourceNotFoundException;

public class CustomErrorDecoder implements ErrorDecoder  {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {

            return new ResourceNotFoundException("Resource not found with given id.");
        }
        
        if (response.status() == 500) {

            return new ResourceNotFoundException("Something went wrong.");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
