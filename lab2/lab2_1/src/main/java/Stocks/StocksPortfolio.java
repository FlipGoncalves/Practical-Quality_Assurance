package Stocks;

import java.util.ArrayList;

public class StocksPortfolio {
    private ArrayList<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock st) {
        stocks.add(st);
    }

    public double getTotalValue() {
        double return_value = 0;
        for (Stock sc: stocks) {
            return_value += sc.getQuantity() * stockmarket.lookUpPrice(sc.getLabel());
        }
        return return_value;
    }

    public IStockmarketService getStockmarket() {
        return stockmarket;
    }

    public void setStockmarket(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
    }
}
