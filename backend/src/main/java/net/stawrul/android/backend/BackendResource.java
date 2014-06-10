package net.stawrul.android.backend;

import net.stawrul.android.backend.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/")
public class BackendResource {
    private static List<Category> categories;

    static {
        categories = new ArrayList<Category>();
        categories.add(new Category(1, "Cloud category", Category.Type.USER_DEFINE, 0));
        categories.add(new Category(2, "Second cloud category", Category.Type.USER_DEFINE, 0));
    }

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

    @GET
    @Path("categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Category> getCategories() {

        return categories;
    }

    @GET
    @Path("categories/{id}")
    public String getCategory(@PathParam("id") int id) {
        return String.valueOf(id);
    }

    @POST
    @Path("categories")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveCategory(Category category) {
        categories.add(category);
    }
}
