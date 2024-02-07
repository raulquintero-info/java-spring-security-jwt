package dtos;
// esta clase va a ser la que nos devolvera la informacion con el token y el tipo dque tenga este 
public class DtoAuthRespuesta {
	private String accessToken;
	private String tokenType = "Bearer ";
	
	public DtoAuthRespuesta(String accessToken) {
		this.accessToken = accessToken;
	}

	
	
	public String getAccessToken() {
		return accessToken;
	}

	public DtoAuthRespuesta() {
		super();
	}



	public DtoAuthRespuesta(String accessToken, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}



	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	
	
	
	
	
}
