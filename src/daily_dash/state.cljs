(ns daily-dash.state
  (:require [reagent.core :as r]))

(defonce state (r/atom {:bitcoin {:price {:today {:AUD 0
                                                  :USD 0}
                                          :yesterday {:AUD 0
                                                      :USD 0}
                                          :last-updated "never"}}}))

(defn bitcoin-price [day currency]
  (get-in @state [:bitcoin :price day currency]))

(defn bitcoin-price-difference [currency]
  (- (bitcoin-price :today currency)
     (bitcoin-price :yesterday currency)))

(defn bitcoin-price-last-update []
  (get-in @state [:bitcoin :price :last-updated]))
