package org.knowm.xchange.examples.bitcurex;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcurex.BitcurexExchange;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import org.knowm.xchange.bitcurex.service.polling.BitcurexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Bitcurex
 */
public class BitcurexTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Bitcurex exchange API using default settings
    Exchange bitcurex = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    requestData(bitcurex, CurrencyPair.BTC_EUR);
    requestData(bitcurex, CurrencyPair.BTC_PLN);
  }

  private static void requestData(Exchange bitcurex, CurrencyPair pair) throws IOException {

    generic(bitcurex, pair);
    raw(bitcurex, pair.counter.getCurrencyCode());
  }

  private static void generic(Exchange bitcurex, CurrencyPair pair) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcurex.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    Ticker ticker = marketDataService.getTicker(pair);

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }

  private static void raw(Exchange bitcurex, String currency) throws IOException {

    BitcurexMarketDataServiceRaw bitcurexMarketDataServiceRaw = (BitcurexMarketDataServiceRaw) bitcurex.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    BitcurexTicker ticker = bitcurexMarketDataServiceRaw.getBitcurexTicker(currency);

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Vol: " + ticker.getVolume());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }
}
