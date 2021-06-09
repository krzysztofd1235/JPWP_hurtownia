package holder;

import converters.ProductView;


public final class ProductViewHolder {



        private ProductView productView;

        private final static ProductViewHolder INSTANCE = new ProductViewHolder();

        private ProductViewHolder(){}

    public ProductView getProductView() {
        return productView;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }

    public static ProductViewHolder getINSTANCE() {
            return INSTANCE;
        }


}
