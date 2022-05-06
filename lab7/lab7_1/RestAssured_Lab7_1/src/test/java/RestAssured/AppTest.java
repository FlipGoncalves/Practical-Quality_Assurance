package RestAssured;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class AppTest 
{

    @Test
    public void endpointavailable() {
        when().get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200);    
    }

    @Test
    public void Todo4() {
        given().pathParam( "todoNumber", 4 ).when().get( "https://jsonplaceholder.typicode.com/todos/{todoNumber}" ).then().contentType( ContentType.JSON )
                .assertThat().statusCode( 200 ).log().body().body( "title", equalTo( "et porro tempora" ) );    }

    @Test
    public void getalltodosyouget198and199() {
        List<Map<String, Object>> todos = get( "https://jsonplaceholder.typicode.com/todos" ).as( new TypeRef<List<Map<String, Object>>>() {} );
    
        assertTrue( todos.size() >= 199 );
        assertThat( todos.get( 197 ).get( "id" ), IsEqual.<Object>equalTo( 198 ) );
        assertThat( todos.get( 198 ).get( "id" ), IsEqual.<Object>equalTo( 199 ) );
        assertThat( todos.stream().map( ( map ) -> { return map.get( "id" ); }).collect( Collectors.toList() ), hasItems( 198,199 ) );    
    }

}
