(ns daily-dash.constants)

(def update-interval 20000)

(def state-dump-interval 2000)

(def bitcoin-price-api "http://api.coindesk.com/v1/bpi/currentprice/AUD.json")

(def bitcoin-price-api-yesterday-aud "http://api.coindesk.com/v1/bpi/historical/close.json?currency=AUD&for=yesterday")
(def bitcoin-price-api-yesterday-usd "http://api.coindesk.com/v1/bpi/historical/close.json?currency=USD&for=yesterday")
