(ns daily-dash.constants
  (:require [cljs-time.format :refer [formatter]]))

(def update-interval (* 1000 60 1))

(def state-dump-interval 2000)

(def bitcoin-price-api "http://api.coindesk.com/v1/bpi/currentprice/AUD.json")

(def bitcoin-price-api-yesterday-aud "http://api.coindesk.com/v1/bpi/historical/close.json?currency=AUD&for=yesterday")
(def bitcoin-price-api-yesterday-usd "http://api.coindesk.com/v1/bpi/historical/close.json?currency=USD&for=yesterday")

(def weather-api-today "http://api.openweathermap.org/data/2.5/weather?q=perth&units=metric&APPID=d2d2b3d1f40c76b46b06688c1ea746f3")

(def date-time-formatter
  (formatter "hh:mm:ss"))

(def price-format "%.2f")
