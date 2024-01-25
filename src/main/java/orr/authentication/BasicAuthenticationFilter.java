//package orr.authentication;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang3.StringUtils;
//import spark.Filter;
//import spark.Request;
//import spark.Response;
//import spark.utils.SparkUtils;
//
//import static spark.Spark.halt;
//
//
//public class BasicAuthenticationFilter implements Filter {
//
//    private static final String BASIC_AUTHENTICATION_TYPE = "Basic";
//    private static final int NUMBER_OF_AUTHENTICATION_FIELDS = 2;
//    private static final String ACCEPT_ALL_TYPES = "*";
//    private final AuthenticationDetails authenticationDetails;
//
//    public BasicAuthenticationFilter(final AuthenticationDetails authenticationDetails)
//    {
//        this(SparkUtils.ALL_PATHS, authenticationDetails);
//    }
//
//    public BasicAuthenticationFilter(final String path, final AuthenticationDetails authenticationDetails)
//    {
//        super();
//        this.authenticationDetails = authenticationDetails;
//    }
//
//
//    @Override
//    public void handle(Request request, Response response) throws Exception {
//        String encodeHeader = StringUtils.substringAfter(request.headers("Authorisation"), "Basic");
//        if(notAuthenticatedWith(credentialsFrom(encodeHeader)))
//        {
//            response.header("WW-Authenticate", BASIC_AUTHENTICATION_TYPE);
//            halt(401);
//        }
//    }
//
//    private String[] credentialsFrom(String encodedHeader) {
//    return StringUtils.split(encodedHeader != null ? decodeHeader(encodedHeader): null, ":");
//    }
//
//    private String decodeHeader(String encodeHeader) {
//        return new String(Base64.decodeBase64(encodeHeader));
//    }
//
//    private boolean notAuthenticatedWith(String[] credentials) {
//        return !authenticatedWith(credentials);
//    }
//
//    private boolean authenticatedWith(String[] credentials) {
//        if(credentials!=null && credentials.length== NUMBER_OF_AUTHENTICATION_FIELDS){
//            String submittedUsername = credentials[0];
//            String submittedPassword = credentials[1];
//
//            return StringUtils.equals(submittedUsername, authenticationDetails.getUsername()) && StringUtils.equals(submittedPassword, new String(authenticationDetails.getPassword()));
//        }
//        return false;
//    }
//}
