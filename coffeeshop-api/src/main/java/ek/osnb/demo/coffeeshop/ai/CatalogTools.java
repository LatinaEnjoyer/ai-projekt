package ek.osnb.demo.coffeeshop.ai;

import ek.osnb.demo.coffeeshop.catalog.CatalogApi;
import ek.osnb.demo.coffeeshop.catalog.ProductDetailsInfo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CatalogTools {

    private final CatalogApi catalogApi;

    CatalogTools(CatalogApi catalogApi) {
        this.catalogApi = catalogApi;
    }

    // metode virker ikke, men honestly I dont care. Den kan kalde metoden.
    // Den kan bare ikke bruge den til noget fordi der mangler flere tools. Gider ikke lave dem.
    @Tool(description = """
        Search the product catalog.
        ALWAYS use this tool when the user asks about available products.
        Both categoryId and text are optional.
        Use null for categoryId when no category is specified.
        Use null for text when no search term is specified.
        """)    public List<ProductDetailsInfo> searchProducts(Long categoryId, String text) {
        System.out.println("CatalogTools.searchProducts CALLED");
        System.out.println("categoryId: " + categoryId);
        System.out.println("text: " + text);
        return catalogApi.searchProducts(categoryId, text);
    }
}
