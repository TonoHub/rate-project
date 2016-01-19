package edu.upc.eetac.dsa.rate.client;

import android.util.Log;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.rate.client.entity.AuthToken;
import edu.upc.eetac.dsa.rate.client.entity.Link;
import edu.upc.eetac.dsa.rate.client.entity.Root;
import edu.upc.eetac.dsa.rate.client.entity.Game;

/**
 * Created by tono on 19/12/2015.
 */
public class RateClient {
    private final static String BASE_URI = "http://10.183.40.74:8080/rate";
    private static RateClient instance;
    private AuthToken authToken = null;
    private Game sting = null;
    private Root root;
    private ClientConfig clientConfig = null;
    private final static String TAG = RateClient.class.toString();
    private Client client = null;

    private RateClient() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        loadRoot();
    }

    public static RateClient getInstance() {
        if (instance == null)
            instance = new RateClient();
        return instance;
    }

    private void loadRoot() {
        WebTarget target = client.target(BASE_URI);
        Response response = target.request().get();

        String json = response.readEntity(String.class);
        root = (new Gson()).fromJson(json, Root.class);
    }

    public Root getRoot() {
        return root;
    }

    public final static Link getLink(List<Link> links, String rel){
        for(Link link : links){
            if(link.getRels().contains(rel)) {
                return link;
            }
        }
        return null;
    }

    public boolean login(String userid, String password) {
        String loginUri = getLink(root.getLinks(), "login").getUri().toString();
        WebTarget target = client.target(loginUri);
        Form form = new Form();
        form.param("loginid", userid);
        form.param("password", password);
        Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String json = response.readEntity(String.class);
            authToken = (new Gson()).fromJson(json, AuthToken.class);
            Log.d(TAG, json);
            return true;
        }
        else
        {
            Log.d(TAG, "pon las credenciales de un usuario existente en la base!");
            return false;
        }
    }

    public String getReview(String uri) throws RateClientException {
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new RateClientException(response.readEntity(String.class));
    }

    public String getReviews(String uri) throws RateClientException {
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-rev").getUri().toString();
        }
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new RateClientException(response.readEntity(String.class));
    }


    public String getGame(String uri) throws RateClientException {
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new RateClientException(response.readEntity(String.class));
    }

    public String getGames(String uri) throws RateClientException {
        if(uri==null){
            uri = getLink(authToken.getLinks(), "current-games").getUri().toString();
        }
        WebTarget target = client.target(uri);
        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode())
            return response.readEntity(String.class);
        else
            throw new RateClientException(response.readEntity(String.class));
    }


    public boolean CreateGame(Form form) throws RateClientException {

        String token = authToken.getToken();
        String uri = getLink(authToken.getLinks(), "create-game").getUri().toString();
        WebTarget target = client.target(uri);
        Response response = target.request().header("X-Auth-Token", token).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode())
            return true;
        else
            return false;


    }

    public boolean CreateReview(Form form) throws RateClientException {

        String token = authToken.getToken();
        String uri = getLink(authToken.getLinks(), "create-rev").getUri().toString();
        WebTarget target = client.target(uri);
        Response response = target.request().header("X-Auth-Token", token).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode())
            return true;
        else
            return false;


    }

    public boolean CreateScore(Form form) throws RateClientException {

        String token = authToken.getToken();
        String uri = getLink(authToken.getLinks(), "create-score").getUri().toString();
        WebTarget target = client.target(uri);
        Response response = target.request().header("X-Auth-Token", token).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode())
            return true;
        else
            return false;


    }

    public boolean CreateLike(Form form) throws RateClientException {

        String token = authToken.getToken();
        String uri = getLink(authToken.getLinks(), "create-likes").getUri().toString();
        WebTarget target = client.target(uri);
        Response response = target.request().header("X-Auth-Token", token).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode())
            return true;
        else
            return false;


    }
}

