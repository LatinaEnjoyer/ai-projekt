package ek.osnb.demo.coffeeshop.ai;

import ek.osnb.demo.coffeeshop.catalog.CatalogApi;
import ek.osnb.demo.coffeeshop.catalog.ProductDetailsInfo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class StaffTools {

    private final CatalogApi catalogApi;

    StaffTools(CatalogApi catalogApi) {
        this.catalogApi = catalogApi;
    }

    @Tool(description = """
            List all products with 5 or fewer items in stock.
            Use this when staff asks about low stock or products
            that need restocking.
            """)
    public List<ProductDetailsInfo> findLowStockProducts() {
        return catalogApi.searchProducts(null, null)
                .stream()
                .filter(product -> product.stock() <= 5)
                .toList();
    }
}

