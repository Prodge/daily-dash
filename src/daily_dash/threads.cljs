(ns daily-dash.threads
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [daily-dash.state :refer [state]]
            [daily-dash.constants :as constants]
            [daily-dash.ajax :refer [go-get!]]
            [daily-dash.utils :as utils]
            [cljs.core.async :refer [put! chan <! >! timeout close!]]
            [cljs-time.local :as local-time]
            [cljs-time.coerce :as time-coerce]))

(defn bitcoin-price-thread []
  (go
    (loop [counter 0]
      (println "Bitcoin price update")
      (let [today (<! (go-get! constants/bitcoin-price-api))
            yesterday-aud (<! (go-get! constants/bitcoin-price-api-yesterday-aud))
            yesterday-usd (<! (go-get! constants/bitcoin-price-api-yesterday-usd))]
        (when (not (some utils/error? [today yesterday-aud yesterday-usd]))
          (swap! state assoc-in [:bitcoin :price] {:today {:AUD (get-in today [:bpi :AUD :rate_float])
                              :USD (get-in today [:bpi :USD :rate_float])}
                      :yesterday {:AUD  (-> yesterday-aud :bpi vals first)
                                  :USD (-> yesterday-usd :bpi vals first)}
                      :last-updated (local-time/local-now)})))
      (<! (timeout constants/update-interval))
      (recur (inc counter)))))


(defn weather-thread []
  (go
    (loop [counter 0]
      (println "Bitcoin price update")
      (let [current (<! (go-get! constants/weather-api-today))]
        (when (not (some utils/error? [current]))
          (swap! state assoc-in [:weather :today] {:temperature {:current (-> current :main :temp)
                                         :min nil
                                         :max nil}
                           :description (-> current :weather first :description)
                           :wind (:wind current)
                           :humidity (-> current :main :humidity)
                           :last-updated (local-time/local-now)
                           :sun {:rise (time-coerce/from-long (-> current :sys :sunrise))
                                 :set (time-coerce/from-long (-> current :sys :sunset))}})))
      (<! (timeout constants/update-interval))
      (recur (inc counter)))))


(defn state-dump-thread []
  (go
    (loop []
      (js/console.log @state)
      (<! (timeout constants/state-dump-interval))
      (recur))))
