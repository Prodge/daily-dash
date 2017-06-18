(ns daily-dash.component
  (:require [daily-dash.state :as state]
            [cljsjs.moment]))

(defn dashboard []
  [:div.main
   [:h2 "Dashboard"]

   [:h4 "Bitcoin Price"]
   [:p (state/bitcoin-price :today :AUD) " " (state/bitcoin-price-difference :AUD)]
   [:p (state/bitcoin-price :today :USD) " " (state/bitcoin-price-difference :USD)]
   [:p "Last Updated: " (state/bitcoin-price-last-update)]

   [:h4 "Weather"]
   [:p (state/current-temperature)]
   [:p "Last Updated: " (state/bitcoin-price-last-update)]

   ])
