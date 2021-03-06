(ns daily-dash.core
  (:require [reagent.core :as r]
            [daily-dash.component :as component]
            [daily-dash.threads :as t]))

(enable-console-print!)

(defn on-js-reload []
  (println "Reloading"))

(defn ^:export run []
  (t/bitcoin-price-thread)
  (t/weather-thread)
  (t/state-dump-thread)
  (r/render [component/dashboard]
  (js/document.getElementById "app")))
