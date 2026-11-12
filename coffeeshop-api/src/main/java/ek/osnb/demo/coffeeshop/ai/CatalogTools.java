
package ek.osnb.demo.coffeeshop.ai;

import ek.osnb.demo.coffeeshop.catalog.CatalogApi;
import ek.osnb.demo.coffeeshop.catalog.ProductDetailsInfo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
class CatalogTools {

    private final CatalogApi catalogApi;

    CatalogTools(CatalogApi catalogApi) {
        this.catalogApi = catalogApi;
    }

    @Tool(description = """
            Search the product catalog.
            Use this tool whenever the user asks about products,
            prices, stock, origins, roast levels or recommendations.
            Both categoryId and text are optional.
            Text can match product name, description, category,
            origin or roast level.
            """)
    public List<ProductDetailsInfo> searchProducts(
            Long categoryId, String text) {

        Long effectiveCategoryId =
                categoryId != null && categoryId > 0
                        ? categoryId : null;

        String search = text == null || text.isBlank()
                ? null
                : text.trim().toLowerCase(Locale.ROOT);

        return catalogApi.searchProducts(effectiveCategoryId, null)
                .stream()
                .filter(product -> search == null
                        || matches(product.name(), search)
                        || matches(product.description(), search)
                        || matches(product.category(), search)
                        || matches(product.origin(), search)
                        || matches(product.roastLevel(), search))
                .toList();
    }

    private boolean matches(String value, String search) {
        return value != null
                && value.toLowerCase(Locale.ROOT).contains(search);
    }

    @Tool(description = "Get complete details about a specific product by its ID.")
    public ProductDetailsInfo getProductDetails(Long productId) {
        return catalogApi.getProductDetails(productId);
    }
}

