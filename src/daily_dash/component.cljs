(ns daily-dash.component
  (:require [daily-dash.state :as state]))

(defn dashboard []
  [:div.main
   [:h2 "Dashboard"]
   [:h4 "Bitcoin Price"]
   [:p "$" (state/bitcoin-price :today :AUD) " AUD (" (state/bitcoin-price-difference :AUD) ")" ]
   [:p "$" (state/bitcoin-price :today :USD) " USD (" (state/bitcoin-price-difference :USD) ")"]
   [:p "Last Updated: " (state/bitcoin-price-last-update)]])
