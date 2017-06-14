(ns daily-dash.threads
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [daily-dash.state :refer [state]]
            [daily-dash.constants :as constants]
            [daily-dash.ajax :refer [go-get!]]
            [cljs.core.async :refer [put! chan <! >! timeout close!]]
            [cljs-time.local :as local-time]
            [cljs-time.coerce :as time-coerce]))

(defn bitcoin-price-thread []
  (go
    (loop [counter 0]
      (println "Bitcoin price update")
      (let [today (<! (go-get! constants/bitcoin-price-api))
            prices {:today {:AUD (get-in today [:bpi :AUD :rate_float])
                            :USD (get-in today [:bpi :USD :rate_float])}
                    :yesterday {:AUD  (-> (<! (go-get! constants/bitcoin-price-api-yesterday-aud)) :bpi vals first)
                                :USD (-> (<! (go-get! constants/bitcoin-price-api-yesterday-usd)) :bpi vals first)}
                    :last-updated (local-time/local-now)}]
        (swap! state assoc-in [:bitcoin :price] prices))
      (<! (timeout constants/update-interval))
      (recur (inc counter)))))


(defn weather-thread []
  (go
    (loop [counter 0]
      (println "Bitcoin price update")
      (let [current (<! (go-get! constants/weather-api-today))
            weather-today {:temperature {:current (-> current :main :temp)
                                         :min nil
                                         :max nil}
                           :description (-> current :weather first :description)
                           :wind (:wind current)
                           :humidity (-> current :main :humidity)
                           :last-updated (local-time/local-now)
                           :sun {:rise (time-coerce/from-long (-> current :sys :sunrise))
                                 :set (time-coerce/from-long (-> current :sys :sunset))}}]
          (swap! state assoc-in [:weather :today] weather-today))
      (<! (timeout constants/update-interval))
      (recur (inc counter)))))


(defn state-dump-thread []
  (go
    (loop []
      (js/console.log @state)
      (<! (timeout constants/state-dump-interval))
      (recur))))
