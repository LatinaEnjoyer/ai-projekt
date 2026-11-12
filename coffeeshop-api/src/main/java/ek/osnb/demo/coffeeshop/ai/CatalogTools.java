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

    @Tool(description = "Search for products by category and search text")
    public List<ProductDetailsInfo> searchProducts(Long categoryId, String text) {
        return catalogApi.searchProducts(categoryId, text);
    }
}
