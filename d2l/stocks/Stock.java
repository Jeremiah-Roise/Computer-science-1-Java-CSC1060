public class Stock {
    String name;
    String symbol;
    Double previousClosingPrice;
    Double currentPrice;

    Stock(String name, String symbol, double previousClosingPrice, double currentPrice){
        this.name = name;
        this.symbol = symbol;
        this.previousClosingPrice = previousClosingPrice;
        this.currentPrice = currentPrice;
    }

    public Double getChangePercent() {
        //     | Difference                       |   |value of 1% of previousClosingPrice|
        return (previousClosingPrice - currentPrice) * (previousClosingPrice / 100f);
    }
}
