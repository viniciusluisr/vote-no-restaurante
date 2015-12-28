package br.com.vote.no.restaurante.config;

import com.mangofactory.swagger.configuration.JacksonScalaSupport;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.configuration.SpringSwaggerModelConfig;
import com.mangofactory.swagger.configuration.SwaggerGlobalSettings;
import com.mangofactory.swagger.core.DefaultSwaggerPathProvider;
import com.mangofactory.swagger.core.SwaggerApiResourceListing;
import com.mangofactory.swagger.core.SwaggerPathProvider;
import com.mangofactory.swagger.scanners.ApiListingReferenceScanner;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vinicius on 26/12/15.
 */
@Configuration
@ComponentScan(basePackages = "com.mangofactory.swagger")
@EnableAutoConfiguration
public class SwaggerConfig {

    public static final List<String> DEFAULT_INCLUDE_PATTERNS = Arrays.asList("/v1/.*");
    public static final String SWAGGER_GROUP = "vote-no-restaurante-api";

    private String docsLocation = "https://vote-no-restaurante-apis.herokuapp.com";

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;
    @Autowired
    private SpringSwaggerModelConfig springSwaggerModelConfig;

    /**
     * Adds the jackson scala module to the MappingJackson2HttpMessageConverter registered with spring
     * Swagger core models are scala so we need to be able to convert to JSON
     * Also registers some custom serializers needed to transform swagger models to swagger-ui required json format
     *
     * @return the jackson scala support
     */
    @Bean
    public JacksonScalaSupport jacksonScalaSupport() {
        JacksonScalaSupport jacksonScalaSupport = new JacksonScalaSupport();
        //Set to false to disable
        jacksonScalaSupport.setRegisterScalaModule(true);
        return jacksonScalaSupport;
    }


    /**
     * Global swagger settings
     *
     * @return the swagger global settings
     */
    @Bean
    public SwaggerGlobalSettings swaggerGlobalSettings() {
        SwaggerGlobalSettings swaggerGlobalSettings = new SwaggerGlobalSettings();
        swaggerGlobalSettings.setGlobalResponseMessages(springSwaggerConfig.defaultResponseMessages());
        swaggerGlobalSettings.setIgnorableParameterTypes(springSwaggerConfig.defaultIgnorableParameterTypes());
        swaggerGlobalSettings.setParameterDataTypes(springSwaggerModelConfig.defaultParameterDataTypes());
        return swaggerGlobalSettings;
    }

    /**
     * API Info as it appears on the swagger-ui page
     */
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "vote-no-restaurante API",
                "API para votação de restaurantes e ranqueamento",
                "",
                "viniciusluisr@gmail.com",
                "",
                ""
        );
        return apiInfo;
    }

    /**
     * Configure a SwaggerApiResourceListing for each swagger instance within your app. e.g. 1. private  2. external apis
     * Required to be a spring bean as spring will call the postConstruct method to bootstrap swagger scanning.
     *
     * @return swagger api resource listing
     */
    @Bean
    public SwaggerApiResourceListing swaggerApiResourceListing() {
        //The group name is important and should match the group set on ApiListingReferenceScanner
        //Note that swaggerCache() is by DefaultSwaggerController to serve the swagger json
        SwaggerApiResourceListing swaggerApiResourceListing = new SwaggerApiResourceListing(springSwaggerConfig.swaggerCache(),
                SWAGGER_GROUP);

        //Set the required swagger settings
        swaggerApiResourceListing.setSwaggerGlobalSettings(swaggerGlobalSettings());

        //Use a custom path provider or springSwaggerConfig.defaultSwaggerPathProvider()
        swaggerApiResourceListing.setSwaggerPathProvider(apiPathProvider());

        //Supply the API Info as it should appear on swagger-ui web page
        swaggerApiResourceListing.setApiInfo(apiInfo());

        //Every SwaggerApiResourceListing needs an ApiListingReferenceScanner to scan the spring request mappings
        swaggerApiResourceListing.setApiListingReferenceScanner(apiListingReferenceScanner());
        return swaggerApiResourceListing;
    }

    /**
     * Api listing reference scanner.
     *
     * @return the api listing reference scanner
     */
    @Bean
    public ApiListingReferenceScanner apiListingReferenceScanner() {
        ApiListingReferenceScanner apiListingReferenceScanner = new ApiListingReferenceScanner();

        //Picks up all of the registered spring RequestMappingHandlerMappings for scanning
        apiListingReferenceScanner.setRequestMappingHandlerMapping(springSwaggerConfig.swaggerRequestMappingHandlerMappings());

        //Excludes any controllers with the supplied annotations
        apiListingReferenceScanner.setExcludeAnnotations(springSwaggerConfig.defaultExcludeAnnotations());

        //
        apiListingReferenceScanner.setResourceGroupingStrategy(springSwaggerConfig.defaultResourceGroupingStrategy());

        //Path provider used to generate the appropriate uri's
        apiListingReferenceScanner.setSwaggerPathProvider(apiPathProvider());

        //Must match the swagger group set on the SwaggerApiResourceListing
        apiListingReferenceScanner.setSwaggerGroup(SWAGGER_GROUP);

        //Only include paths that match the supplied regular expressions
        apiListingReferenceScanner.setIncludePatterns(DEFAULT_INCLUDE_PATTERNS);

        return apiListingReferenceScanner;
    }

    /**
     * Example of a custom path provider
     *
     * @return the api path provider
     */
    @Bean
    public ApiPathProvider apiPathProvider() {
        ApiPathProvider apiPathProvider = new ApiPathProvider(docsLocation);
        apiPathProvider.setDefaultSwaggerPathProvider(springSwaggerConfig.defaultSwaggerPathProvider());
        return apiPathProvider;
    }

    /**
     * Relative swagger path provider.
     *
     * @return the swagger path provider
     */
    @Bean
    public SwaggerPathProvider relativeSwaggerPathProvider() {
        return new ApiRelativeSwaggerPathProvider();
    }

    private class ApiRelativeSwaggerPathProvider extends DefaultSwaggerPathProvider {
        @Override
        public String getAppBasePath() {
            return "/";
        }

        @Override
        public String getSwaggerDocumentationBasePath() {
            return "/api-docs";
        }
    }

}