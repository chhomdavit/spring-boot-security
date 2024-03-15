package web.project.springbootcrud.exception;


public class NotFoundException extends RuntimeException{
    
    public NotFoundException(String message) {
		super(message);
	}
}
