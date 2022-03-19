package Stocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest 
{
    @Mock
    private IStockmarketService stockmarket;

    @InjectMocks
    private StocksPortfolio stport;

    @Test
    public void getTotalValueTest()
    {
        when(stockmarket.lookUpPrice("stock1")).thenReturn(2.0);
        when(stockmarket.lookUpPrice("stock2")).thenReturn(3.0);
        when(stockmarket.lookUpPrice("stock3")).thenReturn(5.0);

        stport = new StocksPortfolio(stockmarket);
        
        stport.addStock(new Stock("stock1", 2));
        stport.addStock(new Stock("stock2", 3));
        stport.addStock(new Stock("stock3", 1));

        assertEquals(stport.getTotalValue(), 18.0);


        verify(stockmarket, times(2)).lookUpPrice(new String());
        verify(stockmarket).lookUpPrice("stock1");
        verify(stockmarket).lookUpPrice("stock2");
        verify(stockmarket).lookUpPrice("stock3");
    }
}
